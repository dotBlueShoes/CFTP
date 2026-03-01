package cftp.entity;

import cftp.CFTP;
import cftp.blocks.FieryBlock;
import cftp.registries.CFTPBlocks;
import cftp.registries.CFTPEntities;
import cftp.utility.FieryLogic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FieryBlockEntity extends TntEntity {

    private @Nullable LivingEntity causingEntity;

    public FieryBlockEntity(EntityType<? extends TntEntity> entityType, World world) {
        super(entityType, world);
    }

    public FieryBlockEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter, BlockState blockState) {
        this(CFTPEntities.FIERY_PROJECTILE, world);
        this.setBlockState(blockState);

        float offX = (world.random.nextInt(50) - 25) * 0.01f;
        float offZ = (world.random.nextInt(50) - 25) * 0.01f;

        this.setPosition(x + offX, y, z + offZ);
        this.setVelocity(0.0, -0.1f, 0.0);

        this.setFuse(80);

        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;

        this.causingEntity = igniter;
    }

    private void explode(ServerWorld serverWorld) {
        BlockPos pos = new BlockPos((int)this.getX() - 1, (int)this.getY(), (int)this.getZ() - 1);

        BlockPos sh = pos.south();
        BlockPos nh = pos.north();
        BlockPos et = pos.east();
        BlockPos wt = pos.west();
        BlockPos dn = pos.down();
        BlockPos up = pos.up();

        //if (state.getBlock() == CFTPBlocks.SAW_DUST_BLOCK) {
        //    world.setBlockState(pos, CFTPBlocks.FIERY_BLOCK.getDefaultState().with(FieryBlock.AGE, 0), Block.NOTIFY_ALL);

        if (FieryLogic.isStateTriggering(serverWorld, pos))
            FieryLogic.igniteBlock(serverWorld, pos, random);
            //serverWorld.setBlockState(pos, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        if (FieryLogic.isStateTriggering(serverWorld, sh))
            FieryLogic.igniteBlock(serverWorld, sh, random);
            //serverWorld.setBlockState(sh, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        if (FieryLogic.isStateTriggering(serverWorld, nh))
            FieryLogic.igniteBlock(serverWorld, nh, random);
            //serverWorld.setBlockState(nh, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        if (FieryLogic.isStateTriggering(serverWorld, et))
            FieryLogic.igniteBlock(serverWorld, et, random);
            //serverWorld.setBlockState(et, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        if (FieryLogic.isStateTriggering(serverWorld, wt))
            FieryLogic.igniteBlock(serverWorld, wt, random);
            //serverWorld.setBlockState(wt, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        if (FieryLogic.isStateTriggering(serverWorld, dn))
            FieryLogic.igniteBlock(serverWorld, dn, random);
            //serverWorld.setBlockState(dn, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
        if (FieryLogic.isStateTriggering(serverWorld, up))
            FieryLogic.igniteBlock(serverWorld, up, random);
            //serverWorld.setBlockState(up, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);

        serverWorld.playSound(
                null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE,
                SoundCategory.PLAYERS
        );

        serverWorld.spawnParticles(
                ParticleTypes.POOF,
                getX(), getY(), getZ(),
                20,
                0.5, 0.5, 0.5,
                0.02
        );

    }


    @Override
    public void tick() {

        World world = this.getWorld();
        int fuse;

        this.tickPortalTeleportation();
        this.applyGravity();
        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.99));

        fuse = this.getFuse() - 1;

        if (world instanceof ServerWorld serverWorld) {
            boolean hitBlock = this.horizontalCollision || this.verticalCollision;

            List<Entity> entities = serverWorld.getOtherEntities(
                    this,
                    this.getBoundingBox(),
                    Entity::isAlive
            );

            boolean hitEntity = !entities.isEmpty();

            if (hitBlock || hitEntity) fuse = 0;

            for (Entity entity : entities) {
                DamageSource source = serverWorld.getDamageSources().explosion(entity, getOwner());
                entity.damage(serverWorld, source, 8.0F);
            }
        }

        this.setFuse(fuse);

        if (fuse <= 0) {
            if (world instanceof ServerWorld serverWorld) {
                this.explode(serverWorld);
                this.discard();
            }
        } else {
            this.updateWaterState();
            if (world.isClient) {
                world.addParticle(
                        ParticleTypes.SMOKE,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        0.0, 0.0, 0.0
                );
            }
        }
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return this.causingEntity;
    }

    @Override
    public void copyFrom(Entity original) {
        super.copyFrom(original);
        if (original instanceof FieryBlockEntity sulphurEntity) {
            this.causingEntity = sulphurEntity.causingEntity;
        }
    }

    @Override
    protected Box calculateDefaultBoundingBox(Vec3d pos) {
        float size = 0.25f; // so it's 50% size of a cube
        return new Box(
                pos.x - size, pos.y - size, pos.z - size,
                pos.x + size, pos.y + size, pos.z + size
        );
    }

}
