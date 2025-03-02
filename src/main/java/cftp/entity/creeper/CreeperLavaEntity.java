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

public class CreeperLavaEntity extends CreeperElementalEntity {

    protected int explosionRadius = 5;

    public CreeperLavaEntity(
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

                            serverWorld.setBlockState(blockPos, Blocks.LAVA.getDefaultState(), Block.NOTIFY_ALL);

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

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return true;
    }

}
