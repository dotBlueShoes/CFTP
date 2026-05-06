package cftp.entity.creeper;

import cftp.CFTP;
import cftp.CFTPClient;
import cftp.entity.base.CreeperElementalEntityRenderState;
import cftp.entity.base.CreeperSpectreEntityModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class CreeperSpectreEntityRenderer extends MobEntityRenderer<CreeperSpectreEntity, CreeperEntityRenderState, CreeperSpectreEntityModel> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_spectre.png";

    public CreeperSpectreEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CreeperSpectreEntityModel(context.getPart(CFTPClient.SPECTRE_CREEPER)), 0.0F);
        //todo this.addFeature(new CreeperChargeFeatureRenderer(this, context.getEntityModels()));
    }

    @Override
    public CreeperEntityRenderState createRenderState() {
        return new CreeperEntityRenderState();
    }

    //@Override
    //public CreeperElementalEntityRenderState createRenderState() {
    //    return new CreeperElementalEntityRenderState();
    //}

    @Override
    protected @Nullable RenderLayer getRenderLayer(CreeperEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderLayer.getEntityTranslucent(this.getTexture(state));
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }

}
