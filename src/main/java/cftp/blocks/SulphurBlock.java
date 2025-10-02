package cftp.blocks;

import cftp.CFTP;
import cftp.entity.SulphurEntity;
import cftp.registries.CFTPBlocks;
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

    public SulphurBlock(IntProvider experienceDropped, Settings settings) {
        super(experienceDropped, settings);
    }

    // jak powina się zachować siarka kiedy jest niszczona przez nie poprawne narzędzie ?
    // jak powina się zachować siarka kiedy jest niszczona poprzez wybuch ?
    // see how to add sulphur to villager trade for gunpowder

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        CFTP.LOGGER.info("break");
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        CFTP.LOGGER.info("broken");
        super.onBroken(world, pos, state);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        CFTP.LOGGER.info("past broken");

        // Checks whether a tool is required
        boolean requiresTool = state.isToolRequired();

        // Checks if the given tool is suitable (correct pickaxe type & tier, etc.)
        boolean correctTool = tool.isSuitableFor(state);

        //if (requiresTool && correctTool) {}

        super.afterBreak(world, player, pos, state, blockEntity, tool);
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

    private void primeSulphur(ServerWorld world, BlockPos pos) {

        SulphurEntity sulphurEntity = new SulphurEntity(
                world,
                (double)pos.getX() + (double)0.5F,
                pos.getY(),
                (double)pos.getZ() + (double)0.5F,
                null,
                this.getDefaultState()
        );

        //tntEntity.surphurBlockState = this.getDefaultState();
        //sulphurEntity.surphurBlockState = CFTPBlocks.NETHER_SULPHUR_ORE.getDefaultState();

        world.spawnEntity(sulphurEntity);

        //world.playSound(null,
        //        tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(),
        //        SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS,
        //        1.0F, 1.0F
        //);

        //world.emitGameEvent(null, GameEvent.PRIME_FUSE, pos);
    }

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

            primeSulphur(serverWorld, pos);

            //serverWorld.createExplosion(
            //        null, pos.getX(), pos.getY(), pos.getZ(), 2.0f, World.ExplosionSourceType.BLOCK
            //);
        }
    }

}
