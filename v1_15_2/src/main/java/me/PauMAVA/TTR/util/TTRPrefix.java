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

import org.bukkit.ChatColor;

public enum TTRPrefix {

    TTR_GAME(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.RESET + ChatColor.GREEN + "" + ChatColor.BOLD + "The Towers" + ChatColor.RESET + ChatColor.GRAY + "" + ChatColor.BOLD + "]" + ChatColor.RESET + " "),
    TTR_GAME_DARK(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.RESET + ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "The Towers" + ChatColor.RESET + ChatColor.GRAY + "" + ChatColor.BOLD + "]" + ChatColor.RESET + " "),
    TTR_GLOBAL(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.RESET + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "GLOBAL" + ChatColor.RESET + ChatColor.GRAY + "" + ChatColor.BOLD + "]" + ChatColor.RESET + " "),
    TTR_TEAM(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.RESET + ChatColor.GREEN + "" + ChatColor.BOLD + "TEAM" + ChatColor.RESET + ChatColor.GRAY + "" + ChatColor.BOLD + "]" + ChatColor.RESET + " ");

    private String prefix;

    TTRPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return this.prefix;
    }

}
