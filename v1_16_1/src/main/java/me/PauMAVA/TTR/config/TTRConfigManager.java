/*
 *  TheTowersRemastered (TTR)
 *  Copyright (c) 2019-2021  Pau Machetti Vallverdú
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.PauMAVA.TTR.config;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.teams.TTRTeam;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.*;

public class TTRConfigManager {

    private FileConfiguration configuration;
    private World world;
    private ConfigurationSection teamsSection;
    private ConfigurationSection matchSection;
    private ConfigurationSection mapSection;
    private ConfigurationSection autoStartSection;

    public TTRConfigManager(FileConfiguration configuration) {
        this.configuration = configuration;
        this.world = TTRCore.getInstance().getServer().getWorlds().get(0);
        if (!new File(TTRCore.getInstance().getDataFolder() + "/config.yml").exists()) {
            setUpFile();
            saveConfig();
        } else {
            this.teamsSection = this.configuration.getConfigurationSection("teams");
            this.mapSection = this.configuration.getConfigurationSection("map");
            this.matchSection = this.configuration.getConfigurationSection("match");
            this.autoStartSection = this.configuration.getConfigurationSection("autostart");
        }
    }

    public int getMaxPoints() {
        return this.matchSection.getInt("maxpoints");
    }

    public int getMaxHealth() {
        return this.matchSection.getInt("maxhealth");
    }

    public int getTime() {
        return this.matchSection.getInt("time");
    }

    public String getWeather() {
        return this.matchSection.getString("weather");
    }

    public Location getLobbyLocation() {
        return this.mapSection.getLocation("lobby");
    }

    public List<Location> getIronSpawns() {
        return (List<Location>) this.mapSection.getList("ironspawns");
    }

    public List<Location> getXPSpawns() {
        return (List<Location>) this.mapSection.getList("xpspawns");
    }

    public int getTeamCount() {
        return this.teamsSection.getKeys(false).size();
    }

    public Set<String> getTeamNames() {
        return this.teamsSection.getKeys(false);
    }

    private ConfigurationSection getTeam(String teamName) {
        for (String key : this.teamsSection.getKeys(false)) {
            if (key.equalsIgnoreCase(teamName)) {
                return this.teamsSection.getConfigurationSection(key);
            }
        }
        return null;
    }

    public ChatColor getTeamColor(String teamName) {
        return ChatColor.valueOf(getTeam(teamName).getString("color"));
    }

    public Location getTeamSpawn(String teamName) {
        return getTeam(teamName).getLocation("spawn");
    }

    public Location getTeamCage(String teamName) {
        return getTeam(teamName).getLocation("cage");
    }

    public HashMap<Location, TTRTeam> getTeamCages() {
        HashMap<Location, TTRTeam> cages = new HashMap<Location, TTRTeam>();
        for (String teamName : getTeamNames()) {
            cages.put(getTeamCage(teamName), TTRCore.getInstance().getTeamHandler().getTeam(teamName));
        }
        return cages;
    }

    public boolean isEnabled() {
        return this.configuration.getBoolean("enable_on_start");
    }

    public void setEnableOnStart(boolean value) {
        this.configuration.set("enable_on_start", value);
        saveConfig();
    }

    public String getLocale() {
        return this.configuration.getString("locale");
    }

    private void saveConfig() {
        TTRCore.getInstance().saveConfig();
    }

    public void resetFile() {
        setUpFile();
    }

    private void setUpFile() {
        this.configuration.addDefault("enable_on_start", false);
        this.configuration.addDefault("locale", "en");
        this.autoStartSection = this.configuration.createSection("autostart");
        autoStartSection.addDefault("enabled", true);
        autoStartSection.addDefault("count", 4);
        this.matchSection = this.configuration.createSection("match");
        matchSection.addDefault("time", 10000);
        matchSection.addDefault("weather", "CLEAR");
        matchSection.addDefault("maxpoints", 10);
        matchSection.addDefault("maxhealth", 20);
        this.mapSection = this.configuration.createSection("map");
        mapSection.addDefault("lobby", new Location(this.world, 0, 207, 1014));
        mapSection.addDefault("ironspawns", new ArrayList<Location>(Arrays.asList(new Location(this.world, -0, 206, 1138))));
        mapSection.addDefault("xpspawns", new ArrayList<Location>(Arrays.asList(new Location(this.world, -0, 206, 1166))));
        this.teamsSection = this.configuration.createSection("teams");
        ConfigurationSection team1section = teamsSection.createSection("Red Team");
        ConfigurationSection team2section = teamsSection.createSection("Blue Team");
        team1section.addDefault("color", "RED");
        team2section.addDefault("color", "BLUE");
        team1section.addDefault("spawn", new Location(this.world, 84, 192, 1152));
        team2section.addDefault("spawn", new Location(this.world, -83, 192, 1152));
        team1section.addDefault("cage", new Location(this.world, 84, 200, 1152));
        team2section.addDefault("cage", new Location(this.world, -83, 200, 1152));
        this.configuration.options().copyDefaults(true);
    }
}