package cftp.entity.creeper;

import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
import cftp.utility.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.List;

public class CreeperNetherEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperNether.power;

    public CreeperNetherEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperNether.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperNether.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 0)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperNether.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.NETHER.getType();
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();

                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;

                // For the case of extending the difficulty enum. We provide a default.
                float diameter = this.power;
                int dropExplosionItemChance;
                int ghostCreeperChance;

                switch (difficulty) {
                    case PEACEFUL:
                    case EASY: {
                        dropExplosionItemChance = (int) (100 * DROP_EXPLOSION_ITEM_CHANCE_EASY);
                        ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                        diameter *= chargedPower;
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

                final DamageSource damageSource = serverWorld.getDamageSources().explosion(dummyExplosion);
                var seed = (int) this.getX() + (int) this.getY() + (int) this.getZ();

                { // Destroy some items on the ground.
                    Box box = new Box(
                            this.getX() - radius,
                            this.getY() - radius,
                            this.getZ() - radius,
                            this.getX() + radius,
                            this.getY() + radius,
                            this.getZ() + radius
                    );

                    List<ItemEntity> items = serverWorld.getEntitiesByClass(
                            ItemEntity.class, box, entity -> true
                    );

                    for (ItemEntity item : items) {
                        item.damage(serverWorld, damageSource, ITEM_EXPLOSION_DAMAGE);
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
                SpawnGhostCreeper(serverWorld, ghostCreeperChance);
            } else {
                this.playDefusedExplosionSound(serverWorld);
            }

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();
        }
    }

}
