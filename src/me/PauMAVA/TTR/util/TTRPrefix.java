package me.PauMAVA.TTR.util;

import org.bukkit.ChatColor;

public enum TTRPrefix {

    TTR_GAME(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.RESET + ChatColor.GREEN + "" + ChatColor.BOLD + "The Towers" + ChatColor.RESET + ChatColor.GRAY + "" + ChatColor.BOLD + "]" + ChatColor.RESET + " ");

    private String prefix;

    TTRPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return this.prefix;
    }

}
