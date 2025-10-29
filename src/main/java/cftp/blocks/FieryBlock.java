package cftp.blocks;

import cftp.CFTP;
import cftp.mixin.FireBlockAccessor;
import cftp.registries.CFTPBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.dimension.NetherPortal;
import org.jetbrains.annotations.Nullable;

public class FieryBlock extends Block {

    public static final IntProperty AGE = Properties.AGE_15;
    //private static final int LIFETIME = 15;
    //private int currentLifetime = LIFETIME;

    // NOTE. This makes fire not able to attach to the block anymore.
    private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1.0, 1.0, 1.0, 15.0, 15.0, 15.0);

    public FieryBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (world instanceof ServerWorld serverWorld && entity instanceof PlayerEntity player) {
            if (player.isSneaking()) {
                player.damage(serverWorld, serverWorld.getDamageSources().hotFloor(), 2.0F);
            }
        }
    }

    private static int getFireTickDelay(Random random) {
        return 30 + random.nextInt(10);
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        world.scheduleBlockTick(pos, this, getFireTickDelay(world.random));
    }

    // to make fire create FieryBlocks when in contact with SawDustBlock
    // i need to create a mixin for FireBlock.trySpreadingFire() and make an if SawDust then FieryBlock

    private static boolean isOverworldOrNether(World world) {
        return world.getRegistryKey() == World.OVERWORLD || world.getRegistryKey() == World.NETHER;
    }

    private static boolean shouldLightPortalAt(World world, BlockPos pos, Direction direction) {
        if (!isOverworldOrNether(world)) {
            return false;
        } else {
            BlockPos.Mutable mutable = pos.mutableCopy();
            boolean bl = false;

            for (Direction direction2 : Direction.values()) {
                if (world.getBlockState(mutable.set(pos).move(direction2)).isOf(Blocks.OBSIDIAN)) {
                    bl = true;
                    break;
                }
            }

            if (!bl) {
                return false;
            } else {
                Direction.Axis axis = direction.getAxis().isHorizontal()
                        ? direction.rotateYCounterclockwise().getAxis()
                        : Direction.Type.HORIZONTAL.randomAxis(world.random);
                return NetherPortal.getNewPortal(world, pos, axis).isPresent();
            }
        }
    }

    private void igniteBlock (World world, BlockPos pos, Direction direction) {
        BlockState blockState = world.getBlockState(pos);

        boolean isValid = blockState.isAir() &&
                FireBlock.getState(world, pos).canPlaceAt(world, pos) ||
                shouldLightPortalAt(world, pos, direction);

        if (blockState.getBlock() == CFTPBlocks.SAW_DUST_BLOCK) {
            world.setBlockState(pos, CFTPBlocks.FIERY_BLOCK.getDefaultState().with(AGE, 0), Block.NOTIFY_ALL);
        } else if (isValid) {
            BlockState fireState = AbstractFireBlock.getState(world, pos).with(AGE, 0);
            world.setBlockState(pos, fireState, Block.NOTIFY_ALL);
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        // 1. Forever fire (netherrack)
        // 2. is that formula correct?
        // 3. Check for what normal fire checks...
        // 4. isIncreasedFireBurnout

        { // AGING
            int prevAge = state.get(AGE);
            CFTP.LOGGER.info("prevAge: {}", prevAge);
            if (prevAge >= 15) {
                world.removeBlock(pos, false);
                return;
            }

            int nextAge = Math.min(15, prevAge + random.nextInt(3) / 2);

            state = state.with(AGE, nextAge);
            world.setBlockState(pos, state, Block.NO_REDRAW);
        }

        world.scheduleBlockTick(pos, this, getFireTickDelay(world.random));

        //boolean isIncreasedFireBurnout = world.getBiome(pos).isIn(BiomeTags.INCREASED_FIRE_BURNOUT);
        //int sfe = isIncreasedFireBurnout ? -50 : 0;
        //int currentAge = 15; //state.get(FireBlock.AGE);

        //this.trySpreadingFire(world, pos.east(),  300 + sfe, random, currentAge);
        //this.trySpreadingFire(world, pos.west(),  300 + sfe, random, currentAge);
        //this.trySpreadingFire(world, pos.down(),  250 + sfe, random, currentAge);
        //this.trySpreadingFire(world, pos.up(),    250 + sfe, random, currentAge);
        //this.trySpreadingFire(world, pos.north(), 300 + sfe, random, currentAge);
        //this.trySpreadingFire(world, pos.south(), 300 + sfe, random, currentAge);
        //world.setBlockState(pos.east(), Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);

        BlockPos a = pos.east();
        BlockPos b = pos.west();
        BlockPos c = pos.north();
        BlockPos d = pos.south();
        BlockPos e = pos.up();
        BlockPos f = pos.down();

        igniteBlock(world, a, Direction.random(random));
        igniteBlock(world, b, Direction.random(random));
        igniteBlock(world, c, Direction.random(random));
        igniteBlock(world, d, Direction.random(random));
        igniteBlock(world, e, Direction.random(random));
        igniteBlock(world, f, Direction.random(random));

    }

    //private void trySpreadingFire(World world, BlockPos pos, int spreadFactor, Random random, int currentAge) {
    //    int i = this.getSpreadChance(world.getBlockState(pos));
    //    if (random.nextInt(spreadFactor) < i) {
    //        BlockState blockState = world.getBlockState(pos);
    //        if (random.nextInt(currentAge + 10) < 5 && !world.hasRain(pos)) {
    //            int j = Math.min(currentAge + random.nextInt(5) / 4, 15);
    //            world.setBlockState(pos, this.getStateWithAge(world, pos, j), Block.NOTIFY_ALL);
    //        } else {
    //            world.removeBlock(pos, false);
    //        }
    //
    //        Block block = blockState.getBlock();
    //        if (block instanceof TntBlock) {
    //            TntBlock.primeTnt(world, pos);
    //        }
    //    }
    //}

    //private int getSpreadChance(BlockState state) {
    //    FireBlock fire = (FireBlock) Blocks.FIRE;
    //    FireBlockAccessor accessor = (FireBlockAccessor) fire;
    //
    //    return state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED) ?
    //            0 :
    //            accessor.getSpreadChances().getInt(state.getBlock());
    //}

    //private BlockState getStateWithAge(WorldView world, BlockPos pos, int age) {
    //    BlockState blockState = FireBlock.getState(world, pos);
    //    return blockState.isOf(Blocks.FIRE) ? blockState.with(FireBlock.AGE, age) : blockState;
    //}

    // TODO
    //  1. If a block below turns into an air_block this block changes into an entity
    //  this entity falls. and when it hits a collision with anything it sets everything
    //  around on fire. (this can be done with neighbour change function)
    //  2. Every now and then this block should set on fire all 8 neighbouring blocks.
    //  In case it's Hay or SawdustBlock it transforms it into FireBlocks instead.
    //  3. Make it like fire more and more: // net.minecraft.block.FireBlock
    //  4. If on netherrack its existence is infinite.
    //  5. new placed blocks adject to this block are set ablaze from the moment of their placement.
    //  6. add a recipe flint and steel + sawdust block + bucket -> fiery_powder_bucket

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            world.addParticle(
                    ParticleTypes.LARGE_SMOKE, // choose your particle type
                    x, y, z,
                    0.00, 0.04, 0.00 // motion
            );
        }

        {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            world.addParticle(
                    ParticleTypes.SMOKE, // choose your particle type
                    x, y, z,
                    0.00, 0.04, 0.00 // motion
            );
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world instanceof ServerWorld serverWorld) {
            BlockState belowState = world.getBlockState(pos.down());

            if (belowState.isAir()) {
                CFTP.LOGGER.info("is air a!");
                serverWorld.breakBlock(pos, true);
            }
        }

        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (world instanceof ServerWorld serverWorld) {
            BlockState belowState = world.getBlockState(pos.down());

            if (belowState.isAir()) {
                CFTP.LOGGER.info("is air b!");
                serverWorld.breakBlock(pos, true);
            }
        }
    }

    // TODO.
    // First call
    //  world.scheduleBlockTick(pos, this, delayTicks);
    // Then after that many ticks (e.g. 20 = 1 second), Minecraft will call:
    //
    // Every fire spread ticks it will spread fire to all neighbour blocks at once.
    // after a time this block will burn too.

}
