package com.lunazstudios.furnies.registry;

import com.lunazstudios.furnies.block.*;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

/**
 * Original Author: StarfishStudios
 * Project: Another Furniture
 */
public class FBlocks {

    public static class Properties {
        public static BlockBehaviour.Properties WOOD = Block.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS);
        public static BlockBehaviour.Properties MARBLE = Block.Properties.of().strength(1.5F, 6.0F).sound(SoundType.CALCITE).mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASEDRUM);
        public static BlockBehaviour.Properties CONCRETE = Block.Properties.of().strength(1.8F).sound(SoundType.STONE).mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASEDRUM);
    }

    public static final Supplier<Block> WHITE_SOFA = registerBlock("white_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> LIGHT_GRAY_SOFA = registerBlock("light_gray_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> GRAY_SOFA = registerBlock("gray_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> BLACK_SOFA = registerBlock("black_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> BROWN_SOFA = registerBlock("brown_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> RED_SOFA = registerBlock("red_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> ORANGE_SOFA = registerBlock("orange_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> YELLOW_SOFA = registerBlock("yellow_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> LIME_SOFA = registerBlock("lime_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> GREEN_SOFA = registerBlock("green_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> CYAN_SOFA = registerBlock("cyan_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> LIGHT_BLUE_SOFA = registerBlock("light_blue_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> BLUE_SOFA = registerBlock("blue_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> PURPLE_SOFA = registerBlock("purple_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> MAGENTA_SOFA = registerBlock("magenta_sofa", () -> new SofaBlock(Properties.WOOD));
    public static final Supplier<Block> PINK_SOFA = registerBlock("pink_sofa", () -> new SofaBlock(Properties.WOOD));

    public static final Supplier<Block> OAK_CHAIR = registerBlock("oak_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> SPRUCE_CHAIR = registerBlock("spruce_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> BIRCH_CHAIR = registerBlock("birch_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> JUNGLE_CHAIR = registerBlock("jungle_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> ACACIA_CHAIR = registerBlock("acacia_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> DARK_OAK_CHAIR = registerBlock("dark_oak_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> MANGROVE_CHAIR = registerBlock("mangrove_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> CHERRY_CHAIR = registerBlock("cherry_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> BAMBOO_CHAIR = registerBlock("bamboo_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> CRIMSON_CHAIR = registerBlock("crimson_chair", () -> new ChairBlock(Properties.WOOD));
    public static final Supplier<Block> WARPED_CHAIR = registerBlock("warped_chair", () -> new ChairBlock(Properties.WOOD));

    public static final Supplier<Block> OAK_TABLE = registerBlock("oak_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> SPRUCE_TABLE = registerBlock("spruce_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> BIRCH_TABLE = registerBlock("birch_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> JUNGLE_TABLE = registerBlock("jungle_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> ACACIA_TABLE = registerBlock("acacia_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> DARK_OAK_TABLE = registerBlock("dark_oak_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> MANGROVE_TABLE = registerBlock("mangrove_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> CHERRY_TABLE = registerBlock("cherry_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> BAMBOO_TABLE = registerBlock("bamboo_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> CRIMSON_TABLE = registerBlock("crimson_table", () -> new TableBlock(Properties.WOOD));
    public static final Supplier<Block> WARPED_TABLE = registerBlock("warped_table", () -> new TableBlock(Properties.WOOD));

    public static final Supplier<Block> OAK_CABINET = registerBlock("oak_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> SPRUCE_CABINET = registerBlock("spruce_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> BIRCH_CABINET = registerBlock("birch_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> JUNGLE_CABINET = registerBlock("jungle_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> ACACIA_CABINET = registerBlock("acacia_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> DARK_OAK_CABINET = registerBlock("dark_oak_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> MANGROVE_CABINET = registerBlock("mangrove_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> CHERRY_CABINET = registerBlock("cherry_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> BAMBOO_CABINET = registerBlock("bamboo_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> CRIMSON_CABINET = registerBlock("crimson_cabinet", () -> new CabinetBlock(Properties.WOOD));
    public static final Supplier<Block> WARPED_CABINET = registerBlock("warped_cabinet", () -> new CabinetBlock(Properties.WOOD));

    public static final Supplier<Block> OAK_BAR_COUNTER = registerBlock("oak_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> SPRUCE_BAR_COUNTER = registerBlock("spruce_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> BIRCH_BAR_COUNTER = registerBlock("birch_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> JUNGLE_BAR_COUNTER = registerBlock("jungle_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> ACACIA_BAR_COUNTER = registerBlock("acacia_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> DARK_OAK_BAR_COUNTER = registerBlock("dark_oak_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> MANGROVE_BAR_COUNTER = registerBlock("mangrove_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> CHERRY_BAR_COUNTER = registerBlock("cherry_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> BAMBOO_BAR_COUNTER = registerBlock("bamboo_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> CRIMSON_BAR_COUNTER = registerBlock("crimson_bar_counter", () -> new BarCounterBlock(Properties.WOOD));
    public static final Supplier<Block> WARPED_BAR_COUNTER = registerBlock("warped_bar_counter", () -> new BarCounterBlock(Properties.WOOD));

    public static final Supplier<Block> OAK_STOOL = registerBlock("oak_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> SPRUCE_STOOL = registerBlock("spruce_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> BIRCH_STOOL = registerBlock("birch_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> JUNGLE_STOOL = registerBlock("jungle_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> ACACIA_STOOL = registerBlock("acacia_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> DARK_OAK_STOOL = registerBlock("dark_oak_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> MANGROVE_STOOL = registerBlock("mangrove_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> CHERRY_STOOL = registerBlock("cherry_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> BAMBOO_STOOL = registerBlock("bamboo_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> CRIMSON_STOOL = registerBlock("crimson_stool", () -> new StoolBlock(Properties.WOOD));
    public static final Supplier<Block> WARPED_STOOL = registerBlock("warped_stool", () -> new StoolBlock(Properties.WOOD));

    public static final Supplier<Block> OAK_KITCHEN_CABINET = registerBlock("oak_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> SPRUCE_KITCHEN_CABINET = registerBlock("spruce_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> BIRCH_KITCHEN_CABINET = registerBlock("birch_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> JUNGLE_KITCHEN_CABINET = registerBlock("jungle_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> ACACIA_KITCHEN_CABINET = registerBlock("acacia_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> DARK_OAK_KITCHEN_CABINET = registerBlock("dark_oak_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> MANGROVE_KITCHEN_CABINET = registerBlock("mangrove_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> CHERRY_KITCHEN_CABINET = registerBlock("cherry_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> BAMBOO_KITCHEN_CABINET = registerBlock("bamboo_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> CRIMSON_KITCHEN_CABINET = registerBlock("crimson_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));
    public static final Supplier<Block> WARPED_KITCHEN_CABINET = registerBlock("warped_kitchen_cabinet", () -> new KitchenCabinetBlock(Properties.WOOD));

    public static final Supplier<Block> OAK_KITCHEN_DRAWER = registerBlock("oak_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> SPRUCE_KITCHEN_DRAWER = registerBlock("spruce_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> BIRCH_KITCHEN_DRAWER = registerBlock("birch_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> JUNGLE_KITCHEN_DRAWER = registerBlock("jungle_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> ACACIA_KITCHEN_DRAWER = registerBlock("acacia_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> DARK_OAK_KITCHEN_DRAWER = registerBlock("dark_oak_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> MANGROVE_KITCHEN_DRAWER = registerBlock("mangrove_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> CHERRY_KITCHEN_DRAWER = registerBlock("cherry_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> BAMBOO_KITCHEN_DRAWER = registerBlock("bamboo_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> CRIMSON_KITCHEN_DRAWER = registerBlock("crimson_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));
    public static final Supplier<Block> WARPED_KITCHEN_DRAWER = registerBlock("warped_kitchen_drawer", () -> new KitchenDrawerBlock(Properties.WOOD));

    public static final Supplier<Block> OAK_KITCHEN_CABINETRY = registerBlock("oak_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> SPRUCE_KITCHEN_CABINETRY = registerBlock("spruce_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> BIRCH_KITCHEN_CABINETRY = registerBlock("birch_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> JUNGLE_KITCHEN_CABINETRY = registerBlock("jungle_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> ACACIA_KITCHEN_CABINETRY = registerBlock("acacia_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> DARK_OAK_KITCHEN_CABINETRY = registerBlock("dark_oak_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> MANGROVE_KITCHEN_CABINETRY = registerBlock("mangrove_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> CHERRY_KITCHEN_CABINETRY = registerBlock("cherry_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> BAMBOO_KITCHEN_CABINETRY = registerBlock("bamboo_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> CRIMSON_KITCHEN_CABINETRY = registerBlock("crimson_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));
    public static final Supplier<Block> WARPED_KITCHEN_CABINETRY = registerBlock("warped_kitchen_cabinetry", () -> new KitchenCabinetryBlock(Properties.WOOD));

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        Supplier<T> supplier = FRegistry.registerBlock(name, block);
        FRegistry.registerItem(name, () -> new BlockItem(supplier.get(), new Item.Properties()), "tab");
        return supplier;
    }

    public static <T extends Block> Supplier<T> registerBlockHidden(String name, Supplier<T> block) {
        Supplier<T> supplier = FRegistry.registerBlock(name, block);
        FRegistry.registerItem(name, () -> new BlockItem(supplier.get(), new Item.Properties()), null);
        return supplier;
    }

    public static <T extends Block> Supplier<T> registerBlockOnly(String name, Supplier<T> block) {
        return FRegistry.registerBlock(name, block);
    }

    public static void register() {}
}
