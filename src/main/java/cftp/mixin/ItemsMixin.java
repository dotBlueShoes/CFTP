package cftp.mixin;

import cftp.CFTP;
import cftp.registries.CFTPBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public class ItemsMixin {

    ///@Redirect(
    ///        method = "<clinit>",
    ///        at = @At(
    ///                value = "INVOKE",
    ///                target = "Lnet/minecraft/item/Items;register(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;",
    ///                ordinal = 295
    ///        )
    ///)
    ///private static Item redirectRegister(Block block) {
    ///    CFTP.LOGGER.info(block.getRegistryEntry().registryKey().getValue().toString());
    ///    return CFTPBlocks.overrideBlockItem("obsidian", block);
    ///    // your logic
    ///}

}
