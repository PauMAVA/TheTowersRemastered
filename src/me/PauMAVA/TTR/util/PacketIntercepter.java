package me.PauMAVA.TTR.util;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketIntercepter {

    public void addPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler(){
            @Override
            public void channelRead(ChannelHandlerContext context, Object packet) {
                if(packet instanceof PacketPlayOutChat) {

                }

                try{
                    super.channelRead(context, packet);
                } catch (Exception e) {

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
        } catch (IllegalArgumentException ignored) {}
    }


}
