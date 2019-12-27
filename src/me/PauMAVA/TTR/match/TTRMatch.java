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
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            TTRTeam playerTeam = TTRCore.getInstance().getTeamHandler().getPlayerTeam(player);
            if(playerTeam == null) {
                continue;
            }
            player.teleport(TTRCore.getInstance().getConfigManager().getTeamSpawn(playerTeam.getIdentifier()));
        }
    }

    public void endMatch() {
        this.status = MatchStatus.ENDED;
        this.lootSpawner.stopSpawning();
    }

    public MatchStatus getStatus() {
        return this.status;
    }

}
