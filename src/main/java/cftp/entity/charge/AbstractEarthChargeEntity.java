package cftp.entity.charge;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractEarthChargeEntity extends ExplosiveProjectileEntity implements FlyingItemEntity {
    public static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
            true, false, Optional.empty(), Registries.BLOCK.getOptional(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
    );
    public static final double field_52224 = 0.25;

    public AbstractEarthChargeEntity(EntityType<? extends AbstractEarthChargeEntity> entityType, World world) {
        super(entityType, world);
        this.accelerationPower = 0.0;
    }

    public AbstractEarthChargeEntity(EntityType<? extends AbstractEarthChargeEntity> type, World world, Entity owner, double x, double y, double z) {
        super(type, x, y, z, world);
        this.setOwner(owner);
        this.accelerationPower = 0.0;
    }

    public AbstractEarthChargeEntity(EntityType<? extends AbstractEarthChargeEntity> entityType, double d, double e, double f, Vec3d vec3d, World world) {
        super(entityType, d, e, f, vec3d, world);
        this.accelerationPower = 0.0;
    }

    @Override
    protected Box calculateDefaultBoundingBox(Vec3d pos) {
        float f = this.getType().getDimensions().width() / 2.0F;
        float g = this.getType().getDimensions().height();
        float h = 0.15F;
        return new Box(pos.x - f, pos.y - 0.15F, pos.z - f, pos.x + f, pos.y - 0.15F + g, pos.z + f);
    }

    @Override
    public boolean collidesWith(Entity other) {
        return other instanceof AbstractEarthChargeEntity ? false : super.collidesWith(other);
    }

    @Override
    protected boolean canHit(Entity entity) {
        if (entity instanceof AbstractEarthChargeEntity) {
            return false;
        } else {
            return entity.getType() == EntityType.END_CRYSTAL ? false : super.canHit(entity);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (this.getWorld() instanceof ServerWorld) {

            LivingEntity sourceEntity = (LivingEntity)this.getOwner();
            Entity targetEntity = entityHitResult.getEntity();

            // What a world we're living.
            assert sourceEntity != null;
            sourceEntity.onAttacking(targetEntity);


            if (targetEntity instanceof LivingEntity livingTargetEntity) {

                // Minecraft by default has 20 ticks per second. And so here a second is equal to 20.
                // command: effect give @p minecraft:slowness 5 255

                final int DURATION = 35;
                final int SECOND = 20;

                // PETRIFICATION
                // - entity becomes unable to move or jump for a specified duration of time.

                { // Slowness
                    StatusEffectInstance instance = new StatusEffectInstance(StatusEffects.SLOWNESS, SECOND * DURATION, 255);
                    livingTargetEntity.addStatusEffect(instance, sourceEntity);
                }

                // { // Weakness
                //     StatusEffectInstance instance = new StatusEffectInstance(StatusEffects.WEAKNESS, SECOND * DURATION, 255);
                //     livingTargetEntity.addStatusEffect(instance, sourceEntity);
                // }

                { // Negative Jump Boost
                    StatusEffectInstance instance = new StatusEffectInstance(StatusEffects.JUMP_BOOST, SECOND * DURATION, 255);
                    livingTargetEntity.addStatusEffect(instance, sourceEntity);
                }

                // { // Negative HUNGER
                //     StatusEffectInstance instance = new StatusEffectInstance(StatusEffects.HUNGER, SECOND * DURATION, 255);
                //     livingTargetEntity.addStatusEffect(instance, sourceEntity);
                // }

            }

            // TODO - I might make it a AOE ability in the future.
            //this.createExplosion(this.getPos());
        }
    }

    @Override
    public void addVelocity(double deltaX, double deltaY, double deltaZ) {
    }

    protected abstract void createExplosion(Vec3d pos);

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            Vec3i vec3i = blockHitResult.getSide().getVector();
            Vec3d vec3d = Vec3d.of(vec3i).multiply(0.25, 0.25, 0.25);
            Vec3d vec3d2 = blockHitResult.getPos().add(vec3d);
            this.createExplosion(vec3d2);
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    @Override
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Override
    protected float getDrag() {
        return 1.0F;
    }

    @Override
    protected float getDragInWater() {
        return this.getDrag();
    }

    @Nullable
    @Override
    protected ParticleEffect getParticleType() {
        return null;
    }

    protected abstract Item getDefaultItem();

    @Override
    public void tick() {
        if (!this.getWorld().isClient && this.getBlockY() > this.getWorld().getTopYInclusive() + 30) {
            this.createExplosion(this.getPos());
            this.discard();
        } else {
            super.tick();
        }
    }
}
