package cftp.blocks;

import cftp.CFTP;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class SoulOre extends SoulSandBlock {

    public SoulOre(Settings settings) {
        super(settings);
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

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        if (world instanceof ServerWorld serverWorld) {
            if (serverWorld.isAir(pos.up())) {
                serverWorld.setBlockState(pos.up(), Blocks.SOUL_FIRE.getDefaultState());
            }
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (world.isAir(pos.up())) {
            world.setBlockState(pos.up(), Blocks.SOUL_FIRE.getDefaultState());
        }
        if (world.isAir(pos.east())) {
            world.setBlockState(pos.east(), Blocks.SOUL_FIRE.getDefaultState());
        }
        if (world.isAir(pos.west())) {
            world.setBlockState(pos.west(), Blocks.SOUL_FIRE.getDefaultState());
        }
        if (world.isAir(pos.south())) {
            world.setBlockState(pos.south(), Blocks.SOUL_FIRE.getDefaultState());
        }
        if (world.isAir(pos.north())) {
            world.setBlockState(pos.north(), Blocks.SOUL_FIRE.getDefaultState());
        }
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);

        int samplesIterator = 0;
        float[] samples = {
                0.50f, 0.370f, 0.27f, 0.62f, 0.51f, 0.18f
        };

        for (int i = 0; i < 3; ++i) {

            double x = pos.getX() + samples[samplesIterator++];
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + samples[samplesIterator++];

            world.addParticle(
                    ParticleTypes.SOUL, // choose your particle type
                    x, y, z,
                    0.00, 0.08, 0.00 // motion
            );

        }

        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL);
    }
}
