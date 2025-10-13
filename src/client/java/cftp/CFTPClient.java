package cftp;

import cftp.registries.CFTPBlocksClient;
import cftp.registries.CFTPEntitiesClient;
import cftp.registries.CFTPBlocks;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.util.Map;
import java.util.Set;

public class CFTPClient implements ClientModInitializer {

    private static final Identifier NOISE_LAYER_ID  = Identifier.of("cftp", "noise_overlay");
    private static final Identifier NOISE_TEXTURE   = Identifier.of("cftp", "textures/noise.png");

    public static PostEffectProcessor noiseProcessor;
    public static ShaderProgram shader;

	@Override
	public void onInitializeClient() {
		CFTP.LOGGER.info("Hello Fabric Client!");

		CFTPEntitiesClient.register();
        CFTPBlocksClient.register();

        //ShaderProgram

        // THIS does not WORK!
        // 1. Either I need to create a custom ShaderLoader somehow
        // 2. Or make minecraft shader loader to see my "cftp/shaders/post/noise"
        // 3. Maybe all I need is to use minecraft and not cftp folder
        //MinecraftClient.getInstance().getShaderLoader().loadPostEffect(
        //        Identifier.of("cftp", "shaders/post/noise"),
        //        DefaultFramebufferSet.MAIN_ONLY
        //);

        //mc.onInitFinished()
        //mc.reloadResources().thenRun(() -> {
        //    // Safe to call now
        //    try {
        //        PostEffectProcessor noiseProcessor = mc.getShaderLoader().loadPostEffect(
        //                Identifier.of("cftp", "shaders/post/noise.json"),
        //                DefaultFramebufferSet.MAIN_ONLY
        //        );
        //        CFTP.LOGGER.info("call");
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        //});

        //ShaderLoader loader = new ShaderLoader(mc.getTextureManager(), Throwable::printStackTrace);

        //PostEffectProcessor postEffectProcessor = mc.getShaderLoader().loadPostEffect(
        //        Identifier.of("cftp", "shaders/post/noise.json"),
        //        DefaultFramebufferSet.MAIN_ONLY
        //);

        //if (postEffectProcessor != null) {
        //    postEffectProcessor.setUniforms("u_time", 1.0f);
        //    //postEffectProcessor.render(this.client.getFramebuffer(), this.pool);
        //}

        //ShaderLoader.Definitions definitions = new ShaderLoader.Definitions(
        //    Map.of(Identifier.of("cftp", "shaders/post/noise.json"), myPostEffectPipeline) // post chains
        //);
        //ShaderLoader loader = new ShaderLoader(mc.getTextureManager(), Throwable::printStackTrace);
        //loader.apply(defs, mc.getResourceManager(), mc.getProfiler());

        //noiseProcessor = loader.loadPostEffect(
        //        Identifier.of("cftp", "shaders/post/noise.json"),
        //        Set.of(Identifier.of("minecraft", "main"))
        //);

        //ClientResourceReloadEvents.END.register((resourceManager, prepareProfiler, applyProfiler, success) -> {

        //ResourceManagerReloadListener

        //ClientResourceReloadEvents.END
        //ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
        //
        //});

        //ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
        //
        //    noiseProcessor = mc.getShaderLoader().loadPostEffect(
        //            Identifier.of("cftp", "shaders/post/noise.json"),
        //            DefaultFramebufferSet.MAIN_ONLY
        //    );
        //
        //    //if (client.getShaderLoader() != null && noiseProcessor == null) {
        //    //    try {
        //    //        noiseProcessor = client.getShaderLoader().loadPostEffect(
        //    //                new Identifier("cftp", "shaders/post/noise.json"),
        //    //                Set.of(new Identifier("minecraft", "main"))
        //    //        );
        //    //    } catch (Exception e) {
        //    //        e.printStackTrace();
        //    //    }
        //    //}
        //
        //    CFTP.LOGGER.info("call");
        //});

        //mc.execute(() -> {
        //    try {
        //        // Load the post effect processor
        //        noiseProcessor = loader.loadPostEffect(
        //                Identifier.of("cftp", "shaders/post/noise.json"),
        //                Set.of(Identifier.of("minecraft", "main")) // available targets
        //        );
        //        CFTP.LOGGER.info("all right!");
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        //});

        //client.execute(() -> {
        //    try {
        //        // Load the shader program
        //        noiseShader = ShaderLoader.(
        //                new Identifier("mymod", "shaders/post/noise.json")
        //        );
//
        //        // Resize the shader to match the window size
        //        noiseShader.resize(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        //});

        //mc.execute(() -> {
        //    try {
        //        noiseShader = new ShaderEffect(
        //                mc.getResourceManager(),
        //                mc.getFramebuffer(),
        //                Identifier.of("mymod", "shaders/post/noise.json")
        //        );
        //        noiseShader.resize(mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
        //    } catch (IOException e) {
        //        e.printStackTrace();
        //    }
        //});

        // attach AFTER the misc overlays (vignette, spyglass, powder snow)

        HudLayerRegistrationCallback.EVENT.register(layered -> {
            layered.attachLayerAfter(
                    IdentifiedLayer.MISC_OVERLAYS,   // anchor (vanilla layer id)
                    NOISE_LAYER_ID,                  // id for your new layer
                    this::renderNoiseOverlay
            );
        });



        //HudLayerRegistrationCallback.EVENT.register(layers -> {
        //    //layers.addLayer(layerId);
        //    layers.addLayer(
        //            IdentifiedLayer.(
        //                    IdentifiedLayer.MISC_OVERLAYS,  // anchor point
        //                    NOISE_LAYER_ID,
        //                    (context, tickCounter) -> renderNoiseOverlay(context, tickCounter)
        //            )
        //    );
        //});

        //layers.addLayer(Identifier.of("cftp", "noise_overlay"), (drawContext, tickCounter) -> {
        //    //renderNoiseOverlay(drawContext);
        //});

        //new IdentifiedLayer(
        //        IdentifiedLayer.MISC_OVERLAYS,
        //        (drawContext, renderTickCounter) -> renderNoiseOverlay(drawContext),
        //        HudRenderLayer.AFTER_ALL // control ordering
        //)

        //HudElementRegistry.addLast(
        //        Identifier.of("cftp", "noise_overlay"),
        //        (drawContext, tickCounter) -> {
        //            //renderNoiseOverlay(drawContext, tickCounter);
        //        }
        //);

	}

