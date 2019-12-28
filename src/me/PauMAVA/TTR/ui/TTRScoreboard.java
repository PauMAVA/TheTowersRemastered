package me.PauMAVA.TTR.ui;

import me.PauMAVA.TTR.TTRCore;
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
    private int taskPID;

    public TTRScoreboard() {
        this.ttrScoreboard = this.scoreboardManager.getNewScoreboard();
        this.kills = this.ttrScoreboard.registerNewObjective("kills", "PLAYER_KILLS", ChatColor.LIGHT_PURPLE + "kills");
        this.kills.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        this.health = this.ttrScoreboard.registerNewObjective("health", "HEALTH", "Health", RenderType.HEARTS);
        this.health.setDisplaySlot(DisplaySlot.BELOW_NAME);
        this.points = this.ttrScoreboard.registerNewObjective("points", "DUMMY", "Points");
        this.points.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void refreshScoreboard() {
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            player.setScoreboard(this.ttrScoreboard);
        }
    }

    public void removeScoreboard() {
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            player.setScoreboard(this.scoreboardManager.getMainScoreboard());
        }
    }

    public void startScoreboardTask() {
        this.taskPID = new BukkitRunnable(){
            private int i = 0;
            @Override
            public void run() {
                for(TTRTeam team: TTRCore.getInstance().getTeamHandler().getTeams()) {
                    points.getScore(team.getIdentifier()).setScore(team.getPoints());
                }
                points.getScore("------------------").setScore(-1);
                points.getScore(ChatColor.GREEN + "Total time: ").setScore(-2);
                i++;
            }

            private void prettyTime(int i) {

            }
        }.runTaskTimer(TTRCore.getInstance(), 0L, 20L).getTaskId();
    }

    public void stopScoreboardTask() {
        Bukkit.getScheduler().cancelTask(this.taskPID);
    }

}
