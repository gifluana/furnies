package com.lunazstudios.furnies.block;

import com.lunazstudios.furnies.block.entity.KitchenDrawerBlockEntity;
import com.lunazstudios.furnies.block.properties.FBlockStateProperties;
import com.lunazstudios.furnies.util.block.HammerableBlock;
import com.lunazstudios.furnies.util.block.ShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
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

public class KitchenDrawerBlock extends BaseEntityBlock implements HammerableBlock {
    public static final MapCodec<KitchenDrawerBlock> CODEC = simpleCodec(KitchenDrawerBlock::new);
    public MapCodec<KitchenDrawerBlock> codec() {
        return CODEC;
    }
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final IntegerProperty STYLE = FBlockStateProperties.KITCHEN_DRAWER_STYLE;

    protected static final VoxelShape VARIANT_1_NORTH = Shapes.or(
            Block.box(0, 0, 2, 16, 13, 16),
            Block.box(0, 13, 1, 16, 16, 16),
            Block.box(6, 11, 1, 10, 12, 2),
            Block.box(3, 3, 1, 4, 7, 2)
    );
    protected static final VoxelShape VARIANT_1_EAST = ShapeUtil.rotateShape(VARIANT_1_NORTH, Direction.EAST);
    protected static final VoxelShape VARIANT_1_SOUTH = ShapeUtil.rotateShape(VARIANT_1_NORTH, Direction.SOUTH);
    protected static final VoxelShape VARIANT_1_WEST = ShapeUtil.rotateShape(VARIANT_1_NORTH, Direction.WEST);

    protected static final VoxelShape VARIANT_1_NORTH_OPEN = Shapes.or(
            Block.box(6, 11, -6, 10, 12, -5),
            Block.box(1, 11, 7, 15, 13, 8),
            Block.box(1, 10, -5, 15, 13, 2),
            Block.box(14, 1, -11, 15, 9, 3),
            Block.box(15, 3, -9, 16, 7, -8),
            Block.box(0, 13, 1, 16, 16, 16),
            Block.box(0, 0, 2, 16, 13, 16)
    );
    protected static final VoxelShape VARIANT_1_EAST_OPEN = ShapeUtil.rotateShape(VARIANT_1_NORTH_OPEN, Direction.EAST);
    protected static final VoxelShape VARIANT_1_SOUTH_OPEN = ShapeUtil.rotateShape(VARIANT_1_NORTH_OPEN, Direction.SOUTH);
    protected static final VoxelShape VARIANT_1_WEST_OPEN = ShapeUtil.rotateShape(VARIANT_1_NORTH_OPEN, Direction.WEST);

    protected static final VoxelShape VARIANT_2_NORTH = Shapes.or(
            Block.box(0, 0, 2, 16, 13, 16),
            Block.box(0, 13, 1, 16, 16, 16),
            Block.box(9, 6, 1, 10, 10, 2),
            Block.box(6, 6, 1, 7, 10, 2)
    );
    protected static final VoxelShape VARIANT_2_EAST = ShapeUtil.rotateShape(VARIANT_2_NORTH, Direction.EAST);
    protected static final VoxelShape VARIANT_2_SOUTH = ShapeUtil.rotateShape(VARIANT_2_NORTH, Direction.SOUTH);
    protected static final VoxelShape VARIANT_2_WEST = ShapeUtil.rotateShape(VARIANT_2_NORTH, Direction.WEST);

    protected static final VoxelShape VARIANT_2_NORTH_OPEN = Shapes.or(
            Block.box(0, 0, 2, 16, 13, 16),
            Block.box(0, 13, 1, 16, 16, 16),
            Block.box(9, 6, 1, 10, 10, 2),
            Block.box(6, 6, 1, 7, 10, 2)
    );
    protected static final VoxelShape VARIANT_2_EAST_OPEN = ShapeUtil.rotateShape(VARIANT_2_NORTH_OPEN, Direction.EAST);
    protected static final VoxelShape VARIANT_2_SOUTH_OPEN = ShapeUtil.rotateShape(VARIANT_2_NORTH_OPEN, Direction.SOUTH);
    protected static final VoxelShape VARIANT_2_WEST_OPEN = ShapeUtil.rotateShape(VARIANT_2_NORTH_OPEN, Direction.WEST);



    public KitchenDrawerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(OPEN, false)
                .setValue(STYLE, 1));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof KitchenDrawerBlockEntity drawerBE) {
            player.openMenu(drawerBE);
            PiglinAi.angerNearbyPiglins((ServerLevel) level, player, true);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        InteractionResult result = HammerableBlock.super.handleHammerInteraction(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
        if (result.consumesAction()) {
            return InteractionResult.PASS;
        }
        return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
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
        if (blockEntity instanceof KitchenDrawerBlockEntity drawerBE) drawerBE.recheckOpen();
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new KitchenDrawerBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        boolean open = state.getValue(OPEN);
        int style = state.getValue(STYLE);

        switch (style) {
            case 2:
                switch (facing) {
                    case EAST:
                        return open ? VARIANT_2_EAST_OPEN : VARIANT_2_EAST;
                    case SOUTH:
                        return open ? VARIANT_2_SOUTH_OPEN : VARIANT_2_SOUTH;
                    case WEST:
                        return open ? VARIANT_2_WEST_OPEN : VARIANT_2_WEST;
                    default:
                        return open ? VARIANT_2_NORTH_OPEN : VARIANT_2_NORTH;
                }
            case 1:
            default:
                switch (facing) {
                    case EAST:
                        return open ? VARIANT_1_EAST_OPEN : VARIANT_1_EAST;
                    case SOUTH:
                        return open ? VARIANT_1_SOUTH_OPEN : VARIANT_1_SOUTH;
                    case WEST:
                        return open ? VARIANT_1_WEST_OPEN : VARIANT_1_WEST;
                    default:
                        return open ? VARIANT_1_NORTH_OPEN : VARIANT_1_NORTH;
                }
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        Direction facing = context.getHorizontalDirection().getOpposite();

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, waterlogged)
                .setValue(OPEN, false)
                .setValue(STYLE, 1);
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
        return state;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, OPEN, STYLE);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public Property<Integer> getHammerableProperty() {
        return STYLE;
    }

    @Override
    public int getMaxVariant() {
        return 2;
    }
}
