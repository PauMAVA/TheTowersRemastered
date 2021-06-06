/*
 * TheTowersRemastered (TTR)
 * Copyright (c) 2019-2021  Pau Machetti Vallverdú
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

package me.PauMAVA.TTR.util;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.match.MatchStatus;
import me.PauMAVA.TTR.ui.TeamSelector;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (TTRCore.getInstance().enabled()) {
            event.setJoinMessage(TTRPrefix.TTR_GAME + "" + ChatColor.GREEN + "+ " + ChatColor.GRAY + event.getPlayer().getName() + " has joined the game");
            if (TTRCore.getInstance().getCurrentMatch().getStatus() == MatchStatus.PREGAME) {
                Inventory playerInventory = event.getPlayer().getInventory();
                playerInventory.clear();
                playerInventory.setItem(0, new ItemStack(Material.BLACK_BANNER));
                Location location = TTRCore.getInstance().getConfigManager().getLobbyLocation();
                Location copy = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
                copy.add(location.getX() > 0 ? 0.5 : 0.5, 0.0, location.getZ() > 0 ? 0.5 : -0.5);
                event.getPlayer().teleport(copy);
                TTRCore.getInstance().getAutoStarter().addPlayerToQueue(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (TTRCore.getInstance().enabled()) {
            event.setQuitMessage(TTRPrefix.TTR_GAME + "" + ChatColor.RED + "- " + ChatColor.GRAY + event.getPlayer().getName() + " has left the game");
            TTRCore.getInstance().getAutoStarter().removePlayerFromQueue(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerDropEvent(PlayerDropItemEvent event) {
        if (TTRCore.getInstance().enabled() && !TTRCore.getInstance().getCurrentMatch().isOnCourse()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerClickEvent(PlayerInteractEvent event) {
        if (TTRCore.getInstance().enabled() && !(TTRCore.getInstance().getCurrentMatch().getStatus() == MatchStatus.INGAME)) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                return;
            }
            event.setCancelled(true);
            if (event.getItem() != null && event.getItem().getType() == Material.BLACK_BANNER) {
                new TeamSelector(event.getPlayer()).openSelector();
            }
        }
    }

    @EventHandler
    public void placeBlockEvent(BlockPlaceEvent event) {
        if (TTRCore.getInstance().enabled() && !(TTRCore.getInstance().getCurrentMatch().getStatus() == MatchStatus.INGAME)) {
            event.getPlayer().sendMessage(TTRPrefix.TTR_GAME + "" + ChatColor.RED + "You cannot place a block there!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void breakBlockEvent(BlockBreakEvent event) {
        if (TTRCore.getInstance().enabled() && !TTRCore.getInstance().getCurrentMatch().isOnCourse()) {
            event.getPlayer().sendMessage(TTRPrefix.TTR_GAME + "" + ChatColor.RED + "You cannot break that block!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (TTRCore.getInstance().enabled() && TTRCore.getInstance().getCurrentMatch().isOnCourse()) {
            TTRCore.getInstance().getCurrentMatch().playerDeath(event.getEntity(), event.getEntity().getKiller());
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && !(TTRCore.getInstance().getCurrentMatch().getStatus() == MatchStatus.INGAME)) {
            event.setCancelled(true);
        }
    }


}
