package com.lunazstudios.furnies.fabric;

import com.lunazstudios.furnies.Furnies;
import com.lunazstudios.furnies.registry.fabric.FTabsImpl;
import net.fabricmc.api.ModInitializer;

public final class FurniesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Furnies.init();
        FTabsImpl.register();
    }
}
