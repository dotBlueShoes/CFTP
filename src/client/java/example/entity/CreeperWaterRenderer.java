package example.entity;

import example.ExampleMod;
import example.entity.base.CreeperElementalRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.util.Identifier;

public class CreeperWaterRenderer extends CreeperElementalRenderer<CreeperWaterEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_water.png";

    public CreeperWaterRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(ExampleMod.MOD_ID, TEXTURE_PATH);
    }

}
