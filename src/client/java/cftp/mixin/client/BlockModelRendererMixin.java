package cftp.mixin.client;

import cftp.CFTP;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BlockModelRenderer.class)
abstract class BlockModelRendererMixin {
    //@Unique
    //private static final ThreadLocal<BlockRenderContext> CONTEXTS = ThreadLocal.withInitial(BlockRenderContext::new);
//
    //@Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLnet/minecraft/util/math/random/Random;JI)V", cancellable = true)
    //private void hookRender(BlockRenderView blockView, BakedModel model, BlockState state, BlockPos pos, MatrixStack matrices, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, CallbackInfo ci) {
    //    if (!model.isVanillaAdapter()) {
    //        CONTEXTS.get().render(blockView, model, state, pos, matrices, vertexConsumer, cull, random, seed, overlay);
    //        ci.cancel();
    //    }
    //}

    ///**
    // * @author dotBlueShoes
    // * @reason no reason at all.
    // */
    //@Overwrite
    //protected void renderQuad(BlockRenderView world, BlockState state, BlockPos pos, VertexConsumer vertexConsumer, MatrixStack.Entry matrixEntry, BakedQuad quad, float brightness0, float brightness1, float brightness2, float brightness3, int light0, int light1, int light2, int light3, int overlay) {
//
    //    CFTP.LOGGER.info("here!");
    //    float r = 255.0f / 255.0f;
    //    float g = 0.0f / 255.0f;
    //    float b = 0.0f / 255.0f;
//
    //    //if (quad.hasTint()) {
    //    //    int i = this.colors.getColor(state, world, pos, quad.getTintIndex());
    //    //    f = (float)(i >> 16 & 255) / 255.0F;
    //    //    g = (float)(i >> 8 & 255) / 255.0F;
    //    //    h = (float)(i & 255) / 255.0F;
    //    //} else {
    //    //    f = 1.0F;
    //    //    g = 1.0F;
    //    //    h = 1.0F;
    //    //}
//
    //    vertexConsumer.quad(matrixEntry, quad, new float[]{brightness0, brightness1, brightness2, brightness3}, r, g, b, 1.0F, new int[]{light0, light1, light2, light3}, overlay, true);
    //}

    //@Inject(
    //        method = "renderQuad",
    //        at = @At("HEAD")
    //)
    //protected void renderQuad(
    //        BlockRenderView world, BlockState state, BlockPos pos,
    //        VertexConsumer vertexConsumer, MatrixStack.Entry matrixEntry,
    //        BakedQuad quad,
    //        float brightness0, float brightness1, float brightness2, float brightness3,
    //        int light0, int light1, int light2, int light3,
    //        int overlay,
    //        CallbackInfo ci
    //) {
    //    CFTP.LOGGER.info("here!");
    //}

    ///**
    // * @author dotBlueShoes
    // * @reason no reason at all.
    // */
    //@Overwrite
    //private void renderQuad(
    //        BlockRenderView world, BlockState state, BlockPos pos,
    //        VertexConsumer vertexConsumer, MatrixStack.Entry matrixEntry,
    //        BakedQuad quad,
    //        float brightness0, float brightness1, float brightness2, float brightness3,
    //        int light0, int light1, int light2, int light3,
    //        int overlay
    //) {
    //    CFTP.LOGGER.info("renderQuad called!");
//
    //    // constant red tint
    //    vertexConsumer.quad(matrixEntry, quad,
    //            new float[]{brightness0, brightness1, brightness2, brightness3},
    //            1.0f, 0.0f, 0.0f, 1.0f,
    //            new int[]{light0, light1, light2, light3},
    //            overlay,
    //            true);
    //}
//
    ///**
    // * @author dotBlueShoes
    // * @reason no reason at all.
    // */
    //@Overwrite
    //private static void renderQuads(
    //        MatrixStack.Entry entry, VertexConsumer vertexConsumer,
    //        float red, float green, float blue,
    //        List<BakedQuad> quads,
    //        int light, int overlay
    //) {
    //    CFTP.LOGGER.info("renderQuads called!");
    //    for(BakedQuad bakedQuad : quads) {
    //        float r = 1.0f;
    //        float g = 0.0f;
    //        float b = 0.0f;
//
    //        vertexConsumer.quad(entry, bakedQuad, r, g, b, 1.0F, light, overlay);
    //    }
    //}

}
