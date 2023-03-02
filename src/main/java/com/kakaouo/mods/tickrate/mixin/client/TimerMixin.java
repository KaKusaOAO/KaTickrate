package com.kakaouo.mods.tickrate.mixin.client;

import com.kakaouo.mods.tickrate.accessor.ITickrateDuck;
import net.minecraft.client.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Timer.class)
public class TimerMixin implements ITickrateDuck {

    @Mutable
    @Shadow @Final private float msPerTick;

    @Override
    public float getTickrate() {
        return 1000 / this.msPerTick;
    }

    @Override
    public void setTickrate(float tickrate) {
        this.msPerTick = 1000 / tickrate;
    }
}
