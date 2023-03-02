package com.kakaouo.mods.tickrate.mixin.client;

import com.kakaouo.mods.tickrate.accessor.ITickrateDuck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public class MinecraftMixin implements ITickrateDuck {
    @Shadow @Final private Timer timer;

    @Override
    public float getTickrate() {
        return ((ITickrateDuck) this.timer).getTickrate();
    }

    @Override
    public void setTickrate(float tickrate) {
        ((ITickrateDuck) this.timer).setTickrate(tickrate);
    }
}
