package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
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

    final public static BlockState[] NETHER_BLOCKS = {
            Blocks.NETHERRACK.getDefaultState(),
            Blocks.BASALT.getDefaultState(),
            Blocks.SOUL_SAND.getDefaultState(),
            Blocks.SOUL_SOIL.getDefaultState(),
            Blocks.GRAVEL.getDefaultState(),
            Blocks.BLACKSTONE.getDefaultState(),
            Blocks.GLOWSTONE.getDefaultState(),
            // More valuable blocks.
            Blocks.NETHER_GOLD_ORE.getDefaultState(),
            Blocks.NETHER_QUARTZ_ORE.getDefaultState(),
            // It has higher chance of producing NETHER_RACK and SOUL_SAND
            Blocks.NETHERRACK.getDefaultState(),
            Blocks.SOUL_SAND.getDefaultState(),
    }; // + 4

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            final float chargedPower = this.isCharged() ? 2.0F : 1.0F;

            // For the case of extending the difficulty enum. We provide a default.
            float diameter = this.ExplosionDiameter;
            int dropExplosionItemChance = (int)(255 * DROP_EXPLOSION_ITEM_CHANCE_EASY);
            int ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);

            switch (difficulty) {
                case EASY: {
                    diameter *= chargedPower;
                } break;
                case NORMAL: {
                    dropExplosionItemChance = (int)(255 * DROP_EXPLOSION_ITEM_CHANCE_NORMAL);
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    diameter *= 1.5f * chargedPower;
                } break;
                case HARD: {
                    dropExplosionItemChance = (int)(255 * DROP_EXPLOSION_ITEM_CHANCE_HARD);
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    diameter *= 2.0f * chargedPower;
                } break;
            }

            final int iDiameter = (int) diameter;
            final int radius = iDiameter / 2;

            // We're creating a pseudo explosion just to verify the behaviour of blocks when destroyed.
            final ExplosionImpl explosion = new ExplosionImpl(
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
                            Block block = state.getBlock();

                            var pseudoRandom = (Math.abs(seed + (x * iDiameter * iDiameter) + (y * iDiameter) + z)) % 256;
                            var index = PseudoRandom.UNIFORM_PERMUTATION[pseudoRandom] % NETHER_BLOCKS.length;

                            serverWorld.setBlockState(blockPos, NETHER_BLOCKS[index], Block.NOTIFY_ALL);

                            // So that specific blocks won't drop and with a chance of not dropping at all.
                            if (block.shouldDropItemsOnExplosion(explosion) && pseudoRandom <= dropExplosionItemChance) {

                                ItemStack itemStack;

                                // TODO. This prob. can be done better.
                                if (block.equals(Blocks.GRASS_BLOCK)) {
                                    block = Blocks.DIRT;
                                    itemStack = new ItemStack(block.asItem(), 1);
                                } else if (block.equals(Blocks.SHORT_GRASS)) {
                                    itemStack = new ItemStack(Items.WHEAT_SEEDS, 1);
                                } else if (block.equals(Blocks.TALL_GRASS)) {
                                    itemStack = new ItemStack(Items.WHEAT_SEEDS, 1);
                                } else {
                                    itemStack = new ItemStack(block.asItem(), 1);
                                }

                                Block.dropStack(serverWorld, blockPos, itemStack);
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
