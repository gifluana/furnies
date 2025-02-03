package com.lunazstudios.furnies.block.properties;

import net.minecraft.util.StringRepresentable;

public enum KitchenCabinetType implements StringRepresentable {
    SINGLE("single"),
    INNER_LEFT("inner_left"),
    INNER_RIGHT("inner_right"),
    OUTER_LEFT("outer_left"),
    OUTER_RIGHT("outer_right");

    private final String name;

    private KitchenCabinetType(String type) {
        this.name = type;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}