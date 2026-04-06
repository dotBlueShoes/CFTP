package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntityRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperLavaEntityRenderer extends CreeperElementalEntityRenderer<CreeperLavaEntity> {

    //private static final String TEXTURE_PATH = "textures/entity/creeper_lava.png";
    private static final String TEXTURE_PATH_2 = "textures/entity/creeper_lava_2.png";
    private static final String TEXTURE_PATH_1 = "textures/entity/creeper_lava_1.png";
    private static final String TEXTURE_PATH_0 = "textures/entity/creeper_lava_0.png";

    private static final String[] TEXTURE_PATHS = {
            TEXTURE_PATH_0,
            TEXTURE_PATH_1,
            TEXTURE_PATH_2,
            TEXTURE_PATH_1,
    };

    private final float COUNTER_MAX = 4.0f;
    private float counter = 0;
    private final int SELECTOR_MAX = 4;
    private int selector = 0;

    @Override
    public Identifier getTexture(CreeperEntityRenderState state) {
        // this seems to be sometimes faster sometimes slower...
        float deltaTime = MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true);
        counter += deltaTime;

        if (counter >= COUNTER_MAX) {
            counter = 0.0f;
            ++selector;
            if (selector >= SELECTOR_MAX) {
                selector = 0;
            }
        }

        return Identifier.of(CFTP.MOD_ID, TEXTURE_PATHS[selector]);
    }

    public CreeperLavaEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void scale(CreeperEntityRenderState state, MatrixStack matrices) {
        matrices.scale(1.05f, 1.05f, 1.05f);
        super.scale(state, matrices);
    }

}
