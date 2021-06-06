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

package me.PauMAVA.TTR.match;

import me.PauMAVA.TTR.TTRCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class LootSpawner {

    private List<Location> ironLocations;
    private List<Location> xpLocations;
    private long ironFrequency = 150;
    private long xpFrequency = 150;
    private int ironTaskPID;
    private int xpTaskPID;

    public LootSpawner() {
        this.ironLocations = TTRCore.getInstance().getConfigManager().getIronSpawns();
        this.xpLocations = TTRCore.getInstance().getConfigManager().getXPSpawns();
    }

    public void startSpawning() {
        setIronTask();
        setXpTask();
    }

    public void stopSpawning() {
        Bukkit.getScheduler().cancelTask(this.ironTaskPID);
        Bukkit.getScheduler().cancelTask(this.xpTaskPID);
    }

    private void setIronTask() {
        this.ironTaskPID = new BukkitRunnable() {
            @Override
            public void run() {
                for (Location location : ironLocations) {
                    Location copy = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
                    copy.add(location.getX() > 0 ? 0.5 : 0.5, 0.0, location.getZ() > 0 ? 0.5 : -0.5);
                    location.getWorld().dropItem(copy, new ItemStack(Material.IRON_INGOT, 1));
                }
            }
        }.runTaskTimer(TTRCore.getInstance(), 0L, this.ironFrequency).getTaskId();
    }

    private void setXpTask() {
        this.xpTaskPID = new BukkitRunnable() {
            @Override
            public void run() {
                for (Location location : xpLocations) {
                    Location copy = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
                    copy.add(location.getX() > 0 ? 0.5 : 0.5, 0.0, location.getZ() > 0 ? 0.5 : -0.5);
                    location.getWorld().spawnEntity(copy, EntityType.THROWN_EXP_BOTTLE);
                }
            }
        }.runTaskTimer(TTRCore.getInstance(), 0L, this.xpFrequency).getTaskId();
    }

}
