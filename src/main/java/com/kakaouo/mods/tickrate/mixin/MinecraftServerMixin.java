package com.kakaouo.mods.tickrate.mixin;

import com.kakaouo.mods.tickrate.TickrateServer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @ModifyConstant(method = "runServer", constant = @Constant(longValue = 50L))
    private long serverTickWaitTime(long ignored) {
        return TickrateServer.MILISECONDS_PER_TICK;
    }
}
