package cftp.entity;

import cftp.CFTP;
import cftp.registries.CFTPEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FieryBlockEntity extends TntEntity {

    private @Nullable LivingEntity causingEntity;
    private boolean teleported;

    private static final ExplosionBehavior TELEPORTED_EXPLOSION_BEHAVIOR = new ExplosionBehavior(){

        @Override
        public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
            if (state.isOf(Blocks.NETHER_PORTAL)) {
                return false;
            }
            return super.canDestroyBlock(explosion, world, pos, state, power);
        }

        @Override
        public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
            if (blockState.isOf(Blocks.NETHER_PORTAL)) {
                return Optional.empty();
            }
            return super.getBlastResistance(explosion, world, pos, blockState, fluidState);
        }

    };

    public FieryBlockEntity(EntityType<? extends TntEntity> entityType, World world) {
        super(entityType, world);
    }

    public FieryBlockEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter, BlockState blockState) {
        this(CFTPEntities.FIERY_PROJECTILE, world);
        this.setBlockState(blockState);

        float offX = (world.random.nextInt(50) - 25) * 0.01f;
        float offZ = (world.random.nextInt(50) - 25) * 0.01f;

        this.setPosition(x + offX, y, z + offZ);
        this.setVelocity(0.0, -0.1f, 0.0);

        this.setFuse(80);

        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;

        this.causingEntity = igniter;
    }

    private void explode(ServerWorld serverWorld) {
        BlockPos pos = new BlockPos((int)this.getX() - 1, (int)this.getY(), (int)this.getZ() - 1);

        BlockPos sh = pos.south();
        BlockPos nh = pos.north();
        BlockPos et = pos.east();
        BlockPos wt = pos.west();
        BlockPos dn = pos.down();
        BlockPos up = pos.up();

        serverWorld.setBlockState(pos, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);

        serverWorld.setBlockState(sh, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        serverWorld.setBlockState(nh, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        serverWorld.setBlockState(et, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        serverWorld.setBlockState(wt, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        serverWorld.setBlockState(dn, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        serverWorld.setBlockState(up, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);

        serverWorld.playSound(
                null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE,
                SoundCategory.PLAYERS
        );

        serverWorld.spawnParticles(
                ParticleTypes.POOF,
                getX(), getY(), getZ(),
                20,
                0.5, 0.5, 0.5,
                0.02
        );

    }


    @Override
    public void tick() {

        World world = this.getWorld();
        int fuse;

        this.tickPortalTeleportation();
        this.applyGravity();
        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.99));

        fuse = this.getFuse() - 1;

        if (world instanceof ServerWorld serverWorld) {
            boolean hitBlock = this.horizontalCollision || this.verticalCollision;

            List<Entity> entities = serverWorld.getOtherEntities(
                    this,
                    this.getBoundingBox(),
                    Entity::isAlive
            );

            boolean hitEntity = !entities.isEmpty();

            if (hitBlock || hitEntity) fuse = 0;

            for (Entity entity : entities) {
                DamageSource source = serverWorld.getDamageSources().explosion(entity, getOwner());
                entity.damage(serverWorld, source, 8.0F);
            }
        }

        this.setFuse(fuse);

        if (fuse <= 0) {
            if (world instanceof ServerWorld serverWorld) {
                this.explode(serverWorld);
                this.discard();
            }
        } else {
            this.updateWaterState();
            if (world.isClient) {
                world.addParticle(
                        ParticleTypes.SMOKE,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        0.0, 0.0, 0.0
                );
            }
        }
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return this.causingEntity;
    }

    @Override
    public void copyFrom(Entity original) {
        super.copyFrom(original);
        if (original instanceof FieryBlockEntity sulphurEntity) {
            this.causingEntity = sulphurEntity.causingEntity;
        }
    }

    @Override
    protected Box calculateDefaultBoundingBox(Vec3d pos) {
        float size = 0.25f; // so it's 50% size of a cube
        return new Box(
                pos.x - size, pos.y - size, pos.z - size,
                pos.x + size, pos.y + size, pos.z + size
        );
    }

}
