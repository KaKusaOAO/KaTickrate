package com.kakaouo.mods.tickrate;

import com.kakaouo.mods.tickrate.commands.TickrateCommand;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class TickrateServer {
    private static TickrateServer INSTANCE;
    public static Logger LOGGER = LogManager.getLogger("KaTickrateServer");
    public static final String NETWORK_VERSION = "1";
    public static TickrateCommand COMMAND = null;
    public static File CONFIG_FILE = null;

    public static final String GAME_RULE = "tickrate";

    public static float DEFAULT_TICKRATE = 20;
    public static float TICKS_PER_SECOND = 20;
    public static long MILISECONDS_PER_TICK = 50L;
    public static float GAME_SPEED = 1;
    public static float MIN_TICKRATE = 0.1F;
    public static float MAX_TICKRATE = 1000;
    public static boolean SHOW_MESSAGES = true;
    public static boolean CHANGE_SOUND = true;

    public TickrateServer() {
        INSTANCE = this;
    }

    public void updateTickrate(float tickrate) {
        updateServerTickrate(tickrate);
        updateAllClientTickrate(tickrate);
    }

    public void updateServerTickrate(float tickrate) {
        TickrateServer.MILISECONDS_PER_TICK=(long) Math.floor(1000F/tickrate);
    }

    public static TickrateServer getInstance() {
        return INSTANCE;
    }

    public void updateAllClientTickrate(float tickrate) {
        FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeFloat(tickrate);
        KaTickrate.serverInstance.getPlayerList().getPlayers().forEach(player -> {
            ServerPlayNetworking.send(player, KaTickrate.PACKET_CLIENT, passedData);
        });
    }

    public void updatePlayerTickrate(ServerPlayer player, float tickrate) {
        FriendlyByteBuf passedData= new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeFloat(tickrate);
        ServerPlayNetworking.send(player, KaTickrate.PACKET_CLIENT, passedData);
    }
}
