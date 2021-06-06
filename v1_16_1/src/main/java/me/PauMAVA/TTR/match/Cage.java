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

import me.PauMAVA.TTR.teams.TTRTeam;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Cage {

    private Location location;
    private int effectiveRadius;
    private TTRTeam owner;

    public Cage(Location location, int effectiveRadius, TTRTeam owner) {
        this.location = location;
        this.effectiveRadius = effectiveRadius;
        this.owner = owner;
    }

    public boolean isInCage(Player player) {
        return location.distance(player.getLocation()) < effectiveRadius;
    }

    public Location getLocation() {
        return this.location;
    }

    public TTRTeam getOwner() {
        return this.owner;
    }

}
