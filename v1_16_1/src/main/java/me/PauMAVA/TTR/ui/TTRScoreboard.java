package me.PauMAVA.TTR.ui;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.lang.PluginString;
import me.PauMAVA.TTR.teams.TTRTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class TTRScoreboard {

    private ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private Scoreboard ttrScoreboard;
    private Objective kills;
    private Objective health;
    private Objective points;
    private Score totalTime;
    private int taskPID;

    public TTRScoreboard() {
        this.ttrScoreboard = this.scoreboardManager.getNewScoreboard();
        this.kills = this.ttrScoreboard.registerNewObjective("kills", "dummy", ChatColor.LIGHT_PURPLE + "kills");
        this.kills.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        this.health = this.ttrScoreboard.registerNewObjective("showhealth", "health", ChatColor.RED + "❤");
        this.health.setDisplaySlot(DisplaySlot.BELOW_NAME);
        this.points = this.ttrScoreboard.registerNewObjective("points", "dummy", ChatColor.AQUA + "" + ChatColor.BOLD + "Points");
        this.points.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void refreshScoreboard() {
        updatePoints();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setScoreboard(this.ttrScoreboard);
            this.kills.getScore(player.getName()).setScore(TTRCore.getInstance().getCurrentMatch().getKills(player));
        }
    }

    public void removeScoreboard() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setScoreboard(this.scoreboardManager.getMainScoreboard());
        }
    }

    public void startScoreboardTask() {
        this.taskPID = new BukkitRunnable() {
            private int i = 0;

            @Override
            public void run() {
                updatePoints();
                points.getScore(ChatColor.DARK_GRAY + "§m                         ").setScore(-1);
                if (totalTime != null) {
                    ttrScoreboard.resetScores(totalTime.getEntry());
                }
                totalTime = points.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + PluginString.TOTAL_TIME_LABEL + ChatColor.GRAY + prettyTime(i));
                totalTime.setScore(-2);
                i++;
                refreshScoreboard();
            }

            private String prettyTime(int i) {
                String elapsedMinutes, elapsedSeconds;
                if ((((i % 86400) % 3600) / 60) < 10) {
                    elapsedMinutes = "0" + ((i % 86400) % 3600) / 60;
                } else {
                    elapsedMinutes = "" + ((i % 86400) % 3600) / 60;
                }
                if ((((i % 86400) % 3600) % 60) < 10) {
                    elapsedSeconds = "0" + ((i % 86400) % 3600) % 60;
                } else {
                    elapsedSeconds = "" + ((i % 86400) % 3600) % 60;
                }
                return (i % 86400) / 3600 + ":" + elapsedMinutes + ":" + elapsedSeconds;
            }
        }.runTaskTimer(TTRCore.getInstance(), 0L, 20L).getTaskId();
        refreshScoreboard();
    }

    private void updatePoints() {
        for (TTRTeam team : TTRCore.getInstance().getTeamHandler().getTeams()) {
            ChatColor teamColor = TTRCore.getInstance().getConfigManager().getTeamColor(team.getIdentifier());
            points.getScore(teamColor + "" + ChatColor.BOLD + team.getIdentifier()).setScore(team.getPoints());
        }
    }

    public void stopScoreboardTask() {
        Bukkit.getScheduler().cancelTask(this.taskPID);
    }

}
