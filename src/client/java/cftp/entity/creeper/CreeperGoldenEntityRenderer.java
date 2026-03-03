package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntityRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperGoldenEntityRenderer extends CreeperElementalEntityRenderer<CreeperGoldenEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_golden.png";

    public CreeperGoldenEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

    @Override
    public void render(CreeperEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, 0xF000F0);
    }
}
