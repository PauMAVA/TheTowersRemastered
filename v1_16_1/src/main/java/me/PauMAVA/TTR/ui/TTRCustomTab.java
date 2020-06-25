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

import io.netty.handler.codec.DecoderException;
import me.PauMAVA.TTR.util.TTRPrefix;
import net.minecraft.server.v1_16_R1.ChatComponentText;
import net.minecraft.server.v1_16_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

public class TTRCustomTab extends BukkitRunnable {

    private String prefix = "";
    private String suffix = ChatColor.AQUA + "(c) 2019-2020" + ChatColor.BOLD + " PauMAVA" + ChatColor.RESET + "\n" + ChatColor.GREEN + "The Towers Remastered (TTR)";
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
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field header = packet.getClass().getDeclaredField("header");
            Field footer = packet.getClass().getDeclaredField("footer");
            header.setAccessible(true);
            footer.setAccessible(true);
            header.set(packet, new ChatComponentText(this.prefix));
            footer.set(packet, new ChatComponentText(this.suffix));
            for(Player player: Bukkit.getServer().getOnlinePlayers()) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        } catch (IllegalAccessException | NoSuchFieldException | DecoderException e) {
            e.printStackTrace();
        }
    }
}
