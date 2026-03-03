package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperGiantEntityRenderer extends CreeperElementalEntityRenderer<CreeperGiantEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_giant.png";

    public CreeperGiantEntityRenderer(EntityRendererFactory.Context context) {
        super(context, 0.7f);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

    @Override
    protected void scale(CreeperEntityRenderState state, MatrixStack matrices) {
        matrices.scale(1.4f, 1.2f, 1.4f);
        super.scale(state, matrices);
    }

}
