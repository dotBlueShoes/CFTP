package cftp.blocks;

import cftp.CFTP;
import cftp.registries.CFTPBlocks;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ColoredFallingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class SparkBlock extends ColoredFallingBlock {

    public SparkBlock(ColorCode color, Settings settings) {
        super(color, settings);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world instanceof ServerWorld serverWorld) {
            if (stack.getItem() == Items.FLINT_AND_STEEL) {
                serverWorld.setBlockState(pos, CFTPBlocks.FIERY_BLOCK.getDefaultState());
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    //@Override
    //protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    //    CFTP.LOGGER.info("call1");
    //
    //    return super.onUse(state, world, pos, player, hit);
    //}

    // TEST
    //@Override
    //protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    //
    //
    //    if (!(world instanceof ServerWorld)) {
    //        // this is being called twice.
    //
    //        if (stack.getItem() == Items.AIR) {
    //            //CFTP.LOGGER.info("yes");
    //            world.addParticle(
    //                    ParticleTypes.HEART,
    //                    pos.getX() + 0.5f, pos.getY() + 1.0f, pos.getZ() + 0.5f,
    //                    0.0, 0.0, 0.0
    //            );
    //        }
    //    }
    //
    //    return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    //}

    // TEST
    //@Override
    //public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
    //    super.onPlaced(world, pos, state, placer, itemStack);
    //
    //    world.addParticle(
    //            ParticleTypes.HEART,
    //            pos.getX(), pos.getY(), pos.getZ(),
    //            0.0, 0.0, 0.0
    //    );
    //
    //}
}
