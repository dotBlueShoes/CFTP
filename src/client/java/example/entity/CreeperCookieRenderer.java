package example.entity;

import example.ExampleMod;

import example.entity.base.CreeperElementalRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.util.Identifier;

public class CreeperCookieRenderer extends CreeperElementalRenderer<CreeperCookieEntity> {

    private static final String TEXTURE_PATH = "textures/entity/creeper_cookie.png";

    public CreeperCookieRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        return Identifier.of(ExampleMod.MOD_ID, TEXTURE_PATH);
    }

}
