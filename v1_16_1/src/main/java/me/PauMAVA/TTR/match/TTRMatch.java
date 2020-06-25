/*
 * TheTowersRemastered (TTR)
 * Copyright (c) 2019-2020  Pau Machetti Vallverdu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.PauMAVA.TTR.match;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.teams.TTRTeam;
import net.minecraft.server.v1_15_R1.Items;
import net.minecraft.server.v1_15_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_15_R1.PacketPlayInClientCommand.EnumClientCommand;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.HashMap;

public class TTRMatch {

    private MatchStatus status;
    private LootSpawner lootSpawner;
    private CageChecker checker;
    private HashMap<Player, Integer> kills = new HashMap<Player, Integer>();

    public TTRMatch(MatchStatus initialStatus) {
        status = initialStatus;
    }

    public boolean isOnCourse() {
        return this.status == MatchStatus.INGAME;
    }

    public void startMatch() {
        this.status = MatchStatus.INGAME;
        this.lootSpawner = new LootSpawner();
        this.checker = new CageChecker();
        this.checker.setCages(TTRCore.getInstance().getConfigManager().getTeamCages(), 2);
        this.checker.startChecking();
        this.lootSpawner.startSpawning();
        TTRCore.getInstance().getWorldHandler().configureTime();
        TTRCore.getInstance().getWorldHandler().configureWeather();
        TTRCore.getInstance().getWorldHandler().setWorldDifficulty(Difficulty.PEACEFUL);
        TTRCore.getInstance().getScoreboard().startScoreboardTask();
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            TTRTeam playerTeam = TTRCore.getInstance().getTeamHandler().getPlayerTeam(player);
            if(playerTeam == null) {
                continue;
            }
            player.teleport(TTRCore.getInstance().getConfigManager().getTeamSpawn(playerTeam.getIdentifier()));
            player.getInventory().clear();
            player.setExp(0);
            player.setGameMode(GameMode.SURVIVAL);
            double health = TTRCore.getInstance().getConfigManager().getMaxHealth();
            AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            maxHealth.setBaseValue(health);
            player.setHealthScale(health);
            player.setHealth(health);
            player.setFoodLevel(20);
            player.setSaturation(20);
            setPlayerArmor(player);
            this.kills.put(player, 0);
        }
    }

    public void endMatch(TTRTeam team) {
        this.status = MatchStatus.ENDED;
        this.lootSpawner.stopSpawning();
        TTRCore.getInstance().getScoreboard().stopScoreboardTask();
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
            ChatColor teamColor = TTRCore.getInstance().getConfigManager().getTeamColor(team.getIdentifier());
            player.sendTitle(teamColor + "" + ChatColor.BOLD + team.getIdentifier(), ChatColor.AQUA + "WINS!", 10, 100, 20);
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 1);
        }
        TTRCore.getInstance().getWorldHandler().enableDayLightCycle();
        TTRCore.getInstance().getWorldHandler().enableWeatherCycle();
        TTRCore.getInstance().getWorldHandler().restoreDifficulty();
    }

    public void playerDeath(Player player, Player killer) {
        new BukkitRunnable(){
            @Override
            public void run() {
                PacketPlayInClientCommand packet = new PacketPlayInClientCommand();
                Field a;
                try {
                    a = packet.getClass().getDeclaredField("a");
                    a.setAccessible(true);
                    a.set(packet, EnumClientCommand.PERFORM_RESPAWN);
                    ((CraftPlayer) player).getHandle().playerConnection.a(packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                TTRTeam team = TTRCore.getInstance().getTeamHandler().getPlayerTeam(player);
                if(team != null) {
                    player.teleport(TTRCore.getInstance().getConfigManager().getTeamSpawn(team.getIdentifier()));
                }
                setPlayerArmor(player);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 10 ,1);
                player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 10 ,1);
                this.cancel();
                kills.put(killer, getKills(killer) + 1);
            }
        }.runTaskLater(TTRCore.getInstance(), 2L);
    }

    private void setPlayerArmor(Player player) {
        TTRTeam team = TTRCore.getInstance().getTeamHandler().getPlayerTeam(player);
        ChatColor color;
        if(team != null) {
            color = TTRCore.getInstance().getConfigManager().getTeamColor(team.getIdentifier());
        } else {
            return;
        }
        ItemStack[] armor = new ItemStack[]{new ItemStack(Material.LEATHER_BOOTS, 1), new ItemStack(Material.LEATHER_LEGGINGS, 1), new ItemStack(Material.LEATHER_CHESTPLATE, 1), new ItemStack(Material.LEATHER_HELMET, 1)};
        for(ItemStack itemStack: armor) {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
            Color armorColor = Color.fromRGB(0,0,0);
            try {
                meta.setColor((Color) armorColor.getClass().getDeclaredField(color.name()).get(armorColor));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
            itemStack.setItemMeta(meta);
        }
        player.getInventory().setArmorContents(armor);
    }

    public MatchStatus getStatus() {
        return this.status;
    }

    public int getKills(Player player) {
        return this.kills.getOrDefault(player, 0);
    }

}
