package com.lunazstudios.furnies.block;

import com.lunazstudios.furnies.block.entity.KitchenCabinetBlockEntity;
import com.lunazstudios.furnies.block.properties.BarCounterType;
import com.lunazstudios.furnies.block.properties.FBlockStateProperties;
import com.lunazstudios.furnies.block.properties.KitchenCabinetType;
import com.lunazstudios.furnies.util.block.ShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


public class KitchenCabinetBlock extends BaseEntityBlock {
    public static final MapCodec<KitchenCabinetBlock> CODEC = simpleCodec(KitchenCabinetBlock::new);
    public MapCodec<KitchenCabinetBlock> codec() {
        return CODEC;
    }
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<KitchenCabinetType> TYPE = FBlockStateProperties.KITCHEN_CABINET_TYPE;

    protected static final VoxelShape KITCHEN_CABINET_NORTH = Shapes.or(
            Block.box(0, 0, 7, 16, 16, 16),
            Block.box(9, 2, 6, 10, 6, 7),
            Block.box(6, 2, 6, 7, 6, 7)
    );
    protected static final VoxelShape KITCHEN_CABINET_EAST = ShapeUtil.rotateShape(KITCHEN_CABINET_NORTH, Direction.EAST);
    protected static final VoxelShape KITCHEN_CABINET_SOUTH = ShapeUtil.rotateShape(KITCHEN_CABINET_NORTH, Direction.SOUTH);
    protected static final VoxelShape KITCHEN_CABINET_WEST = ShapeUtil.rotateShape(KITCHEN_CABINET_NORTH, Direction.WEST);

    protected static final VoxelShape KITCHEN_CABINET_INNER_NORTH_LEFT = Shapes.or(
            Block.box(0, 0, 7, 16, 16, 16),
            Block.box(7, 0, 0, 16, 16, 7),
            Block.box(2, 2, 6, 3, 6, 7)
    );
    protected static final VoxelShape KITCHEN_CABINET_INNER_EAST_LEFT = ShapeUtil.rotateShape(KITCHEN_CABINET_INNER_NORTH_LEFT, Direction.EAST);
    protected static final VoxelShape KITCHEN_CABINET_INNER_SOUTH_LEFT = ShapeUtil.rotateShape(KITCHEN_CABINET_INNER_NORTH_LEFT, Direction.SOUTH);
    protected static final VoxelShape KITCHEN_CABINET_INNER_WEST_LEFT = ShapeUtil.rotateShape(KITCHEN_CABINET_INNER_NORTH_LEFT, Direction.WEST);

    protected static final VoxelShape KITCHEN_CABINET_OUTER_NORTH_LEFT = Shapes.or(
            Block.box(7, 0, 7, 16, 16, 16)
    );
    protected static final VoxelShape KITCHEN_CABINET_OUTER_EAST_LEFT = ShapeUtil.rotateShape(KITCHEN_CABINET_OUTER_NORTH_LEFT, Direction.EAST);
    protected static final VoxelShape KITCHEN_CABINET_OUTER_SOUTH_LEFT = ShapeUtil.rotateShape(KITCHEN_CABINET_OUTER_NORTH_LEFT, Direction.SOUTH);
    protected static final VoxelShape KITCHEN_CABINET_OUTER_WEST_LEFT = ShapeUtil.rotateShape(KITCHEN_CABINET_OUTER_NORTH_LEFT, Direction.WEST);

    protected static final VoxelShape KITCHEN_CABINET_INNER_NORTH_RIGHT = Shapes.or(
            Block.box(0, 0, 7, 16, 16, 16),
            Block.box(0, 0, 0, 9, 16, 7),
            Block.box(9, 2, 2, 10, 6, 3)
    );
    protected static final VoxelShape KITCHEN_CABINET_INNER_EAST_RIGHT = ShapeUtil.rotateShape(KITCHEN_CABINET_INNER_NORTH_RIGHT, Direction.EAST);
    protected static final VoxelShape KITCHEN_CABINET_INNER_SOUTH_RIGHT = ShapeUtil.rotateShape(KITCHEN_CABINET_INNER_NORTH_RIGHT, Direction.SOUTH);
    protected static final VoxelShape KITCHEN_CABINET_INNER_WEST_RIGHT = ShapeUtil.rotateShape(KITCHEN_CABINET_INNER_NORTH_RIGHT, Direction.WEST);

