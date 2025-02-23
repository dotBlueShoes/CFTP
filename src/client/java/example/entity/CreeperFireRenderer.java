package example.entity;

import example.ExampleMod;
import example.entity.base.CreeperElementalRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.util.Identifier;

public class CreeperFireRenderer extends CreeperElementalRenderer<CreeperFireEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_fire.png";

    public CreeperFireRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(ExampleMod.MOD_ID, TEXTURE_PATH);
    }

}
