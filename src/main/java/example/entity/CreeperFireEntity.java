package example.entity;

import example.ExampleMod;
import example.entity.base.CreeperElementalEntity;
import example.goals.CreeperElementalIgniteGoal;
import example.goals.CreeperFireAttackGoal;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Optional;

public class CreeperFireEntity extends CreeperElementalEntity {

    protected int explosionRadius = 9;

    public CreeperFireEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 18)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperElementalIgniteGoal(this, 1.0F));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(4, new CreeperFireAttackGoal(this, 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final float chargedRadius = this.isCharged() ? 2.0F : 1.0F;
            final int radius = (int)(this.explosionRadius * chargedRadius);
            final int half = radius / 2;

            for (int y = 0; y < radius; ++y) {
                for (int x = 0; x < radius; ++x) {
                    for (int z = 0; z < radius; ++z) {

                        if (Math.pow(x - half, 2) + Math.pow(y - half, 2) + Math.pow(z - half, 2) <= Math.pow(half, 2)) {

                            BlockPos blockPos = BlockPos.ofFloored(
                                    this.getX() + x - half,
                                    this.getY() + y - half,
                                    this.getZ() + z - half
                            );

                            // Prob. Required for server’s sake that everything is ok and prob. that check is not required.
                            if (AbstractFireBlock.canPlaceAt(serverWorld, blockPos, Direction.UP)) {

                                BlockState blockState = AbstractFireBlock.getState(serverWorld, blockPos);
                                serverWorld.setBlockState(blockPos, blockState, Block.NOTIFY_ALL_AND_REDRAW);

                            }

                            //serverWorld.setBlockState(blockPos, Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL);

                        }

                    }
                }
            }

            //AbstractFireBlock.canPlaceAt(world, blockPos2, context.getHorizontalPlayerFacing())) {
            //				world.playSound(playerEntity, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
            //				BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
            //				world.setBlockState(blockPos2, blockState2, Block.NOTIFY_ALL_AND_REDRAW);
            //				world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);

            // world.playSound(playerEntity, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);

            // !this.isFlammable(blockState) && !blockState.isSideSolidFullSquare(world, blockPos, Direction.UP))
            // entity.serverDamage(world.getDamageSources().inFire(), this.damage);
            //		super.onEntityCollision(state, world, pos, entity);

            //serverWorld.createExplosion(
            //        this, this.getX(), this.getY(), this.getZ(),
            //        1, World.ExplosionSourceType.MOB
            //);

            //BlockPos blockPos = BlockPos.ofFloored(m, n, o);
            //BlockState blockState = this.world.getBlockState(blockPos);
            //FluidState fluidState = this.world.getFluidState(blockPos);
            //if (!this.world.isInBuildLimit(blockPos)) {
            //    break;
            //}
            //
            //Optional<Float> optional = this.behavior.getBlastResistance(this, this.world, blockPos, blockState, fluidState);
            //if (optional.isPresent()) {
            //    h -= (optional.get() + 0.3F) * 0.3F;
            //}
            //
            //if (h > 0.0F && this.behavior.canDestroyBlock(this, this.world, blockPos, blockState, h)) {
            //    set.add(blockPos);
            //}

            // List<ExplosionImpl.DroppedItem> list = new ArrayList();
            //		Util.shuffle(positions, this.world.random);
            //
            //		for (BlockPos blockPos : positions) {
            //			this.world.getBlockState(blockPos).onExploded(this.world, blockPos, this, (item, pos) -> addDroppedItem(list, item, pos));
            //		}
            //
            //		for (ExplosionImpl.DroppedItem droppedItem : list) {
            //			Block.dropStack(this.world, droppedItem.pos, droppedItem.item);
            //		}

            // if (!state.isAir() && explosion.getDestructionType() != Explosion.DestructionType.TRIGGER_BLOCK) {
            //			Block block = state.getBlock();
            //			boolean bl = explosion.getCausingEntity() instanceof PlayerEntity;
            //			if (block.shouldDropItemsOnExplosion(explosion)) {
            //				BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
            //				LootWorldContext.Builder builder = new LootWorldContext.Builder(world)
            //					.add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
            //					.add(LootContextParameters.TOOL, ItemStack.EMPTY)
            //					.addOptional(LootContextParameters.BLOCK_ENTITY, blockEntity)
            //					.addOptional(LootContextParameters.THIS_ENTITY, explosion.getEntity());
            //				if (explosion.getDestructionType() == Explosion.DestructionType.DESTROY_WITH_DECAY) {
            //					builder.add(LootContextParameters.EXPLOSION_RADIUS, explosion.getPower());
            //				}
            //
            //				state.onStacksDropped(world, pos, ItemStack.EMPTY, bl);
            //				state.getDroppedStacks(builder).forEach(stack -> stackMerger.accept(stack, pos));
            //			}
            //
            //			world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
            //			block.onDestroyedByExplosion(world, pos, explosion);
            //		}

            this.playExplosionSound(serverWorld);
            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();
        }
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return true;
    }

}
