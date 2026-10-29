package cftp.mixin;

import cftp.CFTP;
import cftp.registries.CFTPStatusEffects;
import cftp.statuseffects.BuryingStatusEffect;
import cftp.utility.IPassThroughEntity;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;

@Mixin(AbstractBlock.class)
public abstract class BlockMixin {

    @Final
    @Shadow
    protected boolean collidable;

    @Unique
    private static Box getBlockBox(BlockPos pos) {
        double x0 = pos.getX();
        double y0 = pos.getY();
        double z0 = pos.getZ();
        double x1 = pos.getX() + 1.0f;
        double y1 = pos.getY() + 1.0f;
        double z1 = pos.getZ() + 1.0f;

        return new Box(x0, y0, z0, x1, y1, z1);
    }

    /**
     * @author dotBlueShoes
     * @reason dunno
     */
    @Overwrite
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        CFTP.LOGGER.info("collision-b");
        Box newBox = getBlockBox(pos);

        if (entity.getBoundingBox().intersects(newBox)) {
            if (entity instanceof LivingEntity livingEntity && livingEntity.hasStatusEffect(CFTPStatusEffects.BURYING_STATUS_EFFECT)) {
                var instance = livingEntity.getStatusEffect(CFTPStatusEffects.BURYING_STATUS_EFFECT);

                // Should never trigger.
                assert instance != null;

                entity.slowMovement(state, BuryingStatusEffect.SLOW.add(
                    instance.getAmplifier() * BuryingStatusEffect.SLOW_PER_LEVEL)
                );
            }
            // Otherwise entities pass-through.
        }
    }

    /**
     * @author dotBlueShoes
     * @reason dunno
     */
    @Overwrite
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Entity entity = context instanceof EntityShapeContext entityContext
                ? entityContext.getEntity()
                : null;

        if (entity instanceof LivingEntity livingEntity
                && (livingEntity.hasStatusEffect(CFTPStatusEffects.BURYING_STATUS_EFFECT)
                    || livingEntity instanceof IPassThroughEntity)
        ) {
            return VoxelShapes.empty();
        }

        //return super.getCollisionShape(state, world, pos, context);

        return this.collidable ? state.getOutlineShape(world, pos) : VoxelShapes.empty();
    }

}
