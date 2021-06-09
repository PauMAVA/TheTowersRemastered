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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectionUtils {

    private static String version = null;

    static {
        try {
            Class.forName("org.bukkit.Bukkit");
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            setField(ReflectionUtils.class, null, "version", version);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static Object getPlayerConnection(Player player) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class<?> CraftPlayer = getCraftbukkitClass("entity.CraftPlayer");
        Object asCraftPlayer = CraftPlayer.cast(player);
        Method getHandle = CraftPlayer.getMethod("getHandle");
        Object playerHandle = getHandle.invoke(asCraftPlayer);
        Field playerConnectionField = playerHandle.getClass().getDeclaredField("playerConnection");
        return playerConnectionField.get(playerHandle);
    }

    public static Channel getPlayerChannel(Player player) throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object playerConnection = getPlayerConnection(player);
        Field networkManagerField = playerConnection.getClass().getDeclaredField("networkManager");
        Object networkManager = networkManagerField.get(playerConnection);
        Field playerChannelField = networkManager.getClass().getDeclaredField("channel");
        Object playerChannel = playerChannelField.get(networkManager);
        if (playerChannel instanceof Channel) {
            return (Channel) playerChannel;
        }
        throw new NoSuchFieldException("No field channel with type io.netty.channel.Channel in player network manager...");
    }

    public static void sendNMSPacketToPlayer(Player player, Object packet) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Object playerConnection = getPlayerConnection(player);
        Class<?> nmsPacket = getNMSClass("Packet");
        Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket", nmsPacket);
        sendPacket.invoke(playerConnection, packet);
    }

    public static Class<?> getNMSClass(String clazz) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + version + "." + clazz);
    }

    public static Class<?> getCraftbukkitClass(String clazz) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + version + "." + clazz);
    }

    public static Object createNMSInstance(String clazz, List<Class<?>> paramTypes, List<Object> params) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> theClazz = getNMSClass(clazz);
        Constructor<?> constructor = theClazz.getDeclaredConstructor(paramTypes.toArray(new Class[0]));
        constructor.setAccessible(true);
        return constructor.newInstance(params.toArray());
    }

    public static void setField(Class<?> clazz, Object instance, String fieldName, Object value) throws IllegalAccessException, NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        boolean accessible = field.canAccess(instance);
        field.setAccessible(true);
        field.set(instance, value);
        field.setAccessible(accessible);
    }

    @SuppressWarnings("unchecked")
    public static <T> T callMethod(Object instance, String name, List<Class<?>> paramTypes, List<Object> parameters, Class<T> returnValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = instance.getClass().getDeclaredMethod(name, paramTypes.toArray(new Class[0]));
        method.setAccessible(true);
        return (T) method.invoke(instance, parameters);
    }

}
