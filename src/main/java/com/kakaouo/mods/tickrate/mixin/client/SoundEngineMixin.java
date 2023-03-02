package com.kakaouo.mods.tickrate.mixin.client;

import com.kakaouo.mods.tickrate.TickrateServer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
    @Inject(method = "calculatePitch", at = @At("HEAD"), cancellable = true)
    private void redoGetAdjustedPitch(SoundInstance soundInstance, CallbackInfoReturnable<Float> ci) {
        ci.setReturnValue(TickrateServer.GAME_SPEED * Mth.clamp(soundInstance.getPitch(), 0.5F, 2.0F));
        ci.cancel();
    }
}
