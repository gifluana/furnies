package com.lunazstudios.furnies.block.properties;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FBlockStateProperties {
    public static final EnumProperty<HorizontalConnectionType> HORIZONTAL_CONNECTION_TYPE = EnumProperty.create("connection_type", HorizontalConnectionType.class);
    public static final IntegerProperty LEVEL_1_3 = IntegerProperty.create("level", 1, 3);
    public static final BooleanProperty BASE = BooleanProperty.create("base");
    public static final EnumProperty<Direction> FACING_EXCEPT_DOWN = EnumProperty.create("facing", Direction.class, (direction) -> direction != Direction.DOWN);
    public static final IntegerProperty STYLE = IntegerProperty.create("style", 1, 3);
    public static final IntegerProperty KITCHEN_DRAWER_STYLE = IntegerProperty.create("style", 1, 2);
    public static final IntegerProperty CHANNEL_1_3 = IntegerProperty.create("channel", 1, 3);
    public static final BooleanProperty CONNECTED_RIGHT = BooleanProperty.create("connected_right");
    public static final BooleanProperty CONNECTED_MIDDLE = BooleanProperty.create("connected_right");
    public static final BooleanProperty CONNECTED_LEFT = BooleanProperty.create("connected_left");
    public static final EnumProperty<SofaType> SOFA_TYPE = EnumProperty.create("type", SofaType.class);
    public static final EnumProperty<BarCounterType> BAR_COUNTER_TYPE = EnumProperty.create("type", BarCounterType.class);
    public static final EnumProperty<KitchenCabinetType> KITCHEN_CABINET_TYPE = EnumProperty.create("type", KitchenCabinetType.class);
}