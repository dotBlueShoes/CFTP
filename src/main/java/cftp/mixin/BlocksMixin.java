package cftp.mixin;

import cftp.CFTP;
import cftp.blocks.ObsidianBlock;
import cftp.registries.CFTPBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(Blocks.class)
public class BlocksMixin {

    //@Redirect(
    //        method = "<clinit>",
    //        at = @At(
    //                value = "NEW",
    //                target = "net/minecraft/block/Block"
    //        )
    //)
    //private static Block redirectBlock(AbstractBlock.Settings settings) {
    //    return new Block(settings);
    //}

    ///@Redirect(
    ///        method = "<clinit>",
    ///        at = @At(
    ///                value = "INVOKE",
    ///                target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;",
    ///                ordinal = 47
    ///        )
    ///)
    ///private static Block redirectObsidian(String id, AbstractBlock.Settings settings) {
    ///    CFTP.LOGGER.info(id);
    ///    return CFTPBlocks.overrideBlock(ObsidianBlock::new, id, settings);
    ///}

}

