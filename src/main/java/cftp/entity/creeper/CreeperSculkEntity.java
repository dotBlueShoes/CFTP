package cftp.entity.creeper;

import cftp.CFTP;
import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
import cftp.utility.Shapes;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.List;
import java.util.function.BiConsumer;

public class CreeperSculkEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperSculk.power;

    public final int EFFECT_TICKS_EASY = 20 * 4;   // 0m 04s
    public final int EFFECT_TICKS_NORM = 20 * 7;   // 0m 07s
    public final int EFFECT_TICKS_HARD = 20 * 10;  // 0m 10s

    public CreeperSculkEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperSculk.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperSculk.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperSculk.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.SCULK.getType();
    }

    @Override // TODO
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();
                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
                float diameter = this.power;

                int dropExplosionItemChance;
                int ghostCreeperChance;
                int effectTicks;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_EASY);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        effectTicks = EFFECT_TICKS_EASY;
                        diameter *= chargedPower;
                    }
                    break;
                    case NORMAL: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_NORMAL);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        effectTicks = EFFECT_TICKS_NORM;
                        diameter *= 1.125f * chargedPower;
                    }
                    break;
                    case HARD:
                    default: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_HARD);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        effectTicks = EFFECT_TICKS_HARD;
                        diameter *= 1.250f * chargedPower;
                    }
                    break;
                }

                // We're creating a pseudo explosion just to verify the behaviour of blocks when destroyed.
                final ExplosionImpl dummyExplosion = new ExplosionImpl(
                        serverWorld, null, null,
                        null, null, 1, false,
                        Explosion.DestructionType.DESTROY
                );

                final DamageSource damageSource = serverWorld.getDamageSources().explosion(dummyExplosion);
                final int iDiameter = (int) diameter;
                final int radius = iDiameter / 2;

                { // Apply effects and damage items on the ground.
                    Box box = new Box(
                            this.getX() - radius,
                            this.getY() - radius,
                            this.getZ() - radius,
                            this.getX() + radius,
                            this.getY() + radius,
                            this.getZ() + radius
                    );

                    List<ServerPlayerEntity> players = serverWorld.getPlayers(
                            p -> p.getBoundingBox().intersects(box)
                    );

                    List<ItemEntity> items = serverWorld.getEntitiesByClass(
                            ItemEntity.class, box, entity -> true
                    );

                    for (ItemEntity item : items) {
                        item.damage(serverWorld, damageSource, ITEM_EXPLOSION_DAMAGE);
                    }

                    for (ServerPlayerEntity player : players) {
                        if (player.interactionManager.getGameMode() != GameMode.CREATIVE) {
                            player.addStatusEffect(new StatusEffectInstance(
                                    StatusEffects.NAUSEA,
                                    effectTicks,
                                    1,
                                    true,
                                    true,
                                    true
                            ));

                            player.addStatusEffect(new StatusEffectInstance(
                                    StatusEffects.BLINDNESS,
                                    effectTicks,
                                    1,
                                    true,
                                    true,
                                    true
                            ));

                            player.damage(serverWorld, damageSource, 4);
                        }
                    }
                }

                for (int y = 0; y < iDiameter; ++y) { // gen
                    for (int x = 0; x < iDiameter; ++x) {
                        for (int z = 0; z < iDiameter; ++z) {
                            int distance = (int) Shapes.sphereDistance(x, y, z, radius);

                            if (distance < radius - 1) {
                                BlockPos blockPos = BlockPos.ofFloored(
                                        this.getX() + x - radius,
                                        this.getY() + y - radius,
                                        this.getZ() + z - radius
                                );

                                BlockState state = serverWorld.getBlockState(blockPos);
                                float resistance = state.getBlock().getBlastResistance();

                                if (resistance < 100) {
                                    // This creeper also gives experience for blocks destroyed.
                                    state.onStacksDropped(serverWorld, blockPos, ItemStack.EMPTY, true);
                                    state.onExploded(serverWorld, blockPos, dummyExplosion, (itemStack, pos) -> {
                                                if (this.random.nextInt(100) < dropExplosionItemChance) {
                                                    Block.dropStack(serverWorld, blockPos, itemStack);
                                                }
                                            }
                                    );
                                }

                            } else if (distance < radius) {
                                BlockPos blockPos = BlockPos.ofFloored(
                                        this.getX() + x - radius,
                                        this.getY() + y - radius,
                                        this.getZ() + z - radius
                                );

                                BlockState state = serverWorld.getBlockState(blockPos);

                                if (state.isIn(BlockTags.SCULK_REPLACEABLE) && this.random.nextInt(10) < 6) {
                                    serverWorld.setBlockState(blockPos, Blocks.SCULK.getDefaultState(), Block.NOTIFY_ALL);
                                }
                            }
                        }
                    }
                }

                serverWorld.spawnParticles(
                        ParticleTypes.SCULK_SOUL,
                        this.getX() + 0.5, this.getY() + 1.15, this.getZ() + 0.5,
                        2,
                        0.2, 0.0, 0.2,
                        0.0
                );

                this.playExplosionSound(serverWorld);
                SpawnGhostCreeper(serverWorld, ghostCreeperChance);
            } else {
                this.playDefusedExplosionSound(serverWorld);
            }

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();
        }
    }

    @Override
    protected void playExplosionSound(World world) {
        world.playSound(
                null, this.getBlockPos(),
                SoundEvents.BLOCK_SCULK_CATALYST_BLOOM, SoundCategory.HOSTILE,
                6.0F, 0.6F + random.nextFloat() * 0.4F
        );
    }

}
