package com.lunazstudios.furnies.block;

import com.lunazstudios.furnies.block.properties.BarCounterType;
import com.lunazstudios.furnies.block.properties.FBlockStateProperties;
import com.lunazstudios.furnies.util.block.ShapeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KitchenCabinetryBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<BarCounterType> TYPE = FBlockStateProperties.BAR_COUNTER_TYPE;

    protected static final VoxelShape CABINETRY_NORTH = Shapes.or(
            Block.box(0, 0, 2, 16, 13, 16),
            Block.box(0, 13, 1, 16, 16, 16)
    );
    protected static final VoxelShape CABINETRY_EAST = ShapeUtil.rotateShape(CABINETRY_NORTH, Direction.EAST);
    protected static final VoxelShape CABINETRY_SOUTH = ShapeUtil.rotateShape(CABINETRY_NORTH, Direction.SOUTH);
    protected static final VoxelShape CABINETRY_WEST = ShapeUtil.rotateShape(CABINETRY_NORTH, Direction.WEST);

    protected static final VoxelShape CABINETRY_INNER_NORTH_LEFT = Shapes.or(
            Block.box(0, 0, 2, 16, 13, 16),
            Block.box(2, 0, 0, 16, 13, 2),
            Block.box(0, 13, 1, 16, 16, 16),
            Block.box(1, 13, 0, 16, 16, 1)
    );
    protected static final VoxelShape CABINETRY_INNER_EAST_LEFT = ShapeUtil.rotateShape(CABINETRY_INNER_NORTH_LEFT, Direction.EAST);
    protected static final VoxelShape CABINETRY_INNER_SOUTH_LEFT = ShapeUtil.rotateShape(CABINETRY_INNER_NORTH_LEFT, Direction.SOUTH);
    protected static final VoxelShape CABINETRY_INNER_WEST_LEFT = ShapeUtil.rotateShape(CABINETRY_INNER_NORTH_LEFT, Direction.WEST);

    protected static final VoxelShape CABINETRY_OUTER_NORTH_LEFT = Shapes.or(
            Block.box(2, 0, 2, 16, 13, 16),
            Block.box(1, 13, 1, 16, 16, 16)
    );
    protected static final VoxelShape CABINETRY_OUTER_EAST_LEFT = ShapeUtil.rotateShape(CABINETRY_OUTER_NORTH_LEFT, Direction.EAST);
    protected static final VoxelShape CABINETRY_OUTER_SOUTH_LEFT = ShapeUtil.rotateShape(CABINETRY_OUTER_NORTH_LEFT, Direction.SOUTH);
    protected static final VoxelShape CABINETRY_OUTER_WEST_LEFT = ShapeUtil.rotateShape(CABINETRY_OUTER_NORTH_LEFT, Direction.WEST);

    protected static final VoxelShape CABINETRY_INNER_NORTH_RIGHT = Shapes.or(
            Block.box(0, 0, 2, 16, 13, 16),
            Block.box(0, 0, 0, 14, 13, 2),
            Block.box(0, 13, 1, 16, 16, 16),
            Block.box(0, 13, 0, 15, 16, 1)
    );
    protected static final VoxelShape CABINETRY_INNER_EAST_RIGHT = ShapeUtil.rotateShape(CABINETRY_INNER_NORTH_RIGHT, Direction.EAST);
    protected static final VoxelShape CABINETRY_INNER_SOUTH_RIGHT = ShapeUtil.rotateShape(CABINETRY_INNER_NORTH_RIGHT, Direction.SOUTH);
    protected static final VoxelShape CABINETRY_INNER_WEST_RIGHT = ShapeUtil.rotateShape(CABINETRY_INNER_NORTH_RIGHT, Direction.WEST);

    protected static final VoxelShape CABINETRY_OUTER_NORTH_RIGHT = Shapes.or(
            Block.box(0, 0, 2, 14, 13, 16),
            Block.box(0, 13, 1, 15, 16, 16)
    );
    protected static final VoxelShape CABINETRY_OUTER_EAST_RIGHT = ShapeUtil.rotateShape(CABINETRY_OUTER_NORTH_RIGHT, Direction.EAST);
    protected static final VoxelShape CABINETRY_OUTER_SOUTH_RIGHT = ShapeUtil.rotateShape(CABINETRY_OUTER_NORTH_RIGHT, Direction.SOUTH);
    protected static final VoxelShape CABINETRY_OUTER_WEST_RIGHT = ShapeUtil.rotateShape(CABINETRY_OUTER_NORTH_RIGHT, Direction.WEST);

    public KitchenCabinetryBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(TYPE, BarCounterType.SINGLE));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(blockPos);
        BlockState blockState = this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        return blockState.setValue(TYPE, getConnection(blockState, context.getLevel(), blockPos));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess,
                                     BlockPos pos, Direction direction, BlockPos neighborPos,
                                     BlockState neighborState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            if (levelReader instanceof Level level) {
                level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
        }

        if (direction.getAxis().isHorizontal()) {
            if (levelReader instanceof Level level) {
                return state.setValue(TYPE, getConnection(state, level, pos));
            }
            return state;
        }

        return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        BarCounterType type = state.getValue(TYPE);

        return switch (type) {
            case SINGLE -> switch (facing) {
                case EAST -> CABINETRY_EAST;
                case SOUTH -> CABINETRY_SOUTH;
                case WEST -> CABINETRY_WEST;
                default -> CABINETRY_NORTH;
            };
            case INNER_LEFT -> switch (facing) {
                case EAST -> CABINETRY_INNER_EAST_LEFT;
                case SOUTH -> CABINETRY_INNER_SOUTH_LEFT;
                case WEST -> CABINETRY_INNER_WEST_LEFT;
                default -> CABINETRY_INNER_NORTH_LEFT;
            };
            case INNER_RIGHT -> switch (facing) {
                case EAST -> CABINETRY_INNER_EAST_RIGHT;
                case SOUTH -> CABINETRY_INNER_SOUTH_RIGHT;
                case WEST -> CABINETRY_INNER_WEST_RIGHT;
                default -> CABINETRY_INNER_NORTH_RIGHT;
            };
            case OUTER_LEFT -> switch (facing) {
                case EAST -> CABINETRY_OUTER_EAST_LEFT;
                case SOUTH -> CABINETRY_OUTER_SOUTH_LEFT;
                case WEST -> CABINETRY_OUTER_WEST_LEFT;
                default -> CABINETRY_OUTER_NORTH_LEFT;
            };
            case OUTER_RIGHT -> switch (facing) {
                case EAST -> CABINETRY_OUTER_EAST_RIGHT;
                case SOUTH -> CABINETRY_OUTER_SOUTH_RIGHT;
                case WEST -> CABINETRY_OUTER_WEST_RIGHT;
                default -> CABINETRY_OUTER_NORTH_RIGHT;
            };
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
    }

    public static BarCounterType getConnection(BlockState state, Level level, BlockPos pos) {
        Direction facing = state.getValue(FACING);

        Direction dir1;
        BlockState state1 = level.getBlockState(pos.relative(facing));
        if (state1.getBlock() instanceof KitchenCabinetryBlock &&
                (dir1 = state1.getValue(FACING)).getAxis() != state.getValue(FACING).getAxis() &&
                isDifferentOrientation(state, level, pos, dir1.getOpposite())) {
            if (dir1 == facing.getCounterClockWise()) {
                return BarCounterType.INNER_LEFT;
            }
            return BarCounterType.INNER_RIGHT;
        }

        Direction dir2;
        BlockState state2 = level.getBlockState(pos.relative(facing.getOpposite()));
        if (state2.getBlock() instanceof KitchenCabinetryBlock &&
                (dir2 = state2.getValue(FACING)).getAxis() != state.getValue(FACING).getAxis() &&
                isDifferentOrientation(state, level, pos, dir2)) {
            if (dir2 == facing.getCounterClockWise()) {
                return BarCounterType.OUTER_LEFT;
            }
            return BarCounterType.OUTER_RIGHT;
        }
        return BarCounterType.SINGLE;
    }

    public static boolean isDifferentOrientation(BlockState state, Level level, BlockPos pos, Direction dir) {
        BlockState otherState = level.getBlockState(pos.relative(dir));
        return !(otherState.getBlock() instanceof KitchenCabinetryBlock)
                || otherState.getValue(FACING) != state.getValue(FACING);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED)
                ? Fluids.WATER.getSource(false)
                : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        Direction direction = state.getValue(FACING);
        BarCounterType type = state.getValue(TYPE);
        switch (mirror) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    return switch (type) {
                        case INNER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, BarCounterType.INNER_RIGHT);
                        case INNER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, BarCounterType.INNER_LEFT);
                        case OUTER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, BarCounterType.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, BarCounterType.OUTER_LEFT);
                        default -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    return switch (type) {
                        case INNER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, BarCounterType.INNER_LEFT);
                        case INNER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, BarCounterType.INNER_RIGHT);
                        case OUTER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, BarCounterType.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, BarCounterType.OUTER_LEFT);
                        default -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
        }
        return super.mirror(state, mirror);
    }
}
