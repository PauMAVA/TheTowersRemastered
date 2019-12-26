package me.PauMAVA.TTR.teams;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TTRTeam {

    private String identifier;
    private List<Player> players = new ArrayList<Player>();
    private int points = 0;

    public TTRTeam(String identifier, List<Player> players) {
        this.identifier = identifier;
        this.players = players;
    }

    public TTRTeam(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return this.points;
    }
}
