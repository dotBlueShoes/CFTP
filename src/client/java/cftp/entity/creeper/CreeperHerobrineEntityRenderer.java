package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.creeper.renderstate.CreeperHerobrineEntityRenderState;
import cftp.entity.herobrine.HerobrineEntityModel;
import cftp.registries.CFTPModelLayersClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class CreeperHerobrineEntityRenderer extends MobEntityRenderer<CreeperHerobrineEntity, CreeperHerobrineEntityRenderState, HerobrineEntityModel> {

    private static final String TEXTURE_PATH = "textures/entity/herobrine.png";

    public CreeperHerobrineEntityRenderer(EntityRendererFactory.Context context) {
        // slim ? EntityModelLayers.PLAYER_SLIM : EntityModelLayers.PLAYER), slim
        super(context, new HerobrineEntityModel(context.getPart(CFTPModelLayersClient.HEROBRINE_CREEPER), false), 0.5F);
        //super(context, new HerobrineEntityModel(context.getPart(EntityModelLayers.PLAYER), false), 0.5F);
        //ZombieEntityRenderer extends ZombieBaseEntityRenderer<ZombieEntity, ZombieEntityRenderState, ZombieEntityModel<ZombieEntityRenderState>>
    }

    //public CreeperGhostRenderer(EntityRendererFactory.Context context) {
    //    PlayerEntityRenderer
    //    //super(context, new CreeperElementalEntityModel(context.getPart(CFTPClient.GHOST_CREEPER)), 0.0F);
    //    //todo this.addFeature(new CreeperChargeFeatureRenderer(this, context.getEntityModels()));
    //}

    @Override
    public CreeperHerobrineEntityRenderState createRenderState() {
        return new CreeperHerobrineEntityRenderState();
    }

    //public CreeperHerobrineEntityRenderer(EntityRendererFactory.Context context) {
    //    super(context);
    //}


    @Override
    public Identifier getTexture(CreeperHerobrineEntityRenderState state) {
        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATH);
    }
}
