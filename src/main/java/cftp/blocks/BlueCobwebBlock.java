package cftp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlueCobwebBlock extends CobwebBlock {

    static final VoxelShape[] COLLISION_SHAPES = {
            Block.createCuboidShape(2, 0, 0, 14, 16, 16), // X
            Block.createCuboidShape(0, 2, 0, 16, 14, 16), // Y
            Block.createCuboidShape(0, 0, 2, 16, 16, 14), // Z
    };

    static final Vec3d[] COLLISION_OFFSETS = {
            new Vec3d(2.0 / 16.0, 0.0, 0.0), // X
            new Vec3d(0.0, 2.0 / 16.0, 0.0), // Y
            new Vec3d(0.0, 0.0, 2.0 / 16.0), // Z
    };

    // TODO. Make it directed.
    static final Vec3d COBWEB_SLOW         = new Vec3d(0.90, 0.90, 0.10);
    static final Vec3d COBWEB_WEAVING_SLOW = new Vec3d(1.00, 1.00, 0.25);

    // TODO. Change to BlockState logic.
    public enum Shape {
        X(0),
        Y(1),
        Z(2),
        ;

        private final int type;

        Shape(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    };

    static final Shape type = Shape.Z;

    public BlueCobwebBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        Box newBox = getBlockBox(pos);

        if (entity.getBoundingBox().intersects(newBox)) {
            if (entity instanceof LivingEntity livingEntity && livingEntity.hasStatusEffect(StatusEffects.WEAVING)) {
                entity.slowMovement(state, COBWEB_WEAVING_SLOW);
            }
            entity.slowMovement(state, COBWEB_SLOW);
        }
    }

    // Define the Block's collision box.
    private static Box getBlockBox(BlockPos pos) {
        double x0 = (double)(pos.getX()) + COLLISION_OFFSETS[type.getType()].x;
        double y0 = (double)(pos.getY()) + COLLISION_OFFSETS[type.getType()].y;
        double z0 = (double)(pos.getZ()) + COLLISION_OFFSETS[type.getType()].z;
        double x1 = (double)(pos.getX()) + 1.0 + COLLISION_OFFSETS[type.getType()].x;
        double y1 = (double)(pos.getY()) + 1.0 + COLLISION_OFFSETS[type.getType()].y;
        double z1 = (double)(pos.getZ()) + 1.0 + COLLISION_OFFSETS[type.getType()].z;

        return new Box(x0, y0, z0, x1, y1, z1);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPES[type.getType()];
    }

}
