package com.lunazstudios.furnies.block;

import com.lunazstudios.furnies.block.entity.FurniCrafterBlockEntity;
import com.lunazstudios.furnies.util.block.ShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FurniCrafterBlock extends BaseEntityBlock {
    public static final MapCodec<FurniCrafterBlock> CODEC = simpleCodec(FurniCrafterBlock::new);
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 0, 0, 3, 11, 3),
            Block.box(13, 0, 0, 16, 11, 3),
            Block.box(13, 0, 13, 16, 11, 16),
            Block.box(0, 0, 13, 3, 11, 16),
            Block.box(0, 11, 0, 16, 16, 16));

    public FurniCrafterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(blockPos);
            if(entity instanceof FurniCrafterBlockEntity furniCrafterBE) {
                player.openMenu(new SimpleMenuProvider(furniCrafterBE, Component.literal("FurniCrafter")));
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FurniCrafterBlockEntity(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) { // Ensure block is actually removed and not just updated
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof FurniCrafterBlockEntity furniCrafterBE) {
                SimpleContainer container = furniCrafterBE.getOutputContainer();

                for (int i = 0; i < container.getContainerSize(); i++) {
                    ItemStack stack = container.getItem(i);
                    if (!stack.isEmpty()) {
                        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                    }
                }

                level.removeBlockEntity(pos);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

}
