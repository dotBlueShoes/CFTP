package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
import cftp.utility.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperFlipEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 6;

    public CreeperFlipEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 0)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.FLIP.getType();
    }


    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
            float diameter = this.ExplosionDiameter;

            int ghostCreeperChance;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    diameter *= chargedPower;
                } break;
                case NORMAL: {
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    diameter *= 1.25f * chargedPower;
                } break;
                case HARD:
                default: {
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    diameter *= 1.5f * chargedPower;
                } break;
            }

            final int iDiameter = (int) diameter;
            final int radius = iDiameter / 2;

            for (int y = 0; y < radius; ++y) {
                for (int x = 0; x < iDiameter; ++x) {
                    for (int z = 0; z < iDiameter; ++z) {
                        if (Shapes.isSphere(x, y, z, radius)) {

                            // selected
                            BlockPos sblockPos = BlockPos.ofFloored(
                                    this.getX() + x - radius,
                                    this.getY() + y - radius,
                                    this.getZ() + z - radius
                            );

                            // mirrored
                            BlockPos mBlockPos = BlockPos.ofFloored(
                                    this.getX() + x - radius,
                                    this.getY() - (y - radius) - 1,
                                    this.getZ() + z - radius
                            );

                            BlockState sState = serverWorld.getBlockState(sblockPos);
                            var sBlock = sState.getBlock().getDefaultState();

                            BlockState mState = serverWorld.getBlockState(mBlockPos);
                            var mBlock = mState.getBlock().getDefaultState();

                            serverWorld.setBlockState(sblockPos, mBlock, Block.NOTIFY_ALL);
                            serverWorld.setBlockState(mBlockPos, sBlock, Block.NOTIFY_ALL);

                        }
                    }
                }
            }

            serverWorld.createExplosion(
                    this,
                    Explosion.createDamageSource(serverWorld, this),
                    CreeperMath.noDestroyExplosionBehaviour,
                    this.getX(),
                    this.getY() - 2,
                    this.getZ(),
                    diameter,
                    false,
                    World.ExplosionSourceType.MOB,
                    ParticleTypes.EXPLOSION,
                    ParticleTypes.EXPLOSION_EMITTER,
                    SoundEvents.ENTITY_GENERIC_EXPLODE
            );

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
        }
    }

}
