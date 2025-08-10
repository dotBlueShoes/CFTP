package cftp.items;

import cftp.CFTP;
import cftp.registries.CFTPItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ElementalPowderItem extends Item {

    public ElementalPowderItem(Settings settings) {
        super(settings);
    }

    private static void createCombustionParticles(ServerWorld world, BlockPos position) {
        world.spawnParticles(ParticleTypes.EFFECT,
                position.getX() + 0.5, position.getY() + 0.9, position.getZ() + 0.5,
                7,
                0.5f, 0.5f, 0.5f,
                0.1f
        );
    }

    private static void createCombustionSound(ServerWorld world, BlockPos position) {
        world.playSound(
                null, position,
                SoundEvents.ENTITY_AXOLOTL_SPLASH,
                SoundCategory.PLAYERS,
                1.0F, 1.0F
        );
    }

    private static void createCombustionSnowSound(ServerWorld world, BlockPos position) {
        world.playSound(
                null, position,
                SoundEvents.BLOCK_POWDER_SNOW_STEP,
                SoundCategory.PLAYERS,
                1.0F, 1.0F
        );
    }

    public static void cauldronProcess (ServerWorld world, BlockPos position, ItemStack getItem) {
        BlockPos maybeHopperPosition = new BlockPos(position.getX(), position.getY() - 1, position.getZ());
        BlockEntity blockEntity = world.getBlockEntity(maybeHopperPosition);

        // See if block below is hopper.
        if (blockEntity instanceof HopperBlockEntity hopperBlockEntity) {
            HopperBlockEntity.transfer(null, hopperBlockEntity, getItem, null);
        } else {

            ItemEntity itemEntity = new ItemEntity(
                    world,
                    position.getX() + 0.5f,
                    position.getY() + 1,
                    position.getZ() + 0.5f,
                    getItem
            );

            CFTP.LOGGER.info(String.valueOf(itemEntity.getVelocity()));


            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos position = context.getBlockPos();
        World world = context.getWorld();

        BlockState state = world.getBlockState(position);
        Block block = state.getBlock();

        if (world instanceof ServerWorld serverWorld) {

            if (block == Blocks.WATER_CAULDRON) {

                final int count = 1;
                ItemStack waterCharge = new ItemStack(CFTPItems.WATER_CHARGE, count);
                ItemStack usingItem = context.getStack();

                { // Leveled Cauldron Variant
                    LeveledCauldronBlock.decrementFluidLevel(state, world, position);
                    usingItem.decrement(1);
                    cauldronProcess(serverWorld, position, waterCharge);
                    createCombustionParticles(serverWorld, position);
                    createCombustionSound(serverWorld, position);
                }

                return ActionResult.SUCCESS;
            } else if (block == Blocks.POWDER_SNOW_CAULDRON) {

                final int count = 1;
                ItemStack waterCharge = new ItemStack(CFTPItems.WATER_CHARGE, count);
                ItemStack usingItem = context.getStack();

                { // Leveled Cauldron Variant
                    LeveledCauldronBlock.decrementFluidLevel(state, world, position);
                    usingItem.decrement(1);
                    cauldronProcess(serverWorld, position, waterCharge);
                    createCombustionParticles(serverWorld, position);
                    createCombustionSnowSound(serverWorld, position);
                }

                return ActionResult.SUCCESS;
            } else if (block == Blocks.LAVA_CAULDRON) {

                final int count = Random.create().nextInt(4) + 6;
                ItemStack fireCharge = new ItemStack(Items.FIRE_CHARGE, count);
                ItemStack usingItem = context.getStack();

                { // Lava Cauldron Variant
                    BlockState blockState = Blocks.CAULDRON.getDefaultState();
                    world.setBlockState(position, blockState);

                    usingItem.decrement(1);
                    cauldronProcess(serverWorld, position, fireCharge);
                    createCombustionParticles(serverWorld, position);
                    createCombustionSound(serverWorld, position);
                }

                return ActionResult.SUCCESS;
            }

        }

        return ActionResult.PASS;
    }

}
