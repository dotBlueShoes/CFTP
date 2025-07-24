package cftp.entity.charge;

import cftp.CFTP;
import cftp.entity.CFTPEntities;
import cftp.item.CFTPItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
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

    private void createSplash(ServerWorld world, BlockPos position) {
        world.spawnParticles(ParticleTypes.SPLASH,
            position.getX() + 0.5, position.getY() + 0.9, position.getZ() + 0.5,
            7,
            0.5f, 0.5f, 0.5f,
            1.0f
        );
    }

    @Override
    protected void createExplosion(Vec3d pos) {

        if (this.getWorld() instanceof ServerWorld serverWorld) {

            BlockPos position = BlockPos.ofFloored(
                    pos.x,
                    pos.y,
                    pos.z
            );

            BlockState state = serverWorld.getBlockState(position);
            Block block = state.getBlock();

            // ISSUE
            //  If we hit a block that is underneath cauldron (for example)
            //  we will be then at cauldron position. Which is invalid
            //  In such a case we should instead get previous x,y,z position
            //  from its flight path.

            //CauldronBlock cauldron;
            //BlockState blockState = Blocks.WATER_CAULDRON.getDefaultState();
            //world.setBlockState(pos, blockState);
            //world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
            //world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON, pos, 0);

            //int condition = (block instanceof Waterloggable) << 1

            int condition = 0;
            condition = (block instanceof Waterloggable                                 ? 1 : condition);
            condition = (block == Blocks.AIR                                            ? 2 : condition);
            condition = (block == Blocks.CAULDRON                                       ? 3 : condition);
            condition = (block == Blocks.WATER_CAULDRON                                 ? 4 : condition);
            condition = (block == Blocks.LAVA_CAULDRON                                  ? 5 : condition);
            condition = (block == Blocks.LAVA && state.getFluidState().isStill()  ? 6 : condition);
            condition = (block == Blocks.LAVA && !state.getFluidState().isStill() ? 7 : condition);

            switch (condition) {
                case 1: {
                    Waterloggable waterloggable = (Waterloggable) block;
                    waterloggable.tryFillWithFluid(serverWorld, position, state, Fluids.WATER.getDefaultState());
                } break;

                case 2: {
                    serverWorld.setBlockState(position, Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL);
                } break;

                case 3:
                case 4: {
                    BlockState blockState = Blocks.WATER_CAULDRON.getDefaultState().with(Properties.LEVEL_3, 3);
                    serverWorld.setBlockState(position, blockState);
                } break;

                case 5:
                case 6:{
                    BlockState blockState = Blocks.OBSIDIAN.getDefaultState();
                    serverWorld.setBlockState(position, blockState);
                } break;

                case 7: {
                    BlockState blockState = Blocks.COBBLESTONE.getDefaultState();
                    serverWorld.setBlockState(position, blockState);
                } break;

                default: {
                    serverWorld.breakBlock(position, true, this.getOwner());
                    serverWorld.setBlockState(position, Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL);
                }
            }

            createSplash(serverWorld, position);




            //if (block instanceof Waterloggable waterloggable) {
            //    waterloggable.tryFillWithFluid(serverWorld, position, state, Fluids.WATER.getDefaultState());
            //} else if (state == Blocks.AIR.getDefaultState()) {
            //    serverWorld.setBlockState(position, Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL);
            //} else if (state == Blocks.CAULDRON.getDefaultState()) {
            //    BlockState blockState = Blocks.WATER_CAULDRON.getDefaultState().with(Properties.LEVEL_3, 3);
            //    serverWorld.setBlockState(position, blockState);
            //} else if (state == Blocks.LAVA.getDefaultState()) {
            //    BlockState blockState = Blocks.COBBLESTONE.getDefaultState();
            //    serverWorld.setBlockState(position, blockState);
            //} else if (block == Blocks.WATER_CAULDRON){
            //    serverWorld.spawnParticles(
            //            ParticleTypes.SPLASH,
            //            position.getX() + 0.5, position.getY() + 0.9, position.getZ() + 0.5,
            //            7, // number of particles
            //            0.5f, 0.5f, 0.5f, // spread
            //            1.0f // particle speed
            //    );
            //} else {
            //    // LOG CFTP.LOGGER.info("Weird explosion!");
            //    serverWorld.breakBlock(position, true, this.getOwner());
            //    serverWorld.setBlockState(position, Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL);
            //}

        }
    }

    @Override
    public boolean shouldRender(double distance) {
        return this.age < 2 && distance < MAX_RENDER_DISTANCE_WHEN_NEWLY_SPAWNED ? false : super.shouldRender(distance);
    }
}
