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

package me.PauMAVA.TTR.teams;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.match.TTRMatch;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TTRTeamHandler {

    private TTRMatch match = TTRCore.getInstance().getCurrentMatch();
    private List<TTRTeam> teams = new ArrayList<TTRTeam>();

    public void setUpDefaultTeams() {
        teams.add(new TTRTeam("red"));
        teams.add(new TTRTeam("blue"));
    }

    public void setUpCustomTeams(List<TTRTeam> customTeams) {
        this.teams = customTeams;
    }

    public boolean addPlayerToTeam(Player player, String teamIdentifier) {
        TTRTeam team = getTeam(teamIdentifier);
        if(team == null) {
            return false;
        }
        team.addPlayer(player);
        return true;
    }

    public boolean removePlayerFromTeam(Player player, String teamIdentifier) {
        TTRTeam team = getTeam(teamIdentifier);
        if(team == null) {
            return false;
        }
        team.removePlayer(player);
        return true;
    }

    public TTRTeam getPlayerTeam(Player player) {
        for(TTRTeam team: this.teams) {
            if(team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }

    private TTRTeam getTeam(String teamIdentifier) {
        for(TTRTeam team: this.teams) {
            if(team.getIdentifier().equalsIgnoreCase(teamIdentifier)) {
                return team;
            }
        }
        return null;
    }
}
