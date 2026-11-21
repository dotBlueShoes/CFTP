package cftp.entity.creeper;

import cftp.CFTP;
import cftp.config.CFTPData;
import cftp.entity.base.CreeperElementalEntityRenderer;
import cftp.entity.creeper.feature.ElementalCreeperGlowingFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperCookieEntityRenderer extends CreeperElementalEntityRenderer<CreeperCookieEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper/cookie.png";

    public CreeperCookieEntityRenderer(EntityRendererFactory.Context context) {
        super(context);

        if (CFTPData.creeperCookie.hasGlowingTexture) {
            this.addFeature(new ElementalCreeperGlowingFeatureRenderer(
                    this,
                    context.getEntityModels(),
                    Identifier.of(CFTP.MOD_ID, "textures/entity/creeper/ilum/halloween.png")
            ));
        }
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

    @Override
    protected void scale(CreeperEntityRenderState state, MatrixStack matrices) {
        matrices.scale(0.96f, 0.96f, 0.96f);
        super.scale(state, matrices);
    }
}
