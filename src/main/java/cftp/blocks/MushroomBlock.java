package cftp.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.ColoredFallingBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.ColorCode;

public class MushroomBlock extends PlantBlock {

    protected MushroomBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return null;
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
