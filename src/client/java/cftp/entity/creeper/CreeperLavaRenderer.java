package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperLavaRenderer extends CreeperElementalRenderer<CreeperLavaEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_lava.png";

    public CreeperLavaRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

    @Override
    protected void scale(CreeperEntityRenderState state, MatrixStack matrices) {
        matrices.scale(1.05f, 1.05f, 1.05f);
        super.scale(state, matrices);
    }

}
