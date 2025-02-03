package com.lunazstudios.furnies.client;

import com.lunazstudios.furnies.client.renderer.entity.SeatRenderer;
import com.lunazstudios.furnies.registry.FEntityTypes;
import com.lunazstudios.furnies.registry.FRegistry;

public class FurniesClient {
    public static void init() {
        FRegistry.registerEntityRenderers(FEntityTypes.SEAT, SeatRenderer::new);
    }
}
