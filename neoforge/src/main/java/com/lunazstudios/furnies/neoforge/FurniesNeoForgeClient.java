package com.lunazstudios.furnies.neoforge;

import com.lunazstudios.furnies.Furnies;
import com.lunazstudios.furnies.client.FurniesClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Furnies.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class FurniesNeoForgeClient {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        FurniesClient.init();

    }
}
