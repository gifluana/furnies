package com.lunazstudios.furnies.neoforge;

import com.lunazstudios.furnies.Furnies;
import com.lunazstudios.furnies.registry.neoforge.FRegistryImpl;
import com.lunazstudios.furnies.registry.neoforge.FTabsImpl;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Furnies.MOD_ID)
public final class FurniesNeoForge {
    public FurniesNeoForge(IEventBus bus) {
        Furnies.init();

        FRegistryImpl.BLOCKS.register(bus);
        FRegistryImpl.ITEMS.register(bus);
        FRegistryImpl.ENTITY_TYPES.register(bus);
        FRegistryImpl.BLOCK_ENTITY_TYPES.register(bus);
        FTabsImpl.register(bus);
    }
}
