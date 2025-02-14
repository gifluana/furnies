package com.lunazstudios.furnies.block;

import com.lunazstudios.furnies.block.FridgeBlock;
import com.lunazstudios.furnies.block.entity.FreezerBlockEntity;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FreezerBlock extends BaseEntityBlock {
    public static final MapCodec<FreezerBlock> CODEC = simpleCodec(FreezerBlock::new);
    public MapCodec<FreezerBlock> codec() {
        return CODEC;
    }
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    protected static final VoxelShape SHAPE_NORTH = Shapes.or(
            Block.box(13, 2, -1, 14, 14, 0),
            Block.box(0, 0, 0, 16, 16, 2),
            Block.box(15, 0, 2, 16, 16, 16),
            Block.box(0, 0, 2, 1, 16, 16),
            Block.box(1, 0, 2, 15, 1, 16),
            Block.box(1, 8, 2, 15, 8, 16),
            Block.box(1, 1, 16, 15, 15, 16),
            Block.box(1, 15, 2, 15, 16, 16)
    );
    protected static final VoxelShape SHAPE_EAST = ShapeUtil.rotateShape(SHAPE_NORTH, Direction.EAST);
    protected static final VoxelShape SHAPE_SOUTH = ShapeUtil.rotateShape(SHAPE_NORTH, Direction.SOUTH);
    protected static final VoxelShape SHAPE_WEST = ShapeUtil.rotateShape(SHAPE_NORTH, Direction.WEST);

    protected static final VoxelShape SHAPE_OPEN_NORTH = Shapes.or(
            Block.box(15, 0, 2, 16, 16, 16),
            Block.box(1, 8, 2, 15, 8, 16),
            Block.box(0, 0, 2, 1, 16, 16),
            Block.box(1, 0, 2, 15, 1, 16),
            Block.box(1, 1, 16, 15, 15, 16),
            Block.box(1, 15, 2, 15, 16, 16)
    );
    protected static final VoxelShape SHAPE_OPEN_EAST = ShapeUtil.rotateShape(SHAPE_OPEN_NORTH, Direction.EAST);
    protected static final VoxelShape SHAPE_OPEN_SOUTH = ShapeUtil.rotateShape(SHAPE_OPEN_NORTH, Direction.SOUTH);
    protected static final VoxelShape SHAPE_OPEN_WEST = ShapeUtil.rotateShape(SHAPE_OPEN_NORTH, Direction.WEST);

    public FreezerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof FreezerBlockEntity BE) {
            player.openMenu(BE);
            PiglinAi.angerNearbyPiglins(player, true);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof Container container) {
                Containers.dropContents(level, pos, container);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            BlockPos fridgePos = pos.below();
            if (level.getBlockState(fridgePos).getBlock() instanceof FridgeBlock) {
                level.removeBlock(fridgePos, false);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof FreezerBlockEntity BE) {
            BE.recheckOpen();
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FreezerBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
        Direction facing = state.getValue(FACING);
        boolean open = state.getValue(OPEN);
        return open
                ? switch (facing) {
            case EAST -> SHAPE_OPEN_EAST;
            case SOUTH -> SHAPE_OPEN_SOUTH;
            case WEST -> SHAPE_OPEN_WEST;
            default -> SHAPE_OPEN_NORTH;
        }
                : switch (facing) {
            case EAST -> SHAPE_EAST;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(OPEN, false);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
