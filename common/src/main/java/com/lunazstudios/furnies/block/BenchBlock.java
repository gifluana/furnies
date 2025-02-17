package com.lunazstudios.furnies.block;

import com.lunazstudios.furnies.block.properties.FBlockStateProperties;
import com.lunazstudios.furnies.block.properties.HorizontalConnectionType;
import com.lunazstudios.furnies.util.block.ShapeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BenchBlock extends SeatBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<HorizontalConnectionType> CONNECTION_TYPE = FBlockStateProperties.HORIZONTAL_CONNECTION_TYPE;

    protected static final VoxelShape SHAPE_SINGLE_NORTH = Shapes.or(
            Block.box(14, 0, 12, 16, 4, 14),
            Block.box(0, 4, 2, 16, 7, 14),
            Block.box(0, 0, 12, 2, 4, 14),
            Block.box(0, 0, 2, 2, 4, 4),
            Block.box(14, 0, 2, 16, 4, 4),
            Block.box(13, 7, 11, 16, 16, 14),
            Block.box(0, 9, 12.5, 16, 16, 12.5),
            Block.box(0, 7, 11, 3, 16, 14));
    protected static final VoxelShape SHAPE_SINGLE_EAST = ShapeUtil.rotateShape(SHAPE_SINGLE_NORTH, Direction.EAST);
    protected static final VoxelShape SHAPE_SINGLE_SOUTH = ShapeUtil.rotateShape(SHAPE_SINGLE_NORTH, Direction.SOUTH);
    protected static final VoxelShape SHAPE_SINGLE_WEST = ShapeUtil.rotateShape(SHAPE_SINGLE_NORTH, Direction.WEST);

    protected static final VoxelShape SHAPE_MIDDLE_NORTH = Shapes.or(
            Block.box(0, 4, 2, 16, 7, 14),
            Block.box(0, 9, 12.5, 16, 16, 12.5));
    protected static final VoxelShape SHAPE_MIDDLE_EAST = ShapeUtil.rotateShape(SHAPE_MIDDLE_NORTH, Direction.EAST);
    protected static final VoxelShape SHAPE_MIDDLE_SOUTH = ShapeUtil.rotateShape(SHAPE_MIDDLE_NORTH, Direction.SOUTH);
    protected static final VoxelShape SHAPE_MIDDLE_WEST = ShapeUtil.rotateShape(SHAPE_MIDDLE_NORTH, Direction.WEST);

    protected static final VoxelShape SHAPE_LEFT_NORTH = Shapes.or(
            Block.box(0, 4, 2, 16, 7, 14),
            Block.box(0, 0, 12, 2, 4, 14),
            Block.box(0, 0, 2, 2, 4, 4),
            Block.box(0, 9, 12.5, 16, 16, 12.5),
            Block.box(0, 7, 11, 3, 16, 14));
    protected static final VoxelShape SHAPE_LEFT_EAST = ShapeUtil.rotateShape(SHAPE_LEFT_NORTH, Direction.EAST);
    protected static final VoxelShape SHAPE_LEFT_SOUTH = ShapeUtil.rotateShape(SHAPE_LEFT_NORTH, Direction.SOUTH);
    protected static final VoxelShape SHAPE_LEFT_WEST = ShapeUtil.rotateShape(SHAPE_LEFT_NORTH, Direction.WEST);

    protected static final VoxelShape SHAPE_RIGHT_NORTH = Shapes.or(
            Block.box(14, 0, 12, 16, 4, 14),
            Block.box(0, 4, 2, 16, 7, 14),
            Block.box(14, 0, 2, 16, 4, 4),
            Block.box(13, 7, 11, 16, 16, 14),
            Block.box(0, 9, 12.5, 16, 16, 12.5));
    protected static final VoxelShape SHAPE_RIGHT_EAST = ShapeUtil.rotateShape(SHAPE_RIGHT_NORTH, Direction.EAST);
    protected static final VoxelShape SHAPE_RIGHT_SOUTH = ShapeUtil.rotateShape(SHAPE_RIGHT_NORTH, Direction.SOUTH);
    protected static final VoxelShape SHAPE_RIGHT_WEST = ShapeUtil.rotateShape(SHAPE_RIGHT_NORTH, Direction.WEST);

    protected static final VoxelShape[] SINGLE_SHAPES = new VoxelShape[] {
            SHAPE_SINGLE_SOUTH, SHAPE_SINGLE_WEST, SHAPE_SINGLE_NORTH, SHAPE_SINGLE_EAST
    };

    protected static final VoxelShape[] MIDDLE_SHAPES = new VoxelShape[] {
            SHAPE_MIDDLE_SOUTH, SHAPE_MIDDLE_WEST, SHAPE_MIDDLE_NORTH, SHAPE_MIDDLE_EAST
    };

    protected static final VoxelShape[] LEFT_SHAPES = new VoxelShape[] {
            SHAPE_LEFT_SOUTH, SHAPE_LEFT_WEST, SHAPE_LEFT_NORTH, SHAPE_LEFT_EAST
    };

    protected static final VoxelShape[] RIGHT_SHAPES = new VoxelShape[] {
            SHAPE_RIGHT_SOUTH, SHAPE_RIGHT_WEST, SHAPE_RIGHT_NORTH, SHAPE_RIGHT_EAST
    };

    public BenchBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(CONNECTION_TYPE, HorizontalConnectionType.SINGLE));
    }

    @Override
    public float seatHeight(BlockState state) {
        return 0.35F;
    }

    @Override
    public BlockPos primaryDismountLocation(Level level, BlockState state, BlockPos pos) {
        return pos.relative(state.getValue(FACING));
    }

    @Override
    public float setRiderRotation(BlockState state, Entity entity) {
        return state.getValue(FACING).toYRot();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int index = state.getValue(FACING).get2DDataValue();
        HorizontalConnectionType connection = state.getValue(CONNECTION_TYPE);
        switch (connection) {
            case SINGLE:
                return SINGLE_SHAPES[index];
            case LEFT:
                return LEFT_SHAPES[index];
            case MIDDLE:
                return MIDDLE_SHAPES[index];
            case RIGHT:
                return RIGHT_SHAPES[index];
            default:
                return SINGLE_SHAPES[index];
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        boolean waterlogged = level.getFluidState(pos).getType() == Fluids.WATER;
        Direction facing = context.getHorizontalDirection().getOpposite();

        Direction leftDir = facing.getCounterClockWise();
        Direction rightDir = facing.getClockWise();

        boolean leftConnected = isConnected(level.getBlockState(pos.relative(leftDir)), facing);
        boolean rightConnected = isConnected(level.getBlockState(pos.relative(rightDir)), facing);

        HorizontalConnectionType connection;
        if (leftConnected && rightConnected) {
            connection = HorizontalConnectionType.MIDDLE;
        } else if (leftConnected) {
            connection = HorizontalConnectionType.RIGHT;
        } else if (rightConnected) {
            connection = HorizontalConnectionType.LEFT;
        } else {
            connection = HorizontalConnectionType.SINGLE;
        }

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, waterlogged)
                .setValue(CONNECTION_TYPE, connection);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            if (levelReader instanceof Level level) {
                level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
        }

        Direction facing = state.getValue(FACING);
        if (direction == facing.getClockWise() || direction == facing.getCounterClockWise()) {
            if (levelReader instanceof Level level) {
                return updateConnectionType(state, level, pos);
            }
            return state;
        }

        return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, randomSource);
    }

    private BlockState updateConnectionType(BlockState state, LevelAccessor level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        Direction leftDir = facing.getCounterClockWise();
        Direction rightDir = facing.getClockWise();
        boolean leftConnected = isConnected(level.getBlockState(pos.relative(leftDir)), facing);
        boolean rightConnected = isConnected(level.getBlockState(pos.relative(rightDir)), facing);

        HorizontalConnectionType connection;
        if (leftConnected && rightConnected) {
            connection = HorizontalConnectionType.MIDDLE;
        } else if (leftConnected) {
            connection = HorizontalConnectionType.RIGHT;
        } else if (rightConnected) {
            connection = HorizontalConnectionType.LEFT;
        } else {
            connection = HorizontalConnectionType.SINGLE;
        }
        return state.setValue(CONNECTION_TYPE, connection);
    }

    private boolean isConnected(BlockState state, Direction facing) {
        return state.getBlock() instanceof BenchBlock && state.getValue(FACING) == facing;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, CONNECTION_TYPE);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
