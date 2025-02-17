package com.lunazstudios.furnies.block;

import com.lunazstudios.furnies.block.properties.BarCounterType;
import com.lunazstudios.furnies.block.properties.FBlockStateProperties;
import com.lunazstudios.furnies.util.block.ShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BarCounterBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<BarCounterType> TYPE = FBlockStateProperties.BAR_COUNTER_TYPE;

    protected static final VoxelShape BARCOUNTER_NORTH = Shapes.or(
            Block.box(0, 0, 3, 1, 14, 16),
            Block.box(15, 0, 3, 16, 14, 16),
            Block.box(1, 0, 4, 15, 1, 16),
            Block.box(1, 0, 3, 15, 14, 4),
            Block.box(1, 7, 4, 15, 8, 16),
            Block.box(4, 2, 2, 5, 3, 3),
            Block.box(0, 1.5, 0, 16, 3.5, 2),
            Block.box(0, 14, 2, 16, 16, 16),
            Block.box(11, 2, 2, 12, 3, 3)
    );
    protected static final VoxelShape BARCOUNTER_EAST = ShapeUtil.rotateShape(BARCOUNTER_NORTH, Direction.EAST);
    protected static final VoxelShape BARCOUNTER_SOUTH = ShapeUtil.rotateShape(BARCOUNTER_NORTH, Direction.SOUTH);
    protected static final VoxelShape BARCOUNTER_WEST = ShapeUtil.rotateShape(BARCOUNTER_NORTH, Direction.WEST);

    protected static final VoxelShape BARCOUNTER_INNER_NORTH_LEFT = Shapes.or(
            Block.box(0, 0, 3, 4, 14, 4),
            Block.box(0, 0, 4, 1, 14, 16),
            Block.box(4, 0, 1, 16, 1, 16),
            Block.box(1, 0, 4, 4, 1, 16),
            Block.box(1, 7, 4, 4, 8, 16),
            Block.box(3, 0, 0, 4, 14, 3),
            Block.box(4, 0, 0, 16, 14, 1),
            Block.box(4, 7, 1, 16, 8, 16),
            Block.box(0, 1.5, 0, 2, 3.5, 2),
            Block.box(0, 14, 2, 16, 16, 16),
            Block.box(2, 14, 0, 16, 16, 2)
    );
    protected static final VoxelShape BARCOUNTER_INNER_EAST_LEFT = ShapeUtil.rotateShape(BARCOUNTER_INNER_NORTH_LEFT, Direction.EAST);
    protected static final VoxelShape BARCOUNTER_INNER_SOUTH_LEFT = ShapeUtil.rotateShape(BARCOUNTER_INNER_NORTH_LEFT, Direction.SOUTH);
    protected static final VoxelShape BARCOUNTER_INNER_WEST_LEFT = ShapeUtil.rotateShape(BARCOUNTER_INNER_NORTH_LEFT, Direction.WEST);

    protected static final VoxelShape BARCOUNTER_OUTER_NORTH_LEFT = Shapes.or(
            Block.box(3, 0, 3, 4, 14, 16),
            Block.box(4, 0, 4, 16, 1, 16),
            Block.box(4, 0, 3, 16, 14, 4),
            Block.box(4, 7, 4, 16, 8, 16),
            Block.box(5, 2, 2, 6, 3, 3),
            Block.box(0, 1.5, 2, 2, 3.5, 16),
            Block.box(0, 1.5, 0, 16, 3.5, 2),
            Block.box(2, 14, 2, 16, 16, 16),
            Block.box(13, 2, 2, 14, 3, 3),
            Block.box(2.25, 2, 13, 3.25, 3, 14),
            Block.box(2.25, 2, 5, 3.25, 3, 6)
    );
    protected static final VoxelShape BARCOUNTER_OUTER_EAST_LEFT = ShapeUtil.rotateShape(BARCOUNTER_OUTER_NORTH_LEFT, Direction.EAST);
    protected static final VoxelShape BARCOUNTER_OUTER_SOUTH_LEFT = ShapeUtil.rotateShape(BARCOUNTER_OUTER_NORTH_LEFT, Direction.SOUTH);
    protected static final VoxelShape BARCOUNTER_OUTER_WEST_LEFT = ShapeUtil.rotateShape(BARCOUNTER_OUTER_NORTH_LEFT, Direction.WEST);

    protected static final VoxelShape BARCOUNTER_INNER_NORTH_RIGHT = Shapes.or(
            Block.box(12, 0, 3, 16, 14, 4),
            Block.box(15, 0, 4, 16, 14, 16),
            Block.box(0, 0, 1, 12, 1, 16),
            Block.box(12, 0, 4, 15, 1, 16),
            Block.box(12, 7, 4, 15, 8, 16),
            Block.box(12, 0, 0, 13, 14, 3),
            Block.box(0, 0, 0, 12, 14, 1),
            Block.box(0, 7, 1, 12, 8, 16),
            Block.box(14, 1.5, 0, 16, 3.5, 2),
            Block.box(0, 14, 2, 16, 16, 16),
            Block.box(0, 14, 0, 14, 16, 2)
    );
    protected static final VoxelShape BARCOUNTER_INNER_EAST_RIGHT = ShapeUtil.rotateShape(BARCOUNTER_INNER_NORTH_RIGHT, Direction.EAST);
    protected static final VoxelShape BARCOUNTER_INNER_SOUTH_RIGHT = ShapeUtil.rotateShape(BARCOUNTER_INNER_NORTH_RIGHT, Direction.SOUTH);
    protected static final VoxelShape BARCOUNTER_INNER_WEST_RIGHT = ShapeUtil.rotateShape(BARCOUNTER_INNER_NORTH_RIGHT, Direction.WEST);

    protected static final VoxelShape BARCOUNTER_OUTER_NORTH_RIGHT = Shapes.or(
            Block.box(12, 0, 3, 13, 14, 16),
            Block.box(0, 0, 4, 12, 1, 16),
            Block.box(0, 0, 3, 12, 14, 4),
            Block.box(0, 7, 4, 12, 8, 16),
            Block.box(10, 2, 2, 11, 3, 3),
            Block.box(14, 1.5, 2, 16, 3.5, 16),
            Block.box(0, 1.5, 0, 16, 3.5, 2),
            Block.box(0, 14, 2, 14, 16, 16),
            Block.box(2, 2, 2, 3, 3, 3),
            Block.box(12.75, 2, 13, 13.75, 3, 14),
            Block.box(12.75, 2, 5, 13.75, 3, 6)
    );
    protected static final VoxelShape BARCOUNTER_OUTER_EAST_RIGHT = ShapeUtil.rotateShape(BARCOUNTER_OUTER_NORTH_RIGHT, Direction.EAST);
    protected static final VoxelShape BARCOUNTER_OUTER_SOUTH_RIGHT = ShapeUtil.rotateShape(BARCOUNTER_OUTER_NORTH_RIGHT, Direction.SOUTH);
    protected static final VoxelShape BARCOUNTER_OUTER_WEST_RIGHT = ShapeUtil.rotateShape(BARCOUNTER_OUTER_NORTH_RIGHT, Direction.WEST);

    public BarCounterBlock(Properties properties) {
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
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
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
        } else {
            return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, randomSource);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        BarCounterType type = state.getValue(TYPE);

        return switch (type) {
            case SINGLE -> switch (facing) {
                case NORTH -> BARCOUNTER_NORTH;
                case EAST -> BARCOUNTER_EAST;
                case SOUTH -> BARCOUNTER_SOUTH;
                case WEST -> BARCOUNTER_WEST;
                default -> BARCOUNTER_NORTH;
            };
            case INNER_LEFT -> switch (facing) {
                case NORTH -> BARCOUNTER_INNER_NORTH_LEFT;
                case EAST -> BARCOUNTER_INNER_EAST_LEFT;
                case SOUTH -> BARCOUNTER_INNER_SOUTH_LEFT;
                case WEST -> BARCOUNTER_INNER_WEST_LEFT;
                default -> BARCOUNTER_INNER_NORTH_LEFT;
            };
            case INNER_RIGHT -> switch (facing) {
                case NORTH -> BARCOUNTER_INNER_NORTH_RIGHT;
                case EAST -> BARCOUNTER_INNER_EAST_RIGHT;
                case SOUTH -> BARCOUNTER_INNER_SOUTH_RIGHT;
                case WEST -> BARCOUNTER_INNER_WEST_RIGHT;
                default -> BARCOUNTER_INNER_NORTH_RIGHT;
            };
            case OUTER_LEFT -> switch (facing) {
                case NORTH -> BARCOUNTER_OUTER_NORTH_LEFT;
                case EAST -> BARCOUNTER_OUTER_EAST_LEFT;
                case SOUTH -> BARCOUNTER_OUTER_SOUTH_LEFT;
                case WEST -> BARCOUNTER_OUTER_WEST_LEFT;
                default -> BARCOUNTER_OUTER_NORTH_LEFT;
            };
            case OUTER_RIGHT -> switch (facing) {
                case NORTH -> BARCOUNTER_OUTER_NORTH_RIGHT;
                case EAST -> BARCOUNTER_OUTER_EAST_RIGHT;
                case SOUTH -> BARCOUNTER_OUTER_SOUTH_RIGHT;
                case WEST -> BARCOUNTER_OUTER_WEST_RIGHT;
                default -> BARCOUNTER_OUTER_NORTH_RIGHT;
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
        if (state1.getBlock() instanceof BarCounterBlock &&
                (dir1 = state1.getValue(FACING)).getAxis() != state.getValue(FACING).getAxis() &&
                isDifferentOrientation(state, level, pos, dir1.getOpposite())) {
            if (dir1 == facing.getCounterClockWise()) {
                return BarCounterType.INNER_LEFT;
            }
            return BarCounterType.INNER_RIGHT;
        }

        Direction dir2;
        BlockState state2 = level.getBlockState(pos.relative(facing.getOpposite()));
        if (state2.getBlock() instanceof BarCounterBlock &&
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
        return !(otherState.getBlock() instanceof BarCounterBlock)
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
