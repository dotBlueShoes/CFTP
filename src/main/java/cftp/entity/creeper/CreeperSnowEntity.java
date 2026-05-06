package cftp.entity.creeper;

import cftp.CFTP;
import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
import cftp.utility.Shapes;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperSnowEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperSnow.power;

    public CreeperSnowEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperSnow.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperSnow.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperSnow.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.SNOW.getType();
    }


    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();

                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
                float diameter = this.power;

                int dropExplosionItemChance;
                int ghostCreeperChance;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        diameter *= chargedPower;
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_EASY);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    }
                    break;
                    case NORMAL: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_NORMAL);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                        diameter *= 1.5f * chargedPower;
                    }
                    break;
                    case HARD:
                    default: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_HARD);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                        diameter *= 2.0f * chargedPower;
                    }
                    break;
                }

                final int iDiameter = (int) diameter;
                final int radius = iDiameter / 2;

                // We're creating a pseudo explosion just to verify the behaviour of blocks when destroyed.
                final ExplosionImpl dummyExplosion = new ExplosionImpl(
                        serverWorld, null, null,
                        null, null, 1, false,
                        Explosion.DestructionType.DESTROY
                );

                var seed = (int) this.getX() + (int) this.getY() + (int) this.getZ();

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
                                float resistance = state.getBlock().getBlastResistance();

                                var pseudoRandom = (Math.abs(seed + (x * iDiameter * iDiameter) + (y * iDiameter) + z)) % 256;
                                var index = PseudoRandom.UNIFORM_PERMUTATION[pseudoRandom] % CreeperMath.SNOW_BLOCKS.length;

                                if (resistance < 100) {
                                    CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, CreeperMath.SNOW_BLOCKS[index], blockPos, (itemStack, pos) -> {
                                        if (this.random.nextInt(100) < dropExplosionItemChance) {
                                            Block.dropStack(serverWorld, blockPos, itemStack);
                                        }
                                    });
                                }

                            }
                        }
                    }
                }

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
    public void tickMovement() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (this.random.nextInt(256) > 252) {

                Block block = serverWorld.getBlockState(this.getBlockPos()).getBlock();

                if (block == Blocks.AIR) {
                    BlockState state = Blocks.SNOW.getDefaultState();
                    serverWorld.setBlockState(this.getBlockPos(), state, Block.NOTIFY_ALL_AND_REDRAW);
                } else if (block == Blocks.CAULDRON) {
                    BlockState state = Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 1);
                    serverWorld.setBlockState(this.getBlockPos(), state, Block.NOTIFY_ALL_AND_REDRAW);
                } else if (block == Blocks.POWDER_SNOW_CAULDRON) {
                    BlockState state = serverWorld.getBlockState(this.getBlockPos());
                    int currentLevel = state.get(LeveledCauldronBlock.LEVEL);
                    CFTP.LOGGER.info("Level: {}", currentLevel);

                    if (currentLevel < 3) { // max level is 3
                        serverWorld.setBlockState(
                                this.getBlockPos(),
                                state.with(LeveledCauldronBlock.LEVEL, ++currentLevel),
                                Block.NOTIFY_ALL_AND_REDRAW
                        );
                    }
                }



                serverWorld.playSound(
                        null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.BLOCK_SNOW_FALL,
                        SoundCategory.HOSTILE,
                        0.8f, 1.1f
                );
            }
        }

        super.tickMovement();
    }

}
