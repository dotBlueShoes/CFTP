package cftp.blocks;

import cftp.CFTP;
import cftp.registries.CFTPBlocks;
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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class SulphurCloud extends Block {

    //  ABOUT
    // This is a guard to ensure that any explosion does not ignite N sulphurs at once.
    //
    boolean isBeingDestroyedViaExplosion = false;

    //  ABOUT
    // This is to communicate between `neighborUpdate` and `getStateForNeighborUpdate` which then signals `onStateReplaced`.
    //
    boolean isNeighbourSulphurCloudReplaced = false;

    public SulphurCloud(Settings settings) {
        super(settings);
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    @Override
    protected void onExploded(BlockState state, ServerWorld world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
        isBeingDestroyedViaExplosion = true;

        super.onExploded(state, world, pos, explosion, stackMerger);
    }

    @Override
    public void onDestroyedByExplosion(ServerWorld world, BlockPos pos, Explosion explosion) {
        super.onDestroyedByExplosion(world, pos, explosion);

        if (world instanceof ServerWorld serverWorld) {
            SulphurLogic.primeSulphur(serverWorld, pos);
        }
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        // TODO
        //  Implement sulphur_dust_bucket logic. So that when a config exists this method might run
        //  and the sulphur_cloud will become a collectable.

        Item item = stack.getItem();

        if (!stack.isOf(Items.FLINT_AND_STEEL) && !stack.isOf(Items.FIRE_CHARGE)) {
            return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
        }

        world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);

        if (world instanceof ServerWorld serverWorld) {
            SulphurLogic.primeSulphur(serverWorld, pos);
        }

        if (stack.isOf(Items.FLINT_AND_STEEL)) {
            stack.damage(1, player, LivingEntity.getSlotForHand(hand));
        } else {
            stack.decrementUnlessCreative(1, player);
        }

        player.incrementStat(Stats.USED.getOrCreateStat(item));

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {

        //  ABOUT
        // Only prime it when either `fire` removes this block or places fire at this position.
        //  This also means that if the block is simply replaced with air it will explode to!
        //

        if (world instanceof ServerWorld serverWorld) {
            boolean wasReplacedWithAir = newState.isOf(Blocks.AIR) && !isBeingDestroyedViaExplosion;
            if (wasReplacedWithAir || newState.isOf(Blocks.FIRE) || newState.isOf(Blocks.SOUL_FIRE)) {
                SulphurLogic.primeSulphur(serverWorld, pos);
            }
        }

        //  ABOUT
        // For now this will be easier to control.
        //

        //if (world instanceof ServerWorld serverWorld ) {
        //    if (newState.isOf(Blocks.FIRE) || newState.isOf(Blocks.SOUL_FIRE)) {
        //        SulphurLogic.primeSulphur(serverWorld, pos);
        //    }
        //}

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView worldView, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (!worldView.isClient() && isNeighbourSulphurCloudReplaced) {

            boolean isNewStateNotTriggering =
                    neighborState == Blocks.AIR.getDefaultState();

            ServerWorld world = (ServerWorld)worldView;

            if (!isNewStateNotTriggering) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                //SulphurLogic.primeSulphur(world, pos);
            }

        }
        return super.getStateForNeighborUpdate(state, worldView, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (world instanceof ServerWorld) {
            isNeighbourSulphurCloudReplaced = sourceBlock.getDefaultState() == CFTPBlocks.SULPHUR_CLOUD.getDefaultState();
            //boolean isAirNow       = sourceBlock.
            //if (isSulphurCloud && !isAirNow) {
            //    //  ABOUT
            //    // This will trigger `onStateReplaced` which will create a primed Sulphur.
            //     //
            //    serverWorld.setBlockState(pos, Blocks.AIR.getDefaultState());
            //}
        }
    }

    //  ABOUT
    // This makes it not interactable ! (so onUseWithItem is useless)
    //

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

}
