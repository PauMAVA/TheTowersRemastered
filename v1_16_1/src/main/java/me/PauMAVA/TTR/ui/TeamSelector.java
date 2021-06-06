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

package me.PauMAVA.TTR.ui;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.teams.TTRTeam;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamSelector extends CustomUI implements Listener {

    private Player owner;
    private int selected = -1;
    private String lastTeam;

    public TeamSelector(Player player) {
        super(27, "Team Selection");
        this.owner = player;
        setUp();
        TTRCore.getInstance().getServer().getPluginManager().registerEvents(this, TTRCore.getInstance());
        TTRTeam possibleTeam = TTRCore.getInstance().getTeamHandler().getPlayerTeam(this.owner);
        if (possibleTeam != null) {
            for (int i = 0; i < super.getInventory().getSize(); i++) {
                ItemStack stack = super.getInventory().getItem(i);
                if (stack != null) {
                    String cleanName = ChatColor.stripColor(stack.getItemMeta().getDisplayName());
                    if (possibleTeam.getIdentifier().equalsIgnoreCase(cleanName)) {
                        this.selected = i;
                        addEnchantment(i);
                        break;
                    }
                }
            }
        }
    }

    public void openSelector() {
        super.openUI(this.owner);
    }

    public void closeSelector() {
        super.closeUI(this.owner);
    }

    public void setUp() {
        int i = 0;
        for (String teamName : TTRCore.getInstance().getConfigManager().getTeamNames()) {
            setSlot(i, new ItemStack(Material.valueOf(TTRCore.getInstance().getConfigManager().getTeamColor(teamName).name() + "_WOOL"), 1), TTRCore.getInstance().getConfigManager().getTeamColor(teamName) + teamName, null);
            i++;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == super.getInventory() && event.getClickedInventory().getItem(event.getSlot()) != null) {
            this.selected = event.getSlot();
            setUp();
            addEnchantment(this.selected);
            String teamName = super.getInventory().getItem(this.selected).getItemMeta().getDisplayName();
            TTRCore.getInstance().getTeamHandler().addPlayer(teamName, this.owner);
            if (lastTeam != null) {
                TTRCore.getInstance().getTeamHandler().removePlayer(teamName, this.owner);
            }
            this.lastTeam = teamName;
        }
        if (TTRCore.getInstance().enabled() && !TTRCore.getInstance().getCurrentMatch().isOnCourse()) {
            event.setCancelled(true);
        }
    }

    private void addEnchantment(int slot) {
        ItemStack selectedItem = super.getInventory().getItem(this.selected);
        selectedItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = selectedItem.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        selectedItem.setItemMeta(meta);
        super.setSlot(slot, selectedItem, null, null);
    }

}
