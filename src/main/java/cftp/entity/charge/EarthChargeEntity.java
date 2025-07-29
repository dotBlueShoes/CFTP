package cftp.entity.charge;

import cftp.entity.CFTPEntities;
import cftp.registries.CFTPItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class EarthChargeEntity extends AbstractEarthChargeEntity {

    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
            true, false, Optional.of(1.22F), Registries.BLOCK.getOptional(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
    );

    private static final float EXPLOSION_POWER = 1.2F;
    private static final float MAX_RENDER_DISTANCE_WHEN_NEWLY_SPAWNED = MathHelper.square(3.5F);
    private int deflectCooldown = 5;

    public EarthChargeEntity(EntityType<? extends AbstractEarthChargeEntity> entityType, World world) {
        super(entityType, world);
    }

    public EarthChargeEntity(PlayerEntity player, World world, double x, double y, double z) {
        super(CFTPEntities.EARTH_CHARGE, world, player, x, y, z);
    }

    public EarthChargeEntity(World world, double x, double y, double z, Vec3d velocity) {
        super(CFTPEntities.EARTH_CHARGE, x, y, z, velocity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return CFTPItems.EARTH_CHARGE;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.deflectCooldown > 0) {
            this.deflectCooldown--;
        }
    }

    @Override
    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
        return this.deflectCooldown > 0 ? false : super.deflect(deflection, deflector, owner, fromAttack);
    }

    @Override
    protected void createExplosion(Vec3d pos) {

        //CFTP.LOGGER.info("Earth Charge Hit Call!");

        // Stunning Players
        // https://www.reddit.com/r/MinecraftCommands/comments/om6iay/how_do_i_make_a_stick_stun_a_player/

        //if (this.getWorld() instanceof ServerWorld serverWorld) {
        //
        //}

    }

    @Override
    public boolean shouldRender(double distance) {
        return this.age < 2 && distance < MAX_RENDER_DISTANCE_WHEN_NEWLY_SPAWNED ? false : super.shouldRender(distance);
    }
}
