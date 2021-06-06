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

package me.PauMAVA.TTR.util;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.chat.TTRChatManager;
import net.minecraft.server.v1_16_R1.PacketPlayInChat;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketIntercepter {

    public void addPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext context, Object packet) {
                if (TTRCore.getInstance().enabled() && TTRCore.getInstance().getCurrentMatch().isOnCourse() && packet instanceof PacketPlayInChat && !((PacketPlayInChat) packet).b().startsWith("/")) {
                    TTRChatManager.sendMessage(player, ((PacketPlayInChat) packet).b());
                }
                try {
                    super.channelRead(context, packet);
                } catch (Exception e) {
                    TTRCore.getInstance().getLogger().warning("An error occurred on packet reading process!");
                    e.printStackTrace();
                }
            }

            @Override
            public void write(ChannelHandlerContext context, Object packet, ChannelPromise promise) {

            }
        };
        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        try {
            pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
        } catch (IllegalArgumentException ignored) {
        }
    }


}
