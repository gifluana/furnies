package com.lunazstudios.furnies.util.block;

import com.lunazstudios.furnies.item.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public interface HammerableBlock {

    Property<Integer> getHammerableProperty();

    int getMaxVariant();

    default InteractionResult handleHammerInteraction(ItemStack itemStack, BlockState state, Level level, BlockPos pos,
                                                      Player player, InteractionHand hand, BlockHitResult hit) {
        if (itemStack.getItem() instanceof HammerItem) {
            int currentVariant = state.getValue(getHammerableProperty());
            int newVariant = (currentVariant % getMaxVariant()) + 1;

            level.setBlock(pos, state.setValue(getHammerableProperty(), newVariant), 3);

            EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            itemStack.hurtAndBreak(1, player, slot);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
