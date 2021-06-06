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

package me.PauMAVA.TTR.world;

import me.PauMAVA.TTR.TTRCore;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;

public class TTRWorldHandler {

    private TTRCore plugin;

    private World matchWorld;

    private Difficulty originalDifficulty;

    public TTRWorldHandler(TTRCore plugin, World matchWorld) {
        this.plugin = plugin;
        this.matchWorld = matchWorld;
        this.originalDifficulty = matchWorld.getDifficulty();
    }

    public void setUpWorld() {
        this.matchWorld.setSpawnLocation(plugin.getConfigManager().getLobbyLocation());
    }

    public void configureWeather() {
        setWeatherCycle(false);
        String weatherType = plugin.getConfigManager().getWeather();
        if (weatherType.equalsIgnoreCase("rain") || weatherType.equalsIgnoreCase("thunder")) {
            this.matchWorld.setStorm(true);
            if (weatherType.equalsIgnoreCase("thunder")) {
                this.matchWorld.setThundering(true);
            }
        } else if (weatherType.equalsIgnoreCase("clear")) {
            this.matchWorld.setStorm(false);
            this.matchWorld.setThundering(false);
        }
    }

    public void configureTime() {
        setDayLightCycle(false);
        this.matchWorld.setTime(plugin.getConfigManager().getTime());
    }

    public void enableDayLightCycle() {
        setDayLightCycle(true);
    }

    public void enableWeatherCycle() {
        setWeatherCycle(true);
    }

    public void restoreDifficulty() {
        matchWorld.setDifficulty(originalDifficulty);
    }

    public void setWorldDifficulty(Difficulty difficulty) {
        matchWorld.setDifficulty(difficulty);
    }

    private void setWeatherCycle(boolean value) {
        this.matchWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, value);
    }

    private void setDayLightCycle(boolean value) {
        this.matchWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, value);
    }


}
