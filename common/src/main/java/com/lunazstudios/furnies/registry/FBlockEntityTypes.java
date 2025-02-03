package com.lunazstudios.furnies.registry;

import com.lunazstudios.furnies.block.entity.CabinetBlockEntity;
import com.lunazstudios.furnies.block.entity.KitchenCabinetBlockEntity;
import com.lunazstudios.furnies.block.entity.KitchenDrawerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class FBlockEntityTypes {

    public static final Supplier<BlockEntityType<CabinetBlockEntity>> CABINET = FRegistry.registerBlockEntityType("cabinet",
            () -> FRegistry.createBlockEntityType(CabinetBlockEntity::new,
                    FBlocks.OAK_CABINET.get(),
                    FBlocks.SPRUCE_CABINET.get(),
                    FBlocks.BIRCH_CABINET.get(),
                    FBlocks.JUNGLE_CABINET.get(),
                    FBlocks.ACACIA_CABINET.get(),
                    FBlocks.DARK_OAK_CABINET.get(),
                    FBlocks.MANGROVE_CABINET.get(),
                    FBlocks.BAMBOO_CABINET.get(),
                    FBlocks.CHERRY_CABINET.get(),
                    FBlocks.WARPED_CABINET.get(),
                    FBlocks.CRIMSON_CABINET.get()
            ));

    public static final Supplier<BlockEntityType<KitchenCabinetBlockEntity>> KITCHEN_CABINET = FRegistry.registerBlockEntityType("kitchen_cabinet",
            () -> FRegistry.createBlockEntityType(KitchenCabinetBlockEntity::new,
                    FBlocks.OAK_KITCHEN_CABINET.get(),
                    FBlocks.SPRUCE_KITCHEN_CABINET.get(),
                    FBlocks.BIRCH_KITCHEN_CABINET.get(),
                    FBlocks.JUNGLE_KITCHEN_CABINET.get(),
                    FBlocks.ACACIA_KITCHEN_CABINET.get(),
                    FBlocks.DARK_OAK_KITCHEN_CABINET.get(),
                    FBlocks.MANGROVE_KITCHEN_CABINET.get(),
                    FBlocks.BAMBOO_KITCHEN_CABINET.get(),
                    FBlocks.CHERRY_KITCHEN_CABINET.get(),
                    FBlocks.WARPED_KITCHEN_CABINET.get(),
                    FBlocks.CRIMSON_KITCHEN_CABINET.get()
            ));

    public static final Supplier<BlockEntityType<KitchenDrawerBlockEntity>> KITCHEN_DRAWER = FRegistry.registerBlockEntityType("kitchen_drawer",
            () -> FRegistry.createBlockEntityType(KitchenDrawerBlockEntity::new,
                    FBlocks.OAK_KITCHEN_DRAWER.get(),
                    FBlocks.SPRUCE_KITCHEN_DRAWER.get(),
                    FBlocks.BIRCH_KITCHEN_DRAWER.get(),
                    FBlocks.JUNGLE_KITCHEN_DRAWER.get(),
                    FBlocks.ACACIA_KITCHEN_DRAWER.get(),
                    FBlocks.DARK_OAK_KITCHEN_DRAWER.get(),
                    FBlocks.MANGROVE_KITCHEN_DRAWER.get(),
                    FBlocks.BAMBOO_KITCHEN_DRAWER.get(),
                    FBlocks.CHERRY_KITCHEN_DRAWER.get(),
                    FBlocks.WARPED_KITCHEN_DRAWER.get(),
                    FBlocks.CRIMSON_KITCHEN_DRAWER.get()
            ));

    public static void init() {}
}
