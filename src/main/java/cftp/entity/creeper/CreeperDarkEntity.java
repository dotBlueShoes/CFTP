package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.List;

public class CreeperDarkEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 12;
    protected int DARKNESS_EFFECT_TICKS_EASY = 20 * 30;     // = 0m 30s
    protected int DARKNESS_EFFECT_TICKS_NORMAL = 20 * 45;   // = 0m 45s
    protected int DARKNESS_EFFECT_TICKS_HARD = 20 * 60;     // = 1m 00s

    public CreeperDarkEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
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
        return CreeperMath.CREEPER_TYPE.DARK.getType();
    }


    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            final float chargedPower = this.isCharged() ? 2.0F : 1.0F;
            float diameter = this.ExplosionDiameter;

            int darknessEffectTicks;
            int ghostCreeperChance;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    darknessEffectTicks = DARKNESS_EFFECT_TICKS_EASY * (int) chargedPower;
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                    diameter *= chargedPower;
                }
                break;
                case NORMAL: {
                    darknessEffectTicks = DARKNESS_EFFECT_TICKS_NORMAL * (int) chargedPower;
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                    diameter *= 1.25f * chargedPower;
                }
                break;
                case HARD:
                default: {
                    darknessEffectTicks = DARKNESS_EFFECT_TICKS_HARD * (int) chargedPower;
                    ghostCreeperChance = (int) (255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                    diameter *= 1.50f * chargedPower;
                }
                break;
            }

            final int iDiameter = (int) diameter;
            final int radius = iDiameter / 2;

            // We're creating a pseudo explosion just to verify the behaviour of blocks when destroyed.
            final ExplosionImpl explosion = new ExplosionImpl(
                    serverWorld, null, null,
                    null, null, 1, false,
                    Explosion.DestructionType.KEEP
            );

            { // The DARKNESS effect is being applied in BOX rather in SPHERE. That's OK.
                double darknessRadius = radius - 3;

                Box box = new Box(
                        this.getX() - darknessRadius,
                        this.getY() - darknessRadius,
                        this.getZ() - darknessRadius,
                        this.getX() + darknessRadius,
                        this.getY() + darknessRadius,
                        this.getZ() + darknessRadius
                );

                List<ServerPlayerEntity> players = serverWorld.getPlayers(p -> p.getBoundingBox().intersects(box));

                for (ServerPlayerEntity player : players) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.DARKNESS,
                            darknessEffectTicks,
                            4,   // amplifier (0 == level I)
                            true,        // ambient (optional)
                            true,        // showParticles (set false to hide)
                            true         // showIcon
                    ));
                }
            }


            // Destroy ALL light blocks
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
                            int emittedLight = state.getLuminance();

                            if (block.shouldDropItemsOnExplosion(explosion) && emittedLight > 0) {

                                serverWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);

                                // This might lag. -> tho I am being told it shouldn't really.
                                // todo:
                                //  1. Consider multiple explosions at the very same time. Maybe some null checking
                                //   and extra registering so that we know a block only breaks once for sure.
                                //  2. Maybe i can cull blocks of said type. - Tho it would behave differently from a normal creeper then.
                                var drops = CreeperMath.GetBlockLootTable(serverWorld, this, block);
                                for (ItemStack drop : drops) {
                                    Block.dropStack(serverWorld, blockPos, drop);
                                }
                            }

                        }
                    }
                }
            }

            this.playExplosionSound(serverWorld);
            this.createExplosionParticles(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
        }
    }

    public void createExplosionParticles(ServerWorld serverWorld) {
        serverWorld.spawnParticles(
                ParticleTypes.POOF,
                this.getX(),
                this.getY() + 1,
                this.getZ(),
                30,
                4, 2, 4,
                0.02
        );
    }

    public void createWalkingParticle() {
        this.getWorld().addParticle(
                ParticleTypes.ASH,
                this.getParticleX(0.5),
                this.getRandomBodyY() + 0.25,
                this.getParticleZ(0.5),
                (this.random.nextDouble() - 0.5) * 2.0,
                -this.random.nextDouble(),
                (this.random.nextDouble() - 0.5) * 2.0
        );
    }

    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            createWalkingParticle();
        }

        super.tickMovement();
    }

}
