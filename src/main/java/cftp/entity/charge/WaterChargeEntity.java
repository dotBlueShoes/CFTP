package cftp.entity.charge;

import cftp.CFTP;
import cftp.entity.CFTPEntities;
import cftp.item.CFTPItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class WaterChargeEntity extends AbstractWaterChargeEntity {

    private static final float MAX_RENDER_DISTANCE_WHEN_NEWLY_SPAWNED = MathHelper.square(3.5F);
    private int deflectCooldown = 5;

    public WaterChargeEntity(EntityType<? extends AbstractWaterChargeEntity> entityType, World world) {
        super(entityType, world);
    }

    public WaterChargeEntity(PlayerEntity player, World world, double x, double y, double z) {
        super(CFTPEntities.WATER_CHARGE, world, player, x, y, z);
    }

    public WaterChargeEntity(World world, double x, double y, double z, Vec3d velocity) {
        super(CFTPEntities.WATER_CHARGE, x, y, z, velocity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return CFTPItems.WATER_CHARGE;
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

        if (this.getWorld() instanceof ServerWorld serverWorld) {

            BlockPos blockPos = BlockPos.ofFloored(
                    pos.x,
                    pos.y,
                    pos.z
            );

            serverWorld.setBlockState(blockPos, Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL);

        }
    }

    @Override
    public boolean shouldRender(double distance) {
        return this.age < 2 && distance < MAX_RENDER_DISTANCE_WHEN_NEWLY_SPAWNED ? false : super.shouldRender(distance);
    }
}
