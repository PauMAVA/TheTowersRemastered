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

package me.PauMAVA.TTR.commands;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.lang.PluginString;
import me.PauMAVA.TTR.util.TTRPrefix;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EnableDisableCommand implements CommandExecutor {

    private TTRCore plugin;

    public EnableDisableCommand(TTRCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender theSender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("ttrenable")) {
            plugin.getConfigManager().setEnableOnStart(true);
            theSender.sendMessage(TTRPrefix.TTR_GAME + "" + ChatColor.GREEN + PluginString.TTR_ENABLE_OUTPUT);
        } else if (label.equalsIgnoreCase("ttrdisable")) {
            plugin.getConfigManager().setEnableOnStart(false);
            theSender.sendMessage(TTRPrefix.TTR_GAME + "" + ChatColor.RED + PluginString.TTR_DISABLE_OUTPUT);
        }
        return false;
    }
}
