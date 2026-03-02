package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperBrewerEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 5;

    public CreeperBrewerEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.BREWER.getType();
    }

    @Override // TODO
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;
            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();
        }
    }

    public void createWalkingParticle() {
        // scaling seems to not matter
        if (this.random.nextInt(100) > 90) {
            for (int i = 0; i < 1; i++) {
                this.getWorld().addParticle(
                        ParticleTypes.EFFECT,
                        this.getParticleX(0.5),
                        this.getRandomBodyY() + 0.25,
                        this.getParticleZ(0.5),
                        (this.random.nextDouble() - 0.5) * 0.0001,
                        -this.random.nextDouble() * 0.0001,
                        (this.random.nextDouble() - 0.5) * 0.0001
                );
            }
        }
    }

    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            createWalkingParticle();
        }

        super.tickMovement();
    }

}
