package cftp.blocks;

import cftp.registries.CFTPBlocks;
import cftp.utility.SulphurLogic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public void onDestroyedByExplosion(ServerWorld world, BlockPos pos, Explosion explosion) {
        super.onDestroyedByExplosion(world, pos, explosion);

        if (world instanceof ServerWorld serverWorld) {
            SulphurLogic.primeSulphur(serverWorld, pos);
        }
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (world instanceof ServerWorld serverWorld ) {
            if (sourceBlock.getDefaultState() == CFTPBlocks.SULPHUR_CLOUD.getDefaultState()) {
                serverWorld.setBlockState(pos, Blocks.AIR.getDefaultState());
                SulphurLogic.primeSulphur(serverWorld, pos);
            }
        }
    }

}