    protected static final VoxelShape KITCHEN_CABINET_OUTER_NORTH_RIGHT = Shapes.or(
            Block.box(0, 0, 7, 9, 16, 16)
    );
    protected static final VoxelShape KITCHEN_CABINET_OUTER_EAST_RIGHT = ShapeUtil.rotateShape(KITCHEN_CABINET_OUTER_NORTH_RIGHT, Direction.EAST);
    protected static final VoxelShape KITCHEN_CABINET_OUTER_SOUTH_RIGHT = ShapeUtil.rotateShape(KITCHEN_CABINET_OUTER_NORTH_RIGHT, Direction.SOUTH);
    protected static final VoxelShape KITCHEN_CABINET_OUTER_WEST_RIGHT = ShapeUtil.rotateShape(KITCHEN_CABINET_OUTER_NORTH_RIGHT, Direction.WEST);

    protected static final VoxelShape KITCHEN_CABINET_OPEN_NORTH = Shapes.or(
            Block.box(0, 0, 7, 16, 16, 16),
            Block.box(1, 1, 1, 2, 15, 7),
            Block.box(14, 1, 1, 15, 15, 7),
            Block.box(15, 2, 2, 16, 6, 3),
            Block.box(0, 2, 2, 1, 6, 3)
    );
    protected static final VoxelShape KITCHEN_CABINET_OPEN_EAST = ShapeUtil.rotateShape(KITCHEN_CABINET_OPEN_NORTH, Direction.EAST);
    protected static final VoxelShape KITCHEN_CABINET_OPEN_SOUTH = ShapeUtil.rotateShape(KITCHEN_CABINET_OPEN_NORTH, Direction.SOUTH);
    protected static final VoxelShape KITCHEN_CABINET_OPEN_WEST = ShapeUtil.rotateShape(KITCHEN_CABINET_OPEN_NORTH, Direction.WEST);


