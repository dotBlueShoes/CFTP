package cftp.mixin;

import cftp.CFTP;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {

    //@Inject(method = "populateEntities", at = @At("HEAD"))
    //private static void onPopulateEntities(ServerWorldAccess world, RegistryEntry<Biome> biomeEntry, ChunkPos chunkPos, Random random, CallbackInfo ci) {
    //    System.out.println("Populating entities in biome: " + biomeEntry);
    //}

    @Unique
    private static BlockPos getEntitySpawnPos(WorldView world, EntityType<?> entityType, int x, int z) {
        int i = world.getTopY(SpawnRestriction.getHeightmapType(entityType), x, z);
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, i, z);
        if (world.getDimension().hasCeiling()) {
            do {
                mutable.move(Direction.DOWN);
            } while (!world.getBlockState(mutable).isAir());

            do {
                mutable.move(Direction.DOWN);
            } while (world.getBlockState(mutable).isAir() && mutable.getY() > world.getBottomY());
        }

        return SpawnRestriction.getLocation(entityType).adjustPosition(world, mutable.toImmutable());
    }

    //  Why does it still generate so little?
    // The reason why strider is picked more times than horse is because strider has a much better chance for spawning then
    // en entity that requires solid ground underneath.

    /**
     * @author dotBlueShoes
     * @reason I am testing things. Tell me it this breaks things.
     */
    @Overwrite
    public static void populateEntities(ServerWorldAccess world, RegistryEntry<Biome> biomeEntry, ChunkPos chunkPos, Random random) {
        SpawnSettings spawnSettings = biomeEntry.value().getSpawnSettings();
        Pool<SpawnSettings.SpawnEntry> pool = spawnSettings.getSpawnEntries(SpawnGroup.CREATURE);
        if (!pool.isEmpty()) {
            int i = chunkPos.getStartX();
            int j = chunkPos.getStartZ();

            while (random.nextFloat() < spawnSettings.getCreatureSpawnProbability()) {
                    Optional<SpawnSettings.SpawnEntry> optional = pool.getOrEmpty(random);
                    if (!optional.isEmpty()) {
                        SpawnSettings.SpawnEntry spawnEntry = (SpawnSettings.SpawnEntry)optional.get();
                        int k = spawnEntry.minGroupSize + random.nextInt(1 + spawnEntry.maxGroupSize - spawnEntry.minGroupSize);
                        EntityData entityData = null;
                        int l = i + random.nextInt(16);
                        int m = j + random.nextInt(16);
                        int n = l;
                        int o = m;

                        for (int p = 0; p < k; p++) {
                            boolean bl = false;

                            for (int q = 0; !bl && q < 4; q++) {
                                BlockPos blockPos = getEntitySpawnPos(world, spawnEntry.type, l, m);
                                if (spawnEntry.type.isSummonable() && SpawnRestriction.isSpawnPosAllowed(spawnEntry.type, world, blockPos)) {
                                    float f = spawnEntry.type.getWidth();
                                    double d = MathHelper.clamp((double)l, (double)i + f, i + 16.0 - f);
                                    double e = MathHelper.clamp((double)m, (double)j + f, j + 16.0 - f);
                                    if (!world.isSpaceEmpty(spawnEntry.type.getSpawnBox(d, blockPos.getY(), e))
                                            || !SpawnRestriction.canSpawn(spawnEntry.type, world, SpawnReason.CHUNK_GENERATION, BlockPos.ofFloored(d, blockPos.getY(), e), world.getRandom())) {
                                        continue;
                                    }

                                    Entity entity;
                                    try {
                                        entity = spawnEntry.type.create(world.toServerWorld(), SpawnReason.NATURAL);
                                        CFTP.LOGGER.info("name {}", entity.getDisplayName());
                                    } catch (Exception var27) {
                                        //LOGGER.warn("Failed to create mob", (Throwable)var27);
                                        continue;
                                    }

                                    if (entity == null) {
                                        continue;
                                    }

                                    entity.refreshPositionAndAngles(d, blockPos.getY(), e, random.nextFloat() * 360.0F, 0.0F);

                                    if (entity instanceof MobEntity mobEntity && mobEntity.canSpawn(world, SpawnReason.CHUNK_GENERATION) && mobEntity.canSpawn(world)) {
                                        entityData = mobEntity.initialize(world, world.getLocalDifficulty(mobEntity.getBlockPos()), SpawnReason.CHUNK_GENERATION, entityData);
                                        world.spawnEntityAndPassengers(mobEntity);
                                        bl = true;
                                    } else {
                                        CFTP.LOGGER.info("call");
                                    }
                                }

                                l += random.nextInt(5) - random.nextInt(5);

                                for (m += random.nextInt(5) - random.nextInt(5); l < i || l >= i + 16 || m < j || m >= j + 16; m = o + random.nextInt(5) - random.nextInt(5)) {
                                    l = n + random.nextInt(5) - random.nextInt(5);
                                }
                            }
                        }
                    }
            }
        }
    }

}
