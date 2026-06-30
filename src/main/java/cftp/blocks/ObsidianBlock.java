package cftp.blocks;

import cftp.registries.CFTPItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ObsidianBlock extends Block {

    public ObsidianBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if ((world instanceof ServerWorld serverWorld)) {
            if (stack.getItem() == CFTPItems.ELEMENTAL_POWDER) {
                serverWorld.setBlockState(pos, Blocks.CRYING_OBSIDIAN.getDefaultState(), Block.NOTIFY_ALL);
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

}
