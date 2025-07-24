package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.Random;

public class CreeperEarthEntity extends CreeperElementalEntity {

    protected int explosionRadius = 5;

    public CreeperEarthEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 0)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    final public static BlockState[] EARTH_BLOCKS = {
            Blocks.DIRT.getDefaultState(),
            Blocks.STONE.getDefaultState(),
            Blocks.GRANITE.getDefaultState(),
            Blocks.DIORITE.getDefaultState(),
            Blocks.ANDESITE.getDefaultState(),
            Blocks.GRAVEL.getDefaultState(),
            Blocks.CLAY.getDefaultState(),
    };

    final public static int[] EARTH_BLOCKS_PSEUDO_RAND = {
            1, 4, 5, 0, 2, 4, 3, 3, 1, 6,
            2, 1, 6, 5, 0, 3, 1, 0, 2, 4,
            5, 2, 3, 4, 0, 6, 1, 5, 2, 6,
            4, 2, 5, 1, 3, 0, 6, 5, 3, 6,
            1, 0, 2, 4, 3, 2, 6, 1, 4, 0,
            3, 2, 4, 5, 6, 3, 5, 2, 6, 4,
            0, 5, 1, 3, 2, 4, 6, 1, 0, 5,
            2, 6, 4, 3, 0, 1, 5, 6, 2, 4,
            1, 3, 5, 2, 4, 0, 6, 5, 1, 3,
            4, 0, 6, 2, 1, 5, 0, 4, 6, 3,
            2, 6, 1, 0, 4, 5, 3, 2, 6, 5,
            0, 4, 1, 3, 2, 6, 4, 5, 0, 1,
            2, 3, 6, 5, 4, 2, 3, 0, 5, 6,
            4, 1, 2, 6, 5, 0, 3, 4, 1, 5,
            6, 2, 0, 4, 3, 1, 2, 6, 5, 4,
            2, 1, 3, 6, 0, 4, 5, 2, 3, 1,
            6, 0, 5, 4, 2, 6, 1, 3, 4, 0
    };

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final float chargedRadius = this.isCharged() ? 2.0F : 1.0F;
            final int radius = (int)(this.explosionRadius * chargedRadius);
            final int half = radius / 2;

            // We're creating a pseudo explosion just to verify if the behaviour of blocks when destroyed.
            final ExplosionImpl explosion = new ExplosionImpl(
                    serverWorld, null, null,
                    null, null, 1, false,
                    Explosion.DestructionType.DESTROY
            );

            for (int y = 0; y < radius; ++y) {
                for (int x = 0; x < radius; ++x) {
                    for (int z = 0; z < radius; ++z) {

                        if (Math.pow(x - half, 2) + Math.pow(y - half, 2) + Math.pow(z - half, 2) <= Math.pow(half, 2)) {
                            BlockPos blockPos = BlockPos.ofFloored(
                                    this.getX() + x - half,
                                    this.getY() + y - half,
                                    this.getZ() + z - half
                            );

                            BlockState state = serverWorld.getBlockState(blockPos);
                            Block block = state.getBlock();

                            var start = (int)this.getX() % 3 + (int)this.getY() % 3 + (int)this.getZ() % 3;
                            var index = EARTH_BLOCKS_PSEUDO_RAND[start + (x * radius * radius) + (y * radius) + z];
                            CFTP.LOGGER.info("num: {}", index);

                            ///EARTH_BLOCKS

                            serverWorld.setBlockState(blockPos, EARTH_BLOCKS[index], Block.NOTIFY_ALL);

                            // So that specific blocks won't drop.
                            if (block.shouldDropItemsOnExplosion(explosion)) {

                                if (block.equals(Blocks.GRASS_BLOCK)) {
                                    block = Blocks.DIRT;
                                }

                                ItemStack itemStack = new ItemStack(block.asItem(), 1);

                                Block.dropStack(serverWorld, blockPos, itemStack);
                            }


                        }

                    }
                }
            }

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();
        }
    }

}
