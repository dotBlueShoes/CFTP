package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.registries.CFTPStatusEffects;
import cftp.statuseffects.BuryingStatusEffect;
import cftp.utility.CreeperHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.List;

public class CreeperSandEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperSand.power;

    public CreeperSandEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperSand.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperSand.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperSand.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperHelper.CREEPER_TYPE.SAND.getType();
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();
                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;

                int ghostCreeperChance;
                int buryingLevel;
                float sumPower;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        buryingLevel = 0;
                    } break;
                    case NORMAL: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        buryingLevel = 1;
                    } break;
                    case HARD:
                    default: {
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        buryingLevel = 2;
                    }
                }

                sumPower = power * chargedPower;

                { // apply Conductive Status Effect to all entities around.
                    Box box = new Box(
                            this.getX() - sumPower,
                            this.getY() - sumPower,
                            this.getZ() - sumPower,
                            this.getX() + sumPower,
                            this.getY() + sumPower,
                            this.getZ() + sumPower
                    );

                    List<Entity> entities = serverWorld.getOtherEntities(
                            this,
                            box,
                            entity -> entity instanceof LivingEntity living && living.isAlive()
                    );

                    for (Entity entity : entities) {
                        LivingEntity target = (LivingEntity) entity;

                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        CFTPStatusEffects.BURYING_STATUS_EFFECT,
                                        BuryingStatusEffect.DURATION + (BuryingStatusEffect.DURATION_PER_LEVEL * buryingLevel),
                                        buryingLevel,
                                        true,
                                        true,
                                        true
                                )
                        );
                    }
                }

                SpawnGhostCreeper(serverWorld, ghostCreeperChance);
            } else {
                this.playDefusedExplosionSound(serverWorld);
            }

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, Entity.RemovalReason.KILLED);
            this.discard();
        }
    }

}
