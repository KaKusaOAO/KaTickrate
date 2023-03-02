package com.kakaouo.mods.tickrate.client;

import com.kakaouo.mods.tickrate.KaTickrate;
import com.kakaouo.mods.tickrate.TickrateClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class KaTickrateClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(KaTickrate.PACKET_CLIENT, (client, handler, buf, responseSender) -> {
            float tickrate = buf.readFloat();
            client.execute(() -> {
                TickrateClient.getInstance().updateClientTickrate(tickrate, false);
            });
        });
    }
}
