package cftp.mixin;


import cftp.utility.JohnHelper;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

@Mixin(HostileEntity.class)
public abstract class HostileEntityMixin extends PathAwareEntity
        implements Monster {

    protected HostileEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public @Nullable EntityData initialize(
            ServerWorldAccess world,
            LocalDifficulty difficulty,
            SpawnReason spawnReason,
            @Nullable EntityData entityData
    ) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData);

        // There 1/5 that it will have blood status effect.
        if (this.random.nextInt(100) < 25) { // 20%
            JohnHelper.applyJohnBasedOnDifficulty(this, null);
        }

        return data;
    }

}
