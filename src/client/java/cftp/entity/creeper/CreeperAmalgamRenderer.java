package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.util.Identifier;

public class CreeperAmalgamRenderer extends CreeperElementalRenderer<CreeperAmalgamEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_amalgam.png";

    public CreeperAmalgamRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

}
