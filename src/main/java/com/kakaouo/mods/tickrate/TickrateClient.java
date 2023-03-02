package com.kakaouo.mods.tickrate;

import com.kakaouo.mods.tickrate.accessor.ITickrateDuck;
import com.kakaouo.mods.tickrate.commands.TickrateCommand;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class TickrateClient {
    private static TickrateClient INSTANCE;
    public static Logger LOGGER = LogManager.getLogger("KaTickrateClient");
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

    public TickrateClient() {
        INSTANCE = this;
    }

    public void updateClientTickrate(float tickrate, boolean log) {
        Minecraft mc = Minecraft.getInstance();
        if(log) LOGGER.info("Updating client tickrate to " + tickrate);
        TICKS_PER_SECOND = tickrate;
        if(CHANGE_SOUND) GAME_SPEED = tickrate / 20F;
        ((ITickrateDuck)mc).setTickrate(tickrate);
    }

    public static TickrateClient getInstance() {
        return INSTANCE;
    }
}
