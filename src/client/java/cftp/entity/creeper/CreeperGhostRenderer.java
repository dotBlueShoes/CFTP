package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class CreeperGhostRenderer extends CreeperElementalRenderer<CreeperGhostEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_ghost.png";

    public CreeperGhostRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

    @Override
    @Nullable
    protected RenderLayer getRenderLayer(CreeperEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderLayer.getEntityTranslucent(this.getTexture(state));
    }

}
