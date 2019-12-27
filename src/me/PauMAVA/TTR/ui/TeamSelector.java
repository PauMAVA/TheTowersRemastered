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

package me.PauMAVA.TTR.ui;

import me.PauMAVA.TTR.TTRCore;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TeamSelector extends CustomUI {

    private Player owner;
    private int selected = -1;

    public TeamSelector(Player player) {
        super(27, "Team Selection");
        this.owner = player;
        setUp();
    }

    public void openSelector() {
        super.openUI(this.owner);
    }

    public void closeSelector() {
        super.closeUI(this.owner);
    }

    public void setUp() {
        int i = 0;
        for(String teamName: TTRCore.getInstance().getConfigManager().getTeamNames()) {
            setSlot(i, new ItemStack(Material.valueOf(TTRCore.getInstance().getConfigManager().getTeamColor(teamName).name() + "_WOOL"), 1), TTRCore.getInstance().getConfigManager().getTeamColor(teamName) + teamName, null);
            i++;
        }
    }

}
