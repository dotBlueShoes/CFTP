package cftp.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class YellowCobwebBlock extends CobwebBlock {

    final Vec3d COBWEB_SLOW         = new Vec3d(0.10, 0.90, 0.90);
    final Vec3d COBWEB_WEAVING_SLOW = new Vec3d(0.25, 1.00, 1.00);

    public YellowCobwebBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

        if (entity instanceof LivingEntity livingEntity && livingEntity.hasStatusEffect(StatusEffects.WEAVING)) {
            entity.slowMovement(state, COBWEB_WEAVING_SLOW);
        }

        entity.slowMovement(state, COBWEB_SLOW);
    }

}
