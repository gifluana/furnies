package com.lunazstudios.furnies.block;

import com.lunazstudios.furnies.registry.FSoundEvents;
import com.lunazstudios.furnies.util.block.ShapeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

/**
 * Original Author: StarfishStudios
 * Project: Another Furniture
 */
public class StoveBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    protected static final VoxelShape SHAPE_NORTH = Shapes.or(
            Block.box(0, 0, 2, 16, 16, 16),
            Block.box(0, 1, 0, 16, 16, 2),
            Block.box(4, 11, -1, 12, 12, 0),
            Block.box(12, 13, -1, 14, 15, 0),
            Block.box(7, 13, -1, 9, 15, 0),
            Block.box(2, 13, -1, 4, 15, 0));
    protected static final VoxelShape SHAPE_EAST = ShapeUtil.rotateShape(SHAPE_NORTH, Direction.EAST);
    protected static final VoxelShape SHAPE_SOUTH = ShapeUtil.rotateShape(SHAPE_NORTH, Direction.SOUTH);
    protected static final VoxelShape SHAPE_WEST = ShapeUtil.rotateShape(SHAPE_NORTH, Direction.WEST);

    protected static final VoxelShape SHAPE_OPEN_NORTH = Shapes.or(
            Block.box(0, 0, 2, 16, 16, 16),
            Block.box(0, 13, 0, 16, 16, 2),
            Block.box(12, 13, -1, 14, 15, 0),
            Block.box(7, 13, -1, 9, 15, 0),
            Block.box(2, 13, -1, 4, 15, 0),
            Block.box(4, 0, -9, 12, 1, -8),
            Block.box(0, 1, -10, 16, 3, 2));
    protected static final VoxelShape SHAPE_OPEN_EAST = ShapeUtil.rotateShape(SHAPE_OPEN_NORTH, Direction.EAST);
    protected static final VoxelShape SHAPE_OPEN_SOUTH = ShapeUtil.rotateShape(SHAPE_OPEN_NORTH, Direction.SOUTH);
    protected static final VoxelShape SHAPE_OPEN_WEST = ShapeUtil.rotateShape(SHAPE_OPEN_NORTH, Direction.WEST);

    protected static final VoxelShape[] SHAPES_CLOSED = new VoxelShape[] {
            SHAPE_SOUTH, SHAPE_WEST, SHAPE_NORTH, SHAPE_EAST
    };
    protected static final VoxelShape[] SHAPES_OPEN = new VoxelShape[] {
            SHAPE_OPEN_SOUTH, SHAPE_OPEN_WEST, SHAPE_OPEN_NORTH, SHAPE_OPEN_EAST
    };

    public StoveBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(OPEN, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int index = state.getValue(FACING).get2DDataValue();
        return state.getValue(OPEN) ? SHAPES_OPEN[index] : SHAPES_CLOSED[index];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, OPEN);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, waterlogged)
                .setValue(OPEN, false);
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

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide) {
            boolean isOpen = blockState.getValue(OPEN);
            SoundEvent soundToPlay = isOpen ? FSoundEvents.STOVE_CLOSE.get() : FSoundEvents.STOVE_OPEN.get();
            // Passing null as the first parameter broadcasts the sound to all players around.
            level.playSound(null, blockPos, soundToPlay, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(blockPos, blockState.cycle(OPEN), 3);
        }
        return InteractionResult.SUCCESS;
    }

}