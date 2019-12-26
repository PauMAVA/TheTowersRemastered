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
package me.PauMAVA.TTR;

import me.PauMAVA.TTR.match.TTRMatch;
import me.PauMAVA.TTR.util.EventListener;
import org.bukkit.plugin.java.JavaPlugin;

public class TTRCore extends JavaPlugin {

    private static TTRCore instance;
    private boolean enabled = false;
    private TTRMatch match;

    @Override
    public void onEnable() {
        instance = this;
        if(this.getConfig().getBoolean("enableOnStart")) {
            enabled = true;
        }
        this.match = new TTRMatch();
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static TTRCore getInstance() {
        return instance;
    }

    public boolean enabled() {
        return this.enabled;
    }

    public TTRMatch getCurrentMatch() {
        return this.match;
    }
}
