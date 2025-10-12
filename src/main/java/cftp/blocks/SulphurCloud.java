package cftp.blocks;

import cftp.CFTP;
import cftp.utility.SulphurLogic;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SulphurCloud extends Block {

    public SulphurCloud(Settings settings) {
        super(settings);
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public void onDestroyedByExplosion(ServerWorld world, BlockPos pos, Explosion explosion) {
        super.onDestroyedByExplosion(world, pos, explosion);

        if (world instanceof ServerWorld serverWorld) {
            SulphurLogic.primeSulphur(serverWorld, pos);
        }
    }

    //@Override
    //protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    //
    //    Item item = stack.getItem();
    //
    //    if (!stack.isOf(Items.FLINT_AND_STEEL) && !stack.isOf(Items.FIRE_CHARGE)) {
    //        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    //    }
    //
    //    world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
    //
    //    if (world instanceof ServerWorld serverWorld) {
    //        SulphurLogic.primeSulphur(serverWorld, pos);
    //    }
    //
    //    if (stack.isOf(Items.FLINT_AND_STEEL)) {
    //        stack.damage(1, player, LivingEntity.getSlotForHand(hand));
    //    } else {
    //        stack.decrementUnlessCreative(1, player);
    //    }
    //
    //    player.incrementStat(Stats.USED.getOrCreateStat(item));
    //
    //    return ActionResult.SUCCESS;
    //}

    // NOPE
    //@Override
    //public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    //    if (world instanceof ServerWorld serverWorld && !player.isCreative()) {
    //        SulphurLogic.primeSulphur(serverWorld, pos);
    //    }
    //    return super.onBreak(world, pos, state, player);
    //}

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        //CFTP.LOGGER.info("replaced");

        if (world instanceof ServerWorld serverWorld ) {
            SulphurLogic.primeSulphur(serverWorld, pos);
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    //
    // This makes it not interactable ! (so onUseWithItem is useless)
    //

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

}
