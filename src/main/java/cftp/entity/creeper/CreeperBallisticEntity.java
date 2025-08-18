package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
import cftp.utility.CreeperMath;
import cftp.utility.PseudoRandom;
import cftp.utility.Shapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;

public class CreeperBallisticEntity extends CreeperElementalEntity {

    protected int ExplosionDiameter = 5;

    public CreeperBallisticEntity(EntityType<? extends CreeperElementalEntity> entityType, World world) {
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
        return CreeperMath.CREEPER_TYPE.BALLISTIC.getType();
    }


    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            //this.dead = true;

            { // TNT. But this method is being called over and over so it's wrong to do it here!
                // Create primed TNT
                TntEntity tnt = new TntEntity(EntityType.TNT, serverWorld);
                tnt.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0f, 0.0f);

                // Set fuse duration (default is 80 ticks = 4 seconds)
                tnt.setFuse(80);

                // get player position;
                Vec3d velocity = new Vec3d(1, 1, 1);

                // Apply velocity
                tnt.setVelocity(velocity);

                // Add to world
                serverWorld.spawnEntity(tnt);
            }

            //this.playExplosionSound(serverWorld);
            //this.spawnEffectsCloud();
            //this.onRemoval(serverWorld, RemovalReason.KILLED);
            //this.discard();

            //SpawnGhostCreeper(serverWorld, ghostCreeperChance);
        }
    }

}
