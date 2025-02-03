package com.lunazstudios.furnies.registry;

import com.lunazstudios.furnies.item.HammerItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FItems {
    public static final Supplier<Item> FURNIES_HAMMER = FRegistry.registerItem("furnies_hammer", () -> new HammerItem(new Item.Properties().durability(900)), "tab");

    public static void init() {}
}
