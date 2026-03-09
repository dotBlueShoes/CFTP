package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.util.Identifier;

public class CreeperHerobrineEntityRenderer extends CreeperElementalEntityRenderer<CreeperHerobrineEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_herobrine.png";

    public CreeperHerobrineEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

}
