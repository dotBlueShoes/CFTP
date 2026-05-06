package cftp.entity.creeper;

import cftp.CFTP;
import cftp.CFTPClient;
import cftp.entity.base.CreeperElementalEntityModel;
import cftp.entity.base.CreeperElementalEntityRenderState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class CreeperGhostRenderer extends MobEntityRenderer<CreeperGhostEntity, CreeperElementalEntityRenderState, CreeperElementalEntityModel> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_ghost.png";

    public CreeperGhostRenderer(EntityRendererFactory.Context context) {
        super(context, new CreeperElementalEntityModel(context.getPart(CFTPClient.GHOST_CREEPER)), 0.0F);
        //todo this.addFeature(new CreeperChargeFeatureRenderer(this, context.getEntityModels()));
    }

    @Override
    public Identifier getTexture(CreeperElementalEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

    @Override
    @Nullable
    protected RenderLayer getRenderLayer(CreeperElementalEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderLayer.getEntityTranslucent(this.getTexture(state));
    }

    @Override
    public void render(CreeperElementalEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntityRenderState.invisibility <= 0) {
            super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
        }
    }

    @Override
    public CreeperElementalEntityRenderState createRenderState() {
        return new CreeperElementalEntityRenderState();
    }

    @Override
    protected void scale(CreeperElementalEntityRenderState creeperEntityRenderState, MatrixStack matrixStack) {
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
    protected float getAnimationCounter(CreeperElementalEntityRenderState creeperEntityRenderState) {
        float time = creeperEntityRenderState.fuseTime;
        return (int)(time * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(time, 0.5F, 1.0F);
    }

    @Override
    public void updateRenderState(CreeperGhostEntity creeperEntity, CreeperElementalEntityRenderState creeperEntityRenderState, float f) {
        super.updateRenderState(creeperEntity, creeperEntityRenderState, f);
        creeperEntityRenderState.fuseTime = creeperEntity.getClientFuseTime(f);
        creeperEntityRenderState.charged = creeperEntity.isCharged();
        creeperEntityRenderState.invisibility = creeperEntity.getInvisibleAge();
    }

}
