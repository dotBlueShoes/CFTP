package cftp.entity.spider;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpiderYellowRenderer extends SpiderEntityRenderer<SpiderYellowEntity> {

    private static final Identifier TEXTURE = Identifier.of("cftp", "textures/entity/spider_yellow.png");

    public SpiderYellowRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.SPIDER);
    }

    public SpiderYellowRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
        super(ctx, layer);
    }

    @Override
    protected float getShadowRadius(LivingEntityRenderState livingEntityRenderState) {
        return 0.5f * super.getShadowRadius(livingEntityRenderState);
    }

    @Override
    protected void scale(LivingEntityRenderState state, MatrixStack matrices) {
        matrices.scale(0.5f, 0.5f, 0.5f);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }

}
