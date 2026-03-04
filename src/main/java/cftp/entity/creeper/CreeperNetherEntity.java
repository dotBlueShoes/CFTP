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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperNetherEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 5;

    public CreeperNetherEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
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
        return CreeperMath.CREEPER_TYPE.NETHER.getType();
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            final float chargedPower = this.isCharged() ? 2.0F : 1.0F;

            // For the case of extending the difficulty enum. We provide a default.
            float diameter = this.ExplosionDiameter;
            int dropExplosionItemChance;
            int ghostCreeperChance;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    dropExplosionItemChance = (int)(100 * DROP_EXPLOSION_ITEM_CHANCE_EASY);
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    diameter *= chargedPower;
                } break;
                case NORMAL: {
                    dropExplosionItemChance = (int)(100 * DROP_EXPLOSION_ITEM_CHANCE_NORMAL);
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    diameter *= 1.5f * chargedPower;
                } break;
                case HARD:
                default: {
                    dropExplosionItemChance = (int)(100 * DROP_EXPLOSION_ITEM_CHANCE_HARD);
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    diameter *= 2.0f * chargedPower;
                } break;
            }

            final int iDiameter = (int) diameter;
            final int radius = iDiameter / 2;

            // We're creating a pseudo explosion just to verify the behaviour of blocks when destroyed.
            final ExplosionImpl dummyExplosion = new ExplosionImpl(
                    serverWorld, null, null,
                    null, null, 1, false,
                    Explosion.DestructionType.DESTROY
            );

            var seed = (int)this.getX() + (int)this.getY() + (int)this.getZ();

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
                            var index = PseudoRandom.UNIFORM_PERMUTATION[pseudoRandom] % CreeperMath.NETHER_BLOCKS.length;

                            if (resistance < 100) {
                                CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, CreeperMath.NETHER_BLOCKS[index], blockPos, (itemStack, pos) -> {
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
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
        }
    }

}
