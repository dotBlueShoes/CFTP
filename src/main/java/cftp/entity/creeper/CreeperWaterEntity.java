package cftp.entity.creeper;

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

public class CreeperWaterEntity extends CreeperElementalEntity {

    protected int explosionRadius = 5;

    public CreeperWaterEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 18)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final float chargedRadius = this.isCharged() ? 2.0F : 1.0F;
            final int radius = (int)(this.explosionRadius * chargedRadius);
            final int half = radius / 2;

            // TODO
            // 1. Instead of dropping the blocks at their destroyed block position
            //  I could make it so that the drop appears always at mob explosion position.
            //  - Such behaviour should be easier to render and calculate as less ItemStacks would be created.
            //  however i wound need to to count all the different items that exploded.
            // 2. Randomize a little the sphere shape.
            // 3. ??? Make it so long short grass drops seeds instead.

            // We're creating a pseudo explosion just to verify if the behaviour of blocks when destroyed.
            final ExplosionImpl explosion = new ExplosionImpl(
                    serverWorld, null, null,
                    null, null, 1, false,
                    Explosion.DestructionType.DESTROY
            );

            // unoptimized
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

                            serverWorld.setBlockState(blockPos, Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL);

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

            //1 // This can be optimized simply calculate 1/8 of the sphere then generate blocks on all sides of the sphere.
            //1 {
            //1     // 1st. calculate the points
            //1     for (int y = 0; y < half; ++y) {
            //1         for (int x = 0; x < half; ++x) {
            //1             for (int z = 0; z < half; ++z)  {
            //1
            //1                 if (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) <= Math.pow(3,  2)) {
            //1
            //1                     BlockPos blockPos = BlockPos.ofFloored(
            //1                             this.getX() + x,
            //1                             this.getY() + y,
            //1                             this.getZ() + z
            //1                     );
            //1
            //1                     serverWorld.setBlockState(blockPos, Blocks.ACACIA_PLANKS.getDefaultState(), Block.NOTIFY_ALL);
            //1                 }
            //1
            //1             }
            //1         }
            //1     }
            //1
            //1     // 2nd. Generate them on all sides.
            //1 }

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();
        }
    }

}
