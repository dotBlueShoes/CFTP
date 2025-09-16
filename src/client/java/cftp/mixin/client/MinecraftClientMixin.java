package cftp.mixin.client;

import cftp.CFTP;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    /// **
    // * @author dotBlueShoes
    // * @reason no idea
    // */
    //@Overwrite
    //private float getTargetMillisPerTick(float millis) {
    //    return 0;
    //}

    ///**
    // * @author dotBlueShoes
    // * @reason no idea
    // */
    //
    //@Inject(method = "reloadResourcesConcurrently()Ljava/util/concurrent/CompletableFuture;", at = @At("RETURN"))
    //public void reloadResourcesConcurrently(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
    //    CFTP.LOGGER.info("OMG1");
    //}

    //public CompletableFuture<Void> reloadResources() {
    //    return this.reloadResources(false, (MinecraftClient.LoadingContext)null);
    //}
    //
    //private CompletableFuture<Void> reloadResources(boolean force, @Nullable LoadingContext loadingContext) {
    //
    //}
    //

    //@Inject(method = "reloadResources(ZLnet/minecraft/resource/LoadingContext;)Ljava/util/concurrent/CompletableFuture;", at = @At("RETURN"))
    //private void onReloadResources(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
    //    CFTP.LOGGER.info("OMG2");
    //}

    //accessible method net/minecraft/client/MinecraftClient onInitFinished (Lnet/minecraft/client/MinecraftClient$LoadingContext;)Ljava/lang/Runnable;

    //@Inject(
    //        method = "onInitFinished",
    //        at = @At("RETURN")
    //)
    //private void afterOnInitFinished(CallbackInfoReturnable<Runnable> cir) {
    //    CFTP.LOGGER.info("OMG-A");
    //    MinecraftClient.getInstance().getShaderLoader().loadPostEffect(
    //            Identifier.of("minecraft", "shaders/post/noise.json"),
    //            DefaultFramebufferSet.MAIN_ONLY
    //    );
    //    CFTP.LOGGER.info("OMG-B");
    //}

    //@Inject(method = "onInitFinished()Ljava/util/concurrent/CompletableFuture;", at = @At("RETURN"))
    //private Runnable onInitFinished(@Nullable MinecraftClient.LoadingContext loadingContext)
//
    //@Inject(method = "reloadResources()Ljava/util/concurrent/CompletableFuture;", at = @At("RETURN"))
    //public void reloadResources(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
    //    CFTP.LOGGER.info("OMG2");
    //    //return this.reloadResources(false, (MinecraftClient.LoadingContext)null);
    //}

}
