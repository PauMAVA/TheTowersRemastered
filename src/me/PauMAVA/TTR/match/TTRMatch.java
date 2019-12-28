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

package me.PauMAVA.TTR.match;

import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.teams.TTRTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TTRMatch {

    private MatchStatus status;
    private LootSpawner lootSpawner;
    private CageChecker checker;

    public TTRMatch(MatchStatus initialStatus) {
        status = initialStatus;
    }

    public boolean isOnCourse() {
        return this.status == MatchStatus.INGAME;
    }

    public void startMatch() {
        this.status = MatchStatus.INGAME;
        this.lootSpawner = new LootSpawner();
        this.checker = new CageChecker();
        this.checker.setCages(TTRCore.getInstance().getConfigManager().getTeamCages(), 2);
        this.checker.startChecking();
        this.lootSpawner.startSpawning();
        TTRCore.getInstance().getScoreboard().startScoreboardTask();
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            TTRTeam playerTeam = TTRCore.getInstance().getTeamHandler().getPlayerTeam(player);
            if(playerTeam == null) {
                continue;
            }
            player.teleport(TTRCore.getInstance().getConfigManager().getTeamSpawn(playerTeam.getIdentifier()));
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(TTRCore.getInstance().getConfigManager().getMaxHealth());
            player.setFoodLevel(20);
            player.setSaturation(20);
        }
    }

    public void endMatch(TTRTeam team) {
        this.status = MatchStatus.ENDED;
        this.lootSpawner.stopSpawning();
        TTRCore.getInstance().getScoreboard().stopScoreboardTask();
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
            ChatColor teamColor = TTRCore.getInstance().getConfigManager().getTeamColor(team.getIdentifier());
            player.sendTitle(teamColor + "" + ChatColor.BOLD + team.getIdentifier(), ChatColor.AQUA + "WINS!", 10, 100, 20);
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 1);
        }
    }

    public MatchStatus getStatus() {
        return this.status;
    }

}
