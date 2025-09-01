package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
import cftp.utility.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.List;

public class CreeperGoldenEntity extends CreeperElementalEntity {

    final int MAX_ABSORPTION = 40;
    protected int ExplosionDiameter = 3;

    public CreeperGoldenEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
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
        return CreeperMath.CREEPER_TYPE.GOLDEN.getType();
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
            float diameter = this.ExplosionDiameter;

            int ghostCreeperChance;
            int absorptionGive;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    diameter *= chargedPower;
                    absorptionGive = 10;
                }
                break;
                case NORMAL: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    diameter *= 1.25f * chargedPower;
                    absorptionGive = 20;
                }
                break;
                case HARD:
                default: {
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    diameter *= 1.50f * chargedPower;
                    absorptionGive = 40;
                }
                break;
            }

            final int iDiameter = (int) diameter;
            final int radius = iDiameter / 2;

            { // The absorption effect is being applied in BOX rather in SPHERE. That's OK.
                double absorptionRadius = 5;

                Box box = new Box(
                        this.getX() - absorptionRadius,
                        this.getY() - absorptionRadius,
                        this.getZ() - absorptionRadius,
                        this.getX() + absorptionRadius,
                        this.getY() + absorptionRadius,
                        this.getZ() + absorptionRadius
                );

                List<ServerPlayerEntity> players = serverWorld.getPlayers(p -> p.getBoundingBox().intersects(box));

                for (ServerPlayerEntity player : players) {
                    EntityAttributeInstance maxAbsorption = player.getAttributeInstance(EntityAttributes.MAX_ABSORPTION);
                    EntityAttributeInstance maxHealth = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);

                    assert maxAbsorption != null;
                    assert maxHealth != null;

                    maxAbsorption.setBaseValue(MAX_ABSORPTION);

                    // We're not adding we're always setting, but we don't set to less than what player might have.
                    if (player.getAbsorptionAmount() < absorptionGive) {
                        player.setAbsorptionAmount(absorptionGive);
                    }

                    //maxHealth.setBaseValue(6);
                    //CFTP.LOGGER.info("absorption: {}", player.getAbsorptionAmount());
                }
            }

            for (int y = 0; y < iDiameter; ++y) {
                for (int x = 0; x < iDiameter; ++x) {
                    for (int z = 0; z < iDiameter; ++z) {
                        if (Shapes.isSphere(x, y, z, radius)) {

                            BlockPos blockPos = BlockPos.ofFloored(
                                    this.getX() + x - radius,
                                    this.getY() + y - radius,
                                    this.getZ() + z - radius
                            );

                            BlockState state = serverWorld.getBlockState(blockPos);
                            Block block = state.getBlock();

                            if (block == Blocks.STONE) {
                                serverWorld.setBlockState(blockPos, Blocks.GOLD_ORE.getDefaultState(), Block.NOTIFY_ALL);
                            } else if (block == Blocks.DEEPSLATE) {
                                serverWorld.setBlockState(blockPos, Blocks.DEEPSLATE_GOLD_ORE.getDefaultState(), Block.NOTIFY_ALL);
                            } else if (block == Blocks.NETHERRACK) {
                                serverWorld.setBlockState(blockPos, Blocks.NETHER_GOLD_ORE.getDefaultState(), Block.NOTIFY_ALL);
                            }

                        }
                    }
                }
            }

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
        }
    }

}
