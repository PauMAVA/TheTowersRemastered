package me.PauMAVA.TTR.util;

import com.mojang.datafixers.kinds.IdF;
import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.ui.TeamSelector;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(TTRCore.getInstance().enabled()) {
            Inventory playerInventory = event.getPlayer().getInventory();
            playerInventory.clear();
            playerInventory.setItem(0, new ItemStack(Material.BLACK_BANNER));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(TTRCore.getInstance().enabled() && !TTRCore.getInstance().getCurrentMatch().isOnCourse()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropEvent(PlayerDropItemEvent event) {
        if(TTRCore.getInstance().enabled() && !TTRCore.getInstance().getCurrentMatch().isOnCourse()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerClickEvent(PlayerInteractEvent event) {
        if(TTRCore.getInstance().enabled() && !TTRCore.getInstance().getCurrentMatch().isOnCourse()) {
            event.setCancelled(true);
            if(event.getItem() != null && event.getItem().getType() == Material.BLACK_BANNER) {
                new TeamSelector(event.getPlayer()).openSelector();
            }
        }
    }

    @EventHandler
    public void placeBlockEvent(BlockPlaceEvent event) {
        if(TTRCore.getInstance().enabled() && !TTRCore.getInstance().getCurrentMatch().isOnCourse()) {
            event.getPlayer().sendMessage(TTRPrefix.TTR_GAME + "" + ChatColor.GRAY + "You cannot break that block!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void breakBlockEvent(BlockBreakEvent event) {
        if(TTRCore.getInstance().enabled() && !TTRCore.getInstance().getCurrentMatch().isOnCourse()) {
            event.getPlayer().sendMessage(TTRPrefix.TTR_GAME + "" + ChatColor.GRAY + "You cannot place a block there!");
            event.setCancelled(true);
        }
    }


}