    private void renderNoiseOverlay(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        int w = mc.getWindow().getScaledWidth();
        int h = mc.getWindow().getScaledHeight();

        //CFTPClient.noiseProcessor = MinecraftClient.getInstance().getShaderLoader().loadPostEffect(
        //        Identifier.of("minecraft", "shaders/post/noise"),
        //        DefaultFramebufferSet.MAIN_ONLY
        //);

        //ShaderProgram shader = ShaderLoader.of(
        //        MinecraftClient.getInstance().getProgramManager(),
        //        Identifier.of("cftp", "shaders/program/quad")
        //).getProgram();

        //{
        //    CompiledShader vertex = CompiledShader.compile(
        //            Identifier.of("cftp","shaders/program/quad.vsh"),
        //            CompiledShader.Type.VERTEX,
        //            readString("/assets/cftp/shaders/program/quad.vsh") // implement readString() to load file
        //    );
        //
        //    CompiledShader fragment = CompiledShader.compile(
        //            Identifier.of("cftp","shaders/program/quad.fsh"),
        //            CompiledShader.Type.FRAGMENT,
        //            readString("/assets/cftp/shaders/program/quad.fsh")
        //    );
        //
        //    shader = ShaderProgram.create(vertex, fragment, VertexFormats.POSITION_TEXTURE);
        //}

        //{
        //    shader.bind();
        //
        //    // bind your texture
        //    RenderSystem.setShaderTexture(0, textureId);
        //
        //    // draw quad
        //    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        //    buffer.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        //    buffer.vertex(x, y + height, 0).texture(0, 1).next();
        //    buffer.vertex(x + width, y + height, 0).texture(1, 1).next();
        //    buffer.vertex(x + width, y, 0).texture(1, 0).next();
        //    buffer.vertex(x, y, 0).texture(0, 0).next();
        //    Tessellator.getInstance().draw();
        //
        //    shader.unbind();
        //
        //    ctx.drawTexture(RenderLayer::getGuiTextured, NOISE_TEXTURE, 0, 0, 0, 0, w, h, w, h); // adjust tex sizes if necessary
        //
        //    // RenderLayer renderLayer = (RenderLayer)renderLayers.apply(sprite);
        //    // Matrix4f matrix4f = this.matrices.peek().getPositionMatrix();
        //    // VertexConsumer vertexConsumer = this.vertexConsumers.getBuffer(renderLayer);
        //    // vertexConsumer.vertex(matrix4f, (float)x1, (float)y1, 0.0F).texture(u1, v1).color(color);
        //    // vertexConsumer.vertex(matrix4f, (float)x1, (float)y2, 0.0F).texture(u1, v2).color(color);
        //    // vertexConsumer.vertex(matrix4f, (float)x2, (float)y2, 0.0F).texture(u2, v2).color(color);
        //    // vertexConsumer.vertex(matrix4f, (float)x2, (float)y1, 0.0F).texture(u2, v1).color(color);
        //}

        //if (noiseProcessor != null) {
        //    noiseProcessor.setUniforms("u_time", (float) (System.currentTimeMillis() % 10000L) / 1000f);
        //
        //    // Use RenderSystem.enableBlend() / disableBlend() for transparency.
        //    //
        //    // If your texture is animated, change texture coordinates or update a dynamic NativeImageBackedTexture — but be mindful of performance.
        //
        //    //Framebuffer overlayFb = new Framebuffer(w, h, /*useDepth=*/ false, /*getError=*/ false);
        //    //overlayFb.clear(true);
        //    //overlayFb.beginWrite(true); // bind FBO & set viewport
        //
        //    RenderSystem.enableBlend();
        //
        //    FrameGraphBuilder builder = new FrameGraphBuilder();
        //    PostEffectProcessor.FramebufferSet set = PostEffectProcessor.FramebufferSet.singleton(
        //            PostEffectProcessor.MAIN, builder.createObjectNode("main", mc.getFramebuffer())
        //    );
        //
        //    noiseProcessor.render(builder, w, h, set);
        //    //ctx.drawTexture(RenderLayer::getGuiTextured, NOISE_TEXTURE, 0, 0, 0, 0, w, h, w, h); // adjust tex sizes if necessary
        //
        //    RenderSystem.disableBlend();
        //}
    }

}