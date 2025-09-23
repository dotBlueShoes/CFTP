package cftp.entity.spider;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpiderBlueRenderer extends SpiderEntityRenderer<SpiderBlueEntity> {

    private static final Identifier TEXTURE = Identifier.of("cftp", "textures/entity/spider_blue.png");
    private static final float SCALE = 1.25f;

    public SpiderBlueRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.SPIDER);
    }

    public SpiderBlueRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
        super(ctx, layer);
    }

    @Override
    protected float getShadowRadius(LivingEntityRenderState livingEntityRenderState) {
        return SCALE * super.getShadowRadius(livingEntityRenderState);
    }

    @Override
    protected void scale(LivingEntityRenderState state, MatrixStack matrices) {
        matrices.scale(SCALE, SCALE, SCALE);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }

}
