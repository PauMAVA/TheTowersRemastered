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

package me.PauMAVA.TTR.ui;

import io.netty.handler.codec.DecoderException;
import me.PauMAVA.TTR.util.ReflectionUtils;
import me.PauMAVA.TTR.util.TTRPrefix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TTRCustomTab extends BukkitRunnable {

    private String prefix = "";
    private String suffix = ChatColor.AQUA + "(c) 2019-2021" + ChatColor.BOLD + " PauMAVA" + ChatColor.RESET + "\n" + ChatColor.GREEN + "The Towers Remastered (TTR)";
    private int i = 1;

    @Override
    public void run() {
        switch (i) {
            case 1:
            case 3:
            case 5: {
                this.prefix = TTRPrefix.TTR_GAME + "";
                break;
            }
            case 2:
            case 4: {
                this.prefix = TTRPrefix.TTR_GAME_DARK + "";
                break;
            }
            case 8: {
                i = 1;
            }
        }
        i++;
        sendPacket();
    }

    private void sendPacket() {
        try {
            Object packet = ReflectionUtils.createNMSInstance("PacketPlayOutPlayerListHeaderFooter", List.of(), List.of());
            Field header = packet.getClass().getDeclaredField("header");
            Field footer = packet.getClass().getDeclaredField("footer");
            header.setAccessible(true);
            footer.setAccessible(true);
            Object headerChatComponentText = ReflectionUtils.createNMSInstance("ChatComponentText", List.of(String.class), List.of(this.prefix));
            Object footerChatComponentText = ReflectionUtils.createNMSInstance("ChatComponentText", List.of(String.class), List.of(this.suffix));
            header.set(packet, headerChatComponentText);
            footer.set(packet, footerChatComponentText);
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                ReflectionUtils.sendNMSPacketToPlayer(player, packet);
            }
        } catch (IllegalAccessException | NoSuchFieldException | DecoderException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
