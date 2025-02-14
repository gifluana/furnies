package com.lunazstudios.furnies.registry;

import com.lunazstudios.furnies.Furnies;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * Original Author: StarfishStudios
 * Project: Another Furniture
 */
public class FBlockTags {
    public static final TagKey<Block> BENCHES = blockTag("benches");
    public static final TagKey<Block> CHAIRS = blockTag("chairs");
    public static final TagKey<Block> CURTAINS = blockTag("curtains");
    public static final TagKey<Block> DRAWERS = blockTag("drawers");
    public static final TagKey<Block> LAMPS = blockTag("lamps");
    public static final TagKey<Block> PLANTER_BOXES = blockTag("planter_boxes");
    public static final TagKey<Block> SHELVES = blockTag("shelves");
    public static final TagKey<Block> SHUTTERS = blockTag("shutters");
    public static final TagKey<Block> SOFAS = blockTag("sofas");
    public static final TagKey<Block> STOOLS = blockTag("stools");
    public static final TagKey<Block> TABLES = blockTag("tables");
    public static final TagKey<Block> TALL_STOOLS = blockTag("tall_stools");

    public static final TagKey<Block> CHAIRS_TUCKABLE_UNDER = blockTag("chairs_tuckable_under");
    public static final TagKey<Block> ABOVE_BYPASSES_SEAT_CHECK = blockTag("above_bypasses_seat_check");
    public static final TagKey<Block> TABLES_CONNECTABLE = blockTag("table_connectable");
    public static final TagKey<Block> DONT_CONNECT_TO_PANES = blockTag("dont_connect_to_panes");
    public static final TagKey<Block> CAN_USE_SHUTTERS_THROUGH = blockTag("can_use_shutters_through");
    public static final TagKey<Block> SINKS_CONNECTABLE = blockTag("sink_connectable");

    private static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, Furnies.id(name));
    }

    public static void init() {}
}
