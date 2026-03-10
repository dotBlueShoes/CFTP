package cftp.entity.creeper;

import cftp.CFTP;
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
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.List;

public class CreeperGoldenEntity extends CreeperElementalEntity {

    protected float power = CFTPData.creeperGolden.power;

    final int MAX_ABSORPTION = 40;

    public CreeperGoldenEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, CFTPData.creeperGolden.health)
                .add(EntityAttributes.MOVEMENT_SPEED, CFTPData.creeperGolden.movementSpeed)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, CFTPData.creeperGolden.followRange);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.GOLDEN.getType();
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            if (!isDefused()) {
                final Difficulty difficulty = this.getWorld().getDifficulty();

                final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
                float diameter = this.power;

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

                // We're creating a pseudo explosion just to verify the behaviour of blocks when destroyed.
                final ExplosionImpl dummyExplosion = new ExplosionImpl(
                        serverWorld, null, null,
                        null, null, 1, false,
                        Explosion.DestructionType.DESTROY
                );

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

                    List<ServerPlayerEntity> players = serverWorld.getPlayers(
                            p -> p.getBoundingBox().intersects(box)
                    );

                    List<ItemEntity> arrows = serverWorld.getEntitiesByClass(
                            ItemEntity.class, box, entity -> entity.getStack().isOf(Items.ARROW)
                    );

                    List<ItemEntity> carrots = serverWorld.getEntitiesByClass(
                            ItemEntity.class, box, entity -> entity.getStack().isOf(Items.CARROT)
                    );

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

                    for (ItemEntity item : arrows) {
                        int count = item.getStack().getCount();
                        ItemStack itemStack = new ItemStack(Items.SPECTRAL_ARROW, count);
                        item.setStack(itemStack);
                    }

                    for (ItemEntity item : carrots) {
                        int count = item.getStack().getCount();
                        ItemStack itemStack = new ItemStack(Items.GOLDEN_CARROT, count);
                        item.setStack(itemStack);
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
                                    CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.GOLD_ORE.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        Block.dropStack(serverWorld, blockPos, itemStack);
                                    });
                                } else if (block == Blocks.DEEPSLATE) {
                                    CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.DEEPSLATE_GOLD_ORE.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        Block.dropStack(serverWorld, blockPos, itemStack);
                                    });
                                } else if (block == Blocks.NETHERRACK) {
                                    CreeperMath.onGeneralReplace(serverWorld, dummyExplosion, state, Blocks.NETHER_GOLD_ORE.getDefaultState(), blockPos, (itemStack, pos) -> {
                                        Block.dropStack(serverWorld, blockPos, itemStack);
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
    protected void playExplosionSound(World world) {
        world.playSound(
                null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE,
                1.1F, 0.3f
        );
    }

}
