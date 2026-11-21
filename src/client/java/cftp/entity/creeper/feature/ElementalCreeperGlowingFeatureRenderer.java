package cftp.entity.creeper.feature;

import cftp.CFTP;
import cftp.config.CFTPData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ElementalCreeperGlowingFeatureRenderer extends FeatureRenderer<CreeperEntityRenderState, CreeperEntityModel> {
    private final Identifier texture;
    private final CreeperEntityModel model;

    public ElementalCreeperGlowingFeatureRenderer(FeatureRendererContext<CreeperEntityRenderState, CreeperEntityModel> context, LoadedEntityModels loader, Identifier texture) {
        super(context);
        this.texture = texture;
        this.model = new CreeperEntityModel(loader.getModelPart(EntityModelLayers.CREEPER));
    }

    protected boolean shouldRender() {
        return CFTPData.isGlowingCreeperEnabled;
    }

    protected Identifier getGlowingTexture() {
        return texture;
    }

    protected CreeperEntityModel getGlowingModel() {
        return this.model;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CreeperEntityRenderState state, float limbAngle, float limbDistance) {
        if (this.shouldRender()) {
            CreeperEntityModel model = this.getGlowingModel();
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEyes(this.getGlowingTexture()));

            model.setAngles(state);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -8355712);
        }
    }
}
