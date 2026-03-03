package cftp.entity.base;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.CreeperChargeFeatureRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public abstract class CreeperElementalEntityRenderer<T extends CreeperElementalEntity> extends MobEntityRenderer<T, CreeperEntityRenderState, CreeperEntityModel> {

    public CreeperElementalEntityRenderer(EntityRendererFactory.Context context, float shadowRadius) {
        super(context, new CreeperEntityModel(context.getPart(EntityModelLayers.CREEPER)), shadowRadius);
        this.addFeature(new CreeperChargeFeatureRenderer(this, context.getEntityModels()));
    }

    public CreeperElementalEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CreeperEntityModel(context.getPart(EntityModelLayers.CREEPER)), 0.5f);
        this.addFeature(new CreeperChargeFeatureRenderer(this, context.getEntityModels()));
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return null;
    }

    @Override
    public CreeperEntityRenderState createRenderState() {
        return new CreeperEntityRenderState();
    }

    @Override
    protected void scale(CreeperEntityRenderState creeperEntityRenderState, MatrixStack matrixStack) {
        float time = creeperEntityRenderState.fuseTime;
        float g = 1.0F + MathHelper.sin(time * 100.0F) * time * 0.01F;

        time = MathHelper.clamp(time, 0.0F, 1.0F);
        time *= time;
        time *= time;

        float h = (1.0F + time * 0.4F) * g;
        float i = (1.0F + time * 0.1F) / g;

        matrixStack.scale(h, i, h);
    }

    @Override
    protected float getAnimationCounter(CreeperEntityRenderState creeperEntityRenderState) {
        float time = creeperEntityRenderState.fuseTime;
        return (int)(time * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(time, 0.5F, 1.0F);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateRenderState(CreeperElementalEntity creeperEntity, CreeperEntityRenderState creeperEntityRenderState, float tickDelta) {
        super.updateRenderState((T) creeperEntity, creeperEntityRenderState, tickDelta);
        creeperEntityRenderState.fuseTime = creeperEntity.getClientFuseTime(tickDelta);
        creeperEntityRenderState.charged = creeperEntity.isCharged();
    }

}
