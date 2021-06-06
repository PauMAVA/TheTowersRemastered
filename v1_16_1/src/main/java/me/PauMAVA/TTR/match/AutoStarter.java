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

package me.PauMAVA.TTR.match;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.util.XPBarTimer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AutoStarter {

    private TTRCore plugin;

    private boolean enabled;

    private int target;

    private List<Player> queue = new ArrayList<>();

    public AutoStarter(TTRCore plugin, FileConfiguration configuration) {
        this.plugin = plugin;
        this.target = configuration.getInt("autostart.count");
        this.enabled = configuration.getBoolean("autostart.enabled");
    }

    public void addPlayerToQueue(Player player) {
        if (enabled) {
            if (!isPlayerInQueue(player)) {
                queue.add(player);
            }
            checkStartGame();
        }
    }

    public void removePlayerFromQueue(Player player) {
        if (enabled) {
            removePlayerFromList(player);
        }
    }

    private void checkStartGame() {
        if (enabled && target <= queue.size()) {
            try {
                new XPBarTimer(20, plugin.getCurrentMatch().getClass().getMethod("startMatch")).runTaskTimer(plugin, 0L, 20L);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void removePlayerFromList(Player player) {
        queue.removeIf(p -> player.getUniqueId().equals(p.getUniqueId()));
    }

    private boolean isPlayerInQueue(Player player) {
        for (Player p : queue) {
            if (player.getUniqueId().equals(p.getUniqueId())) {
                return true;
            }
        }
        return false;
    }


}
