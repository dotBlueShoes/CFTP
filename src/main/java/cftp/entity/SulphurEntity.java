package cftp.entity;

import cftp.registries.CFTPBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SulphurEntity extends TntEntity {

    //public BlockState sulphurBlockState = null; // Blocks.TNT.getDefaultState();
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

    public SulphurEntity(EntityType<? extends TntEntity> entityType, World world) {
        super(entityType, world);
    }

    public SulphurEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter, BlockState blockState) {
        this(CFTPEntities.SULPHUR_CLOUD, world);
        this.setBlockState(blockState);

        this.setPosition(x, y, z);
        double d = world.random.nextDouble() * 6.2831854820251465;

        this.setVelocity(-Math.sin(d) * 0.02, 0.2f, -Math.cos(d) * 0.02);
        this.setFuse(18 + world.random.nextInt(6)); // [ 18 ; 24 )

        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;

        this.causingEntity = igniter;
    }

    //@Override
    //public boolean hasNoGravity() {
    //    return true; // Makes Minecraft skip the gravity update
    //}
    //
    //@Override
    //public void applyExplosionKnockback(float strength, double dx, double dz) {
    //    // Do nothing: ignore explosion knockback
    //}

    private void explode() {
        float explosionPower = 2.0f;
        this.getWorld().createExplosion(
                this,
                Explosion.createDamageSource(this.getWorld(), this),
                this.teleported ? TELEPORTED_EXPLOSION_BEHAVIOR : null,
                this.getX(), this.getBodyY(0.0625), this.getZ(),
                explosionPower,
                false,
                World.ExplosionSourceType.TNT // todo. this makes those particles.
        );
    }

    @Override
    public void tick() {
        this.tickPortalTeleportation();
        this.applyGravity();
        //this.move(MovementType.SELF, this.getVelocity());
        //this.tickBlockCollision();
        this.setVelocity(Vec3d.ZERO);

        //if (this.isOnGround()) {
        //    this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
        //}

        int i = this.getFuse() - 1;
        this.setFuse(i);

        if (i <= 0) {
            this.discard();
            if (!this.getWorld().isClient) {
                this.explode();
            }
        } else {
            this.updateWaterState();
            if (this.getWorld().isClient) {
                this.getWorld().addParticle(
                        ParticleTypes.SMOKE,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        0.0, 0.0, 0.0
                );
            }
        }
    }

    //@Override
    //private void setTeleported(boolean teleported) {
    //    this.teleported = teleported;
    //}
    //@Override
    //@Nullable
    //public Entity teleportTo(TeleportTarget teleportTarget) {
    //    return super.teleportTo(teleportTarget);
    //}

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return this.causingEntity;
    }

    @Override
    public void copyFrom(Entity original) {
        super.copyFrom(original);
        if (original instanceof SulphurEntity sulphurEntity) {
            this.causingEntity = sulphurEntity.causingEntity;
        }
    }

    //@Override
    //protected void initDataTracker(DataTracker.Builder builder) {
    //    builder.add(FUSE, 80);
    //    builder.add(BLOCK_STATE, CFTPBlocks.SULPHUR_ORE.getDefaultState());
    //}

}
