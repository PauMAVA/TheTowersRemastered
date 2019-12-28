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

import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

public class TTRCustomTab extends BukkitRunnable {

    private String prefix;
    private String suffix;
    private int i;

    @Override
    public void run() {
        sendPacket();
    }

    private void sendPacket() {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field header = packet.getClass().getDeclaredField("header");
            Field footer = packet.getClass().getDeclaredField("footer");
            header.setAccessible(true);
            footer.setAccessible(true);
            header.set(packet, this.prefix);
            footer.set(packet, this.suffix);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
