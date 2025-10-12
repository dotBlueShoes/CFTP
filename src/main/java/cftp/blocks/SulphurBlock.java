package cftp.blocks;

import cftp.CFTP;
import cftp.entity.SulphurEntity;
import cftp.registries.CFTPBlocks;
import cftp.utility.SulphurLogic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class SulphurBlock extends ExperienceDroppingBlock {

    boolean isCreateCloudOnBreak = false;

    public SulphurBlock(IntProvider experienceDropped, Settings settings) {
        super(experienceDropped, settings);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        ItemStack stack = player.getMainHandStack();
        isCreateCloudOnBreak = !stack.isSuitableFor(state) && !player.isCreative();

        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {

        if (world instanceof ServerWorld serverWorld && isCreateCloudOnBreak) {
            serverWorld.setBlockState(pos, CFTPBlocks.SULPHUR_CLOUD.getDefaultState());
        }

        super.onBroken(world, pos, state);
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    //@Override
    //protected void onExploded(BlockState state, ServerWorld world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
    //    CFTP.LOGGER.info("exploded");
    //    super.onExploded(state, world, pos, explosion, stackMerger);
    //}

    @Override
    public void onDestroyedByExplosion(ServerWorld world, BlockPos pos, Explosion explosion) {
        super.onDestroyedByExplosion(world, pos, explosion);

        //  TODO
        // This happens instant. Which is not ideal. Instead it should create an entity like tnt i think.
        //  or delay that explosion in some other way.
        //

        // primeTnt(world, pos, player);
        // world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);

        if (world instanceof ServerWorld serverWorld) {

            SulphurLogic.primeSulphur(serverWorld, pos);

            //serverWorld.createExplosion(
            //        null, pos.getX(), pos.getY(), pos.getZ(), 2.0f, World.ExplosionSourceType.BLOCK
            //);
        }
    }

}
