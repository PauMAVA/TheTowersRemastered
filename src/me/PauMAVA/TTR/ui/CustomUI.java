package me.PauMAVA.TTR.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomUI {

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
        if(player.getOpenInventory().equals(this.inventory)) {
            player.closeInventory();
        }
    }

    public void setSlot(int id, ItemStack item, @Nullable String title, @Nullable String lore) {
        if(title == null) {
            this.inventory.setItem(id, item);
            return;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(title);
        if(lore != null) {
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

}
