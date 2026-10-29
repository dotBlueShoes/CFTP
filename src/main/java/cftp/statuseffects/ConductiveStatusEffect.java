package cftp.statuseffects;

import cftp.CFTP;
import cftp.config.CFTPData;
import cftp.registries.CFTPParticles;
import cftp.registries.CFTPStatusEffects;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ConductiveStatusEffect extends StatusEffect {

    public static final int EFFECT_DURATION = 15 * 30;

    protected final Random random = Random.create();

    public ConductiveStatusEffect(StatusEffectCategory category, int color) {
        super(category, color, CFTPParticles.ELECTRIC_PARTICLE);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld serverWorld, LivingEntity entity, int amplifier) {
        StatusEffectInstance instance = entity.getStatusEffect(CFTPStatusEffects.CONDUCTIVE_STATUS_EFFECT);

        // Detecting and taking action at very last tick and only when raining/thunder.
        if (instance != null && instance.getDuration() <= 1 && serverWorld.isRaining()) {
            int level = instance.getAmplifier();

            if (serverWorld.isSkyVisible(entity.getBlockPos())) { // Lighting Bolt Strike
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld, SpawnReason.NATURAL);
                if (lightningEntity != null) {
                    lightningEntity.refreshPositionAfterTeleport(entity.getX(), entity.getY(), entity.getZ());
                    lightningEntity.setCosmetic(true);
                    serverWorld.spawnEntity(lightningEntity);
                }

                serverWorld.createExplosion(
                        entity, entity.getX(), entity.getY(), entity.getZ(),
                        CFTPData.creeperLighting.power, World.ExplosionSourceType.MOB
                );
            }

            { // Conductive Decrement - Reapply.
                if (--level > -1) {
                    int strikeTick = (this.random.nextInt(EFFECT_DURATION) + 1);

                    entity.addStatusEffect(
                            new StatusEffectInstance(
                                    CFTPStatusEffects.CONDUCTIVE_STATUS_EFFECT,
                                    EFFECT_DURATION + strikeTick,
                                    level,
                                    true,
                                    true,
                                    true
                            )
                    );
                }
            }

        }

        return true;
    }
}
