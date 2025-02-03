package com.lunazstudios.furnies.registry.fabric;

import com.lunazstudios.furnies.Furnies;
import com.lunazstudios.furnies.registry.FBlocks;
import com.lunazstudios.furnies.registry.FRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FTabsImpl {
    public static final CreativeModeTab CF_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Furnies.id("tab"),
            FabricItemGroup.builder().title(Component.translatable("item_group." + Furnies.MOD_ID + ".tab"))
                    .icon(() -> new ItemStack(FBlocks.YELLOW_SOFA.get().asItem())).displayItems((parameters, output) -> {
                        output.acceptAll(FRegistry.getAllModItems());
                    }).build());

    public static void register() {}
}
