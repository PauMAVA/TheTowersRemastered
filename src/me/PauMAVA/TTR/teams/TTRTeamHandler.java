package me.PauMAVA.TTR.teams;

import me.PauMAVA.TTR.match.TTRMatch;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TTRTeamHandler {

    private TTRMatch match = new TTRMatch();
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

    private TTRTeam getTeam(String teamIdentifier) {
        for(TTRTeam team: this.teams) {
            if(team.getIdentifier().equalsIgnoreCase(teamIdentifier)) {
                return team;
            }
        }
        return null;
    }
}
