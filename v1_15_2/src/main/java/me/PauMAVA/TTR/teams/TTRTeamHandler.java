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

package me.PauMAVA.TTR.teams;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.match.TTRMatch;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TTRTeamHandler {

    private TTRMatch match = TTRCore.getInstance().getCurrentMatch();
    private List<TTRTeam> teams = new ArrayList<TTRTeam>();

    public void setUpDefaultTeams() {
        for (String team : TTRCore.getInstance().getConfigManager().getTeamNames()) {
            this.teams.add(new TTRTeam(team));
        }
    }

    public boolean addPlayerToTeam(Player player, String teamIdentifier) {
        TTRTeam team = getTeam(teamIdentifier);
        if (team == null) {
            return false;
        }
        team.addPlayer(player);
        return true;
    }

    public boolean removePlayerFromTeam(Player player, String teamIdentifier) {
        TTRTeam team = getTeam(teamIdentifier);
        if (team == null) {
            return false;
        }
        team.removePlayer(player);
        return true;
    }

    public TTRTeam getPlayerTeam(Player player) {
        for (TTRTeam team : this.teams) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }

    public TTRTeam getTeam(String teamIdentifier) {
        for (TTRTeam team : this.teams) {
            teamIdentifier = ChatColor.stripColor(teamIdentifier);
            if (teamIdentifier.contentEquals(team.getIdentifier())) {
                return team;
            }
        }
        return null;
    }

    public List<TTRTeam> getTeams() {
        return this.teams;
    }

    public void addPlayer(String teamIdentifier, Player player) {
        getTeam(teamIdentifier).addPlayer(player);
    }

    public void removePlayer(String teamIdentifier, Player player) {
        getTeam(teamIdentifier).removePlayer(player);
    }
}
