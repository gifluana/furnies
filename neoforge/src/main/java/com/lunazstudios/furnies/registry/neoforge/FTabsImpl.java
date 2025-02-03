package com.lunazstudios.furnies.registry.neoforge;

import com.lunazstudios.furnies.Furnies;
import com.lunazstudios.furnies.registry.FBlocks;
import com.lunazstudios.furnies.registry.FRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;

import java.util.function.Supplier;

public class FTabsImpl {
    public static final Supplier<CreativeModeTab> CF_TAB = FRegistryImpl.MOD_TABS.register(Furnies.MOD_ID, () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(FBlocks.YELLOW_SOFA.get().asItem()))
            .title(Component.translatable("item_group." + Furnies.MOD_ID + ".tab"))
            .displayItems(((parameters, output) -> {
                output.acceptAll(FRegistry.getAllModItems());
            })).build());

    public static void register(IEventBus eventBus) {
        FRegistryImpl.MOD_TABS.register(eventBus);
    }
}
