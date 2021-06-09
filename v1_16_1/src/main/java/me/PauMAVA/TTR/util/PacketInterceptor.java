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

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import me.PauMAVA.TTR.TTRCore;
import me.PauMAVA.TTR.chat.TTRChatManager;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PacketInterceptor {

    private final TTRCore plugin;

    public PacketInterceptor(TTRCore plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext context, Object packet) {
                try {
                    if (plugin.enabled() && plugin.getCurrentMatch().isOnCourse()) {
                        Class<?> packetPlayInChatClazz = ReflectionUtils.getNMSClass("PacketPlayInChat");
                        if (packet.getClass().equals(packetPlayInChatClazz)) {
                            Object asPacketPlayInChat = packetPlayInChatClazz.cast(packet);
                            String message = ReflectionUtils.callMethod(asPacketPlayInChat, "b", List.of(), List.of(), String.class);
                            if (!message.startsWith("/")) {
                                TTRChatManager.sendMessage(player, message);
                            }
                        }
                    }
                    super.channelRead(context, packet);
                } catch (Exception e) {
                    TTRCore.getInstance().getLogger().warning("An error occurred on packet reading process!");
                    e.printStackTrace();
                }
            }
        };
        try {
            ChannelPipeline pipeline = ReflectionUtils.getPlayerChannel(player).pipeline();
            pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
        } catch (IllegalArgumentException | NoSuchFieldException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {}
    }

    public void removePlayer(Player player) {
        try {
           Channel channel = ReflectionUtils.getPlayerChannel(player);
           channel.eventLoop().submit(() -> {
                channel.pipeline().remove(player.getName());
                return null;
           });
        } catch (NoSuchFieldException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
