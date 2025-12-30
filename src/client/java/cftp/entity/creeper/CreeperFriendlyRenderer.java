package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperFriendlyRenderer extends CreeperElementalRenderer<CreeperFriendlyEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_friendly.png";

    public CreeperFriendlyRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

    @Override
    protected void scale(CreeperEntityRenderState state, MatrixStack matrices) {
        matrices.scale(0.90f, 0.90f, 0.90f);
        super.scale(state, matrices);
    }

    @Override
    public void render(CreeperEntityRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        // ... nope
        //int i = this.blockColors.getColor(state, null, null, 0);
        //float f = (i >> 16 & 0xFF) / 255.0F;
        //float g = (i >> 8 & 0xFF) / 255.0F;
        //float h = (i & 0xFF) / 255.0F;

        super.render(state, matrixStack, vertexConsumerProvider, i);
    }
}
