package cftp.item.items;

import cftp.CFTP;
import cftp.item.CFTPItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ElementalPowderItem extends Item {

    public ElementalPowderItem(Settings settings) {
        super(settings);
    }

    public static void useCauldron(World world, BlockPos position, BlockState state, ItemStack useItem, ItemStack getItem) {
        LeveledCauldronBlock.decrementFluidLevel(state, world, position);

        world.playSound(
                null, position,
                SoundEvents.BLOCK_BREWING_STAND_BREW,
                SoundCategory.PLAYERS,
                1.0F, 1.0F
        );

        useItem.decrement(1);

        { // See if block below is hopper.
            BlockPos maybeHopperPosition = new BlockPos(position.getX(), position.getY() - 1, position.getZ());
            BlockEntity blockEntity = world.getBlockEntity(maybeHopperPosition);

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
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos position = context.getBlockPos();
        World world = context.getWorld();

        BlockState state = world.getBlockState(position);
        Block block = state.getBlock();

        if (world instanceof ServerWorld serverWorld) {

            if (block == Blocks.WATER_CAULDRON) {
                ItemStack waterCharge = new ItemStack(CFTPItems.WATER_CHARGE, 1);
                ItemStack usingItem = context.getStack();

                useCauldron(serverWorld, position, state, usingItem, waterCharge);

                return ActionResult.SUCCESS;
            }

            // ISSUE. 'LAVA_CAULDRON' is not 'LeveledCauldronBlock'!
            ///else if (block == Blocks.LAVA_CAULDRON) {
            ///    ItemStack waterCharge = new ItemStack(Items.FIRE_CHARGE, 1);
            ///    ItemStack usingItem = context.getStack();
            ///
            ///    useCauldron(serverWorld, position, state, usingItem, waterCharge);
            ///
            ///    return ActionResult.SUCCESS;
            ///}

        }

        return ActionResult.PASS;
    }

}
