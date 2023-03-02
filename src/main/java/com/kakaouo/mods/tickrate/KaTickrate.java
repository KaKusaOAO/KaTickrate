package com.kakaouo.mods.tickrate;

import com.kakaouo.mods.tickrate.commands.TickrateCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

// https://github.com/ScribbleLP/TASTickratechanger-Light-Fabric
public class KaTickrate implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("KaTickrate");
    public static Config CONFIG;

    public static final ResourceLocation PACKET_CLIENT = createId("main");
    public static MinecraftServer serverInstance;

    public static ResourceLocation createId(String name) {
        return new ResourceLocation("katickrate", name);
    }

    @Override
    public void onInitialize() {
        new TickrateServer();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            TickrateCommand.register(dispatcher);
        });

        CONFIG = new Config(new File(FabricLoader.getInstance().getConfigDir().toFile(), "katickrate.cfg"));

        TickrateServer.DEFAULT_TICKRATE = CONFIG.getValue();
        ServerLifecycleEvents.SERVER_STARTED.register(s -> {
            serverInstance = s;
            TickrateServer.DEFAULT_TICKRATE = CONFIG.getValue();
            TickrateServer.getInstance().updateServerTickrate(TickrateServer.DEFAULT_TICKRATE);
        });
    }
}
