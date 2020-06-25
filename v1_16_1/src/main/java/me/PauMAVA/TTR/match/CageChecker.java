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
import me.PauMAVA.TTR.ui.TTRScoreboard;
import me.PauMAVA.TTR.util.TTRPrefix;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CageChecker {

    private List<Cage> cages = new ArrayList<Cage>();
    private int checkerTaskPID;


    public void startChecking() {
        this.checkerTaskPID = new BukkitRunnable(){
            @Override
            public void run() {
                for(Player p: Bukkit.getServer().getOnlinePlayers()) {
                    for(Cage cage: cages) {
                        Location particleLocation = new Location(cage.getLocation().getWorld(), cage.getLocation().getBlockX(), cage.getLocation().getBlockY() + 1, cage.getLocation().getBlockZ());
                        particleLocation.add(particleLocation.getX() > 0 ? 0.5 : -0.5, 0.0, particleLocation.getZ() > 0 ? 0.5 : -0.5);
                        cage.getLocation().getWorld().spawnParticle(Particle.SPELL, particleLocation, 100);
                        if(cage.isInCage(p) && TTRCore.getInstance().getTeamHandler().getPlayerTeam(p) != null) {
                            if(cage.getOwner().equals(TTRCore.getInstance().getTeamHandler().getPlayerTeam(p))) {
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 10, 1);
                                p.sendMessage(TTRPrefix.TTR_GAME + "" + ChatColor.RED + "You can't do that!");
                                p.teleport(TTRCore.getInstance().getConfigManager().getTeamSpawn(TTRCore.getInstance().getTeamHandler().getPlayerTeam(p).getIdentifier()));
                            } else {
                                cage.getLocation().getWorld().strikeLightningEffect(cage.getLocation());
                                playerOnCage(p);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(TTRCore.getInstance(), 0L, 10L).getTaskId();
    }

    public void stopChecking() {
        Bukkit.getScheduler().cancelTask(this.checkerTaskPID);
    }

    private void playerOnCage(Player player) {
        TTRTeam playersTeam = TTRCore.getInstance().getTeamHandler().getPlayerTeam(player);
        player.teleport(TTRCore.getInstance().getConfigManager().getTeamSpawn(playersTeam.getIdentifier()));
        playersTeam.addPoints(1);
        TTRCore.getInstance().getScoreboard().refreshScoreboard();
        Bukkit.broadcastMessage(TTRPrefix.TTR_GAME + "" + ChatColor.GRAY + player.getName() + " has scored a point!");
        for(Player p: Bukkit.getServer().getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
        }
        if(playersTeam.getPoints() >= TTRCore.getInstance().getConfigManager().getMaxPoints()) {
            TTRCore.getInstance().getCurrentMatch().endMatch(playersTeam);
        }
    }

    public void setCages(HashMap<Location, TTRTeam> cages, int effectiveRadius) {
        for(Location cage: cages.keySet()) {
            this.cages.add(new Cage(cage, effectiveRadius, cages.get(cage)));
        }
    }
}
