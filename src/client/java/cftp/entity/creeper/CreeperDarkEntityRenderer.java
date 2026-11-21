package cftp.entity.creeper;

import cftp.CFTP;
import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntityRenderer;
import cftp.entity.creeper.feature.ElementalCreeperGlowingFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperDarkEntityRenderer extends CreeperElementalEntityRenderer<CreeperDarkEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper/dark.png";

    public CreeperDarkEntityRenderer(EntityRendererFactory.Context context) {
        super(context);

        if (CFTPData.creeperDark.hasGlowingTexture) {
            this.addFeature(new ElementalCreeperGlowingFeatureRenderer(
                    this,
                    context.getEntityModels(),
                    Identifier.of(CFTP.MOD_ID, "textures/entity/creeper/ilum/dark.png")
            ));
        }
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