    public KitchenCabinetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(OPEN, false)
                .setValue(TYPE, KitchenCabinetType.SINGLE));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof KitchenCabinetBlockEntity cabinetBE) {
            player.openMenu(cabinetBE);
            PiglinAi.angerNearbyPiglins((ServerLevel) level, player, true);
        }
        return InteractionResult.CONSUME;
    }

    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.is(newState.getBlock())) return;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof Container) {
            Containers.dropContents(level, pos, (Container)blockEntity);
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof KitchenCabinetBlockEntity cabinetBE) cabinetBE.recheckOpen();
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new KitchenCabinetBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        KitchenCabinetType type = state.getValue(TYPE);
        boolean open = state.getValue(OPEN);

        return switch (type) {
            case SINGLE -> switch (facing) {
                case NORTH -> open ? KITCHEN_CABINET_OPEN_NORTH : KITCHEN_CABINET_NORTH;
                case EAST -> open ? KITCHEN_CABINET_OPEN_EAST : KITCHEN_CABINET_EAST;
                case SOUTH -> open ? KITCHEN_CABINET_OPEN_SOUTH : KITCHEN_CABINET_SOUTH;
                case WEST -> open ? KITCHEN_CABINET_OPEN_WEST : KITCHEN_CABINET_WEST;
                default -> KITCHEN_CABINET_NORTH;
            };
            case INNER_LEFT -> switch (facing) {
                case EAST -> KITCHEN_CABINET_INNER_EAST_LEFT;
                case SOUTH -> KITCHEN_CABINET_INNER_SOUTH_LEFT;
                case WEST -> KITCHEN_CABINET_INNER_WEST_LEFT;
                default -> KITCHEN_CABINET_INNER_NORTH_LEFT;
            };
            case INNER_RIGHT -> switch (facing) {
                case EAST -> KITCHEN_CABINET_INNER_EAST_RIGHT;
                case SOUTH -> KITCHEN_CABINET_INNER_SOUTH_RIGHT;
                case WEST -> KITCHEN_CABINET_INNER_WEST_RIGHT;
                default -> KITCHEN_CABINET_INNER_NORTH_RIGHT;
            };
            case OUTER_LEFT -> switch (facing) {
                case EAST -> KITCHEN_CABINET_OUTER_EAST_LEFT;
                case SOUTH -> KITCHEN_CABINET_OUTER_SOUTH_LEFT;
                case WEST -> KITCHEN_CABINET_OUTER_WEST_LEFT;
                default -> KITCHEN_CABINET_OUTER_NORTH_LEFT;
            };
            case OUTER_RIGHT -> switch (facing) {
                case EAST -> KITCHEN_CABINET_OUTER_EAST_RIGHT;
                case SOUTH -> KITCHEN_CABINET_OUTER_SOUTH_RIGHT;
                case WEST -> KITCHEN_CABINET_OUTER_WEST_RIGHT;
                default -> KITCHEN_CABINET_OUTER_NORTH_RIGHT;
            };
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(blockPos);
        Direction facing = context.getHorizontalDirection().getOpposite();

        BlockState blockState = this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(OPEN, false);

        return blockState.setValue(TYPE, getConnection(blockState, context.getLevel(), blockPos));
    }

    public static KitchenCabinetType getConnection(BlockState state, Level level, BlockPos pos) {
        Direction facing = state.getValue(FACING);

        Direction dir1;
        BlockState state1 = level.getBlockState(pos.relative(facing));
        if (state1.getBlock() instanceof KitchenCabinetBlock &&
                (dir1 = state1.getValue(FACING)).getAxis() != state.getValue(FACING).getAxis() &&
                isDifferentOrientation(state, level, pos, dir1.getOpposite())) {
            return dir1 == facing.getCounterClockWise() ? KitchenCabinetType.INNER_LEFT : KitchenCabinetType.INNER_RIGHT;
        }

        Direction dir2;
        BlockState state2 = level.getBlockState(pos.relative(facing.getOpposite()));
        if (state2.getBlock() instanceof KitchenCabinetBlock &&
                (dir2 = state2.getValue(FACING)).getAxis() != state.getValue(FACING).getAxis() &&
                isDifferentOrientation(state, level, pos, dir2)) {
            return dir2 == facing.getCounterClockWise() ? KitchenCabinetType.OUTER_LEFT : KitchenCabinetType.OUTER_RIGHT;
        }

        return KitchenCabinetType.SINGLE;
    }

    public static boolean isDifferentOrientation(BlockState state, Level level, BlockPos pos, Direction dir) {
        BlockState otherState = level.getBlockState(pos.relative(dir));
        return !(otherState.getBlock() instanceof KitchenCabinetBlock)
                || otherState.getValue(FACING) != state.getValue(FACING);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, OPEN, TYPE);
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
        Direction direction = state.getValue(FACING);
        KitchenCabinetType type = state.getValue(TYPE);
        switch (mirror) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    return switch (type) {
                        case INNER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, KitchenCabinetType.INNER_RIGHT);
                        case INNER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, KitchenCabinetType.INNER_LEFT);
                        case OUTER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, KitchenCabinetType.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, KitchenCabinetType.OUTER_LEFT);
                        default -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    return switch (type) {
                        case INNER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, KitchenCabinetType.INNER_LEFT);
                        case INNER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, KitchenCabinetType.INNER_RIGHT);
                        case OUTER_LEFT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, KitchenCabinetType.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(Rotation.CLOCKWISE_180).setValue(TYPE, KitchenCabinetType.OUTER_LEFT);
                        default -> state.rotate(Rotation.CLOCKWISE_180);
                    };
                }
        }
        return super.mirror(state, mirror);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}