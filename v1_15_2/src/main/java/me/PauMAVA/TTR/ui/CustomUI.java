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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CustomUI {

    private Inventory inventory;
    private String title;
    private int size;

    CustomUI(int size, String title) {
        this.inventory = Bukkit.getServer().createInventory(null, size, title);
    }

    void openUI(Player player) {
        player.openInventory(this.inventory);
    }

    void closeUI(Player player) {
        if (player.getOpenInventory().equals(this.inventory)) {
            player.closeInventory();
        }
    }

    public void setSlot(int id, ItemStack item, @Nullable String title, @Nullable String lore) {
        if (title == null) {
            this.inventory.setItem(id, item);
            return;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(title);
        if (lore != null) {
            meta.setLore(new ArrayList<String>(Arrays.asList(lore)));
        }
        item.setItemMeta(meta);
        this.inventory.setItem(id, item);
    }

    public void clearSlot(int id) {
        this.inventory.clear(id);
    }

    public void clearUI() {
        this.inventory.clear();
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @EventHandler
    abstract void onInventoryClick(InventoryClickEvent event);

}
