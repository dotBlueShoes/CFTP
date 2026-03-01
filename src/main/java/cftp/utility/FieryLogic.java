package cftp.utility;

import cftp.blocks.FieryBlock;
import cftp.registries.CFTPBlocks;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.TntEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.dimension.NetherPortal;

public class FieryLogic {

    public static boolean isOverworldOrNether(World world) {
        return world.getRegistryKey() == World.OVERWORLD || world.getRegistryKey() == World.NETHER;
    }

    public static boolean shouldLightPortalAt(World world, BlockPos pos, Direction direction) {
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

    public static boolean isStateTriggering(ServerWorld serverWorld, BlockPos pos) {
        BlockState state = serverWorld.getBlockState(pos);
        return state == Blocks.AIR.getDefaultState() ||
                (state.isBurnable() && state.getFluidState().isEmpty());
    }

    public static void igniteBlock(ServerWorld world, BlockPos pos, Random random) {

        BlockState fireState = AbstractFireBlock.getState(world, pos);
        BlockState blockState = world.getBlockState(pos);
        Direction direction = Direction.random(random);

        if (fireState.canPlaceAt(world, pos) || shouldLightPortalAt(world, pos, direction)) {
            if (blockState.getBlock() == CFTPBlocks.SAW_DUST_BLOCK) {
                world.setBlockState(pos, CFTPBlocks.FIERY_BLOCK.getDefaultState().with(FieryBlock.AGE, 0), Block.NOTIFY_ALL);
            } else if (blockState.getBlock() == Blocks.TNT) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
                TntEntity tnt = new TntEntity(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f,null);
                world.spawnEntity(tnt);
            } else if (blockState.isAir()) {
                world.setBlockState(pos, fireState, Block.NOTIFY_ALL_AND_REDRAW);
            } else if (blockState.isBurnable()) {
                int destroyChance = random.nextInt(100);

                if (destroyChance > 75) {
                    world.setBlockState(pos, fireState, Block.NOTIFY_ALL_AND_REDRAW);
                }
            }
        }
    }

}
