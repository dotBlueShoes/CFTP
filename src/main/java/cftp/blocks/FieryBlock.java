package cftp.blocks;

import cftp.CFTP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class FieryBlock extends Block {

    // NOTE. This makes fire not able to attach to the block anymore.
    private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1.0, 1.0, 1.0, 15.0, 15.0, 15.0);

    public FieryBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (world instanceof ServerWorld serverWorld && entity instanceof PlayerEntity player) {
            if (player.isSneaking()) {
                player.damage(serverWorld, serverWorld.getDamageSources().hotFloor(), 2.0F);
            }
        }
    }

    // TODO
    //  1. If a block below turns into an air_block this block changes into an entity
    //  this entity falls. and when it hits a collision with anything it sets everything
    //  around on fire. (this can be done with neighbour change function)
    //  2. Every now and then this block should set on fire all 8 neighbouring blocks.
    //  In case it's Hay or SawdustBlock it transforms it into FireBlocks instead.
    //  3. Make it like fire more and more: // net.minecraft.block.FireBlock
    //  4. If on netherrack its existence is infinite.
    //  5. new placed blocks adject to this block are set ablaze from the moment of their placement.
    //  6. add a recipe flint and steel + sawdust block + bucket -> fiery_powder_bucket

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            world.addParticle(
                    ParticleTypes.LARGE_SMOKE, // choose your particle type
                    x, y, z,
                    0.00, 0.04, 0.00 // motion
            );
        }

        {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            world.addParticle(
                    ParticleTypes.SMOKE, // choose your particle type
                    x, y, z,
                    0.00, 0.04, 0.00 // motion
            );
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world instanceof ServerWorld serverWorld) {
            BlockState belowState = world.getBlockState(pos.down());

            if (belowState.isAir()) {
                CFTP.LOGGER.info("is air a!");
                serverWorld.breakBlock(pos, true);
            }
        }

        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (world instanceof ServerWorld serverWorld) {
            BlockState belowState = world.getBlockState(pos.down());

            if (belowState.isAir()) {
                CFTP.LOGGER.info("is air b!");
                serverWorld.breakBlock(pos, true);
            }
        }
    }

    // TODO.
    // First call
    //  world.scheduleBlockTick(pos, this, delayTicks);
    // Then after that many ticks (e.g. 20 = 1 second), Minecraft will call:
    //
    // Every fire spread ticks it will spread fire to all neighbour blocks at once.
    // after a time this block will burn too.

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        CFTP.LOGGER.info("a");
        super.scheduledTick(state, world, pos, random);
    }

}
