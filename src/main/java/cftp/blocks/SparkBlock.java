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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
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

    public static void createIgniteParticles(World world, BlockPos pos) {
        world.addParticle(
                ParticleTypes.FLAME,
                pos.getX() + 0.00 + 0.11, pos.getY() + 0.80, pos.getZ() + 0.00 + 0.05,
                0.01, 0.10, 0.03
        );

        world.addParticle(
                ParticleTypes.FLAME,
                pos.getX() + 1.00 - 0.08, pos.getY() + 0.83, pos.getZ() + 0.00 + 0.19,
                -0.06, 0.09, 0.02
        );

        world.addParticle(
                ParticleTypes.FLAME,
                pos.getX() + 0.00 + 0.23, pos.getY() + 0.79, pos.getZ() + 1.00 - 0.10,
                0.04, 0.11, -0.01
        );

        world.addParticle(
                ParticleTypes.FLAME,
                pos.getX() + 1.00 - 0.31, pos.getY() + 0.75, pos.getZ() + 1.00 - 0.12,
                -0.01, 0.10, -0.02
        );
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

            if (stack.getItem() == Items.FLINT_AND_STEEL) {
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.setBlockState(pos, CFTPBlocks.FIERY_BLOCK.getDefaultState());

                    stack.damage(1, player, LivingEntity.getSlotForHand(hand));
                    player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

                    serverWorld.playSound(
                            null, pos.getX(), pos.getY() + 0.5f, pos.getZ(),
                            SoundEvents.ITEM_FLINTANDSTEEL_USE,
                            SoundCategory.BLOCKS
                    );
                } else {
                    createIgniteParticles(world, pos);
                }
                return ActionResult.SUCCESS;
            }


        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    //onDestroyedByFire

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
