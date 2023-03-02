package com.kakaouo.mods.tickrate.commands;

import com.kakaouo.mods.tickrate.KaTickrate;
import com.kakaouo.mods.tickrate.TickrateServer;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public enum TickrateCommand {
    ;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tickrate")
                .requires(s -> s.hasPermission(2))
                .then(Commands.argument("tps", FloatArgumentType.floatArg(0))
                        .executes(ctx -> {
                            float tps = FloatArgumentType.getFloat(ctx, "tps");
                            ctx.getSource().sendSuccess(Component.literal("Setting tickrate to " + tps), true);
                            setTickrate(tps);
                            return 0;
                        })
                        .then(Commands.literal("client").executes(ctx -> {
                            float tps = FloatArgumentType.getFloat(ctx, "tps");
                            ctx.getSource().sendSuccess(Component.literal("Setting client tickrate to " + tps), true);
                            setTickrateClient(tps);
                            return 0;
                        }))
                        .then(Commands.literal("server").executes(ctx -> {
                            float tps = FloatArgumentType.getFloat(ctx, "tps");
                            ctx.getSource().sendSuccess(Component.literal("Setting server tickrate to " + tps), true);
                            setTickrateServer(tps);
                            return 0;
                        }))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    float tps = FloatArgumentType.getFloat(ctx, "tps");
                                    ctx.getSource().sendSuccess(Component.literal("Setting server tickrate to " + tps + " for players"), true);
                                    setTickratePlayer(tps, EntityArgument.getPlayers(ctx, "targets"));
                                    return 0;
                                }))
                )
        );
    }

    private static void setTickratePlayer(float tickrate, Collection<ServerPlayer> players) {
        players.forEach(player->{
            TickrateServer.getInstance().updatePlayerTickrate(player, tickrate);
        });
    }

    private static void setTickrateServer(float tickrate) {
        TickrateServer.getInstance().updateServerTickrate(tickrate);
    }

    public static void setTickrate(float tickrate) {
        KaTickrate.CONFIG.setValue(tickrate);
        TickrateServer.getInstance().updateTickrate(tickrate);
    }

    public static void setTickrateClient(float tickrate) {
        TickrateServer.getInstance().updateAllClientTickrate(tickrate);
    }
}
