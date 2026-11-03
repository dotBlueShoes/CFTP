package cftp.statuseffects;

import cftp.CFTP;
import cftp.packets.LoopingSoundPayload;
import cftp.registries.CFTPParticles;
import cftp.registries.CFTPStatusEffects;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;

public class BuryingStatusEffect extends StatusEffect {

    //LoopingSoundInstance
    //sound = new LoopingSoundInstance(ModSounds.MY_SOUND, pos);

    public static final Vec3d SLOW = new Vec3d(0.05, 0.05, 0.05); // I -> 5%
    public static float SLOW_PER_LEVEL = 0.03f;                            // n -> 5%

    public static final int DURATION     = 20 * 8;                         // I -> 8 seconds
    public static int DURATION_PER_LEVEL = 20 * 4;                         // n -> 4 seconds

    public BuryingStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld serverWorld, LivingEntity entity, int amplifier) {
        StatusEffectInstance instance = entity.getStatusEffect(CFTPStatusEffects.BURYING_STATUS_EFFECT);

        if (instance != null) {

            // Stop burying sound being played when effect ends.
            if (entity instanceof ServerPlayerEntity player && instance.getDuration() <= 1) {
                LoopingSoundPayload payload = new LoopingSoundPayload(Vec3d.ZERO, false);
                ServerPlayNetworking.send(player, payload);
            }

            // Create particles on the surface implying burying.
            if (instance.getDuration() % 40 == 0) { // every 40 ticks
                int particleY = getNextTopNonOpaqueCube(
                        serverWorld,
                        (int) entity.getX(),
                        (int) entity.getY(),
                        (int) entity.getZ()
                );

                serverWorld.spawnParticles(CFTPParticles.BURYING_PARTICLE,
                        entity.getX(), particleY + 0.125f, entity.getZ(),
                        10,
                        0.25f, 0.25f, 0.25f,
                        1.00f
                );
            }

        }

        return true;
    }

    private int getNextTopNonOpaqueCube(ServerWorld serverWorld, int initialX, int initialY, int initialZ) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        int y = initialY;

        for (; y < serverWorld.getTopY(Heightmap.Type.MOTION_BLOCKING, initialX, initialZ); y++) {
            pos.set(initialX, y, initialZ);
            BlockState state = serverWorld.getBlockState(pos);
            if (!state.isFullCube(serverWorld, pos)) break;
        }

        return y;
    }

    //@Override
    //public void onEntityRemoval(ServerWorld world, LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
    //    super.onEntityRemoval(world, entity, amplifier, reason);
    //
    //    CFTP.LOGGER.info("a");
    //    if (entity instanceof ServerPlayerEntity player) {
    //        CFTP.LOGGER.info("b");
    //        LoopingSoundPayload payload = new LoopingSoundPayload(null, false);
    //        ServerPlayNetworking.send(player, payload);
    //    }
    //}

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
