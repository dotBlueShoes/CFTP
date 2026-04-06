package cftp.entity.creeper;

import cftp.CFTP;
import cftp.entity.base.CreeperElementalEntityRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperFireEntityRenderer extends CreeperElementalEntityRenderer<CreeperFireEntity> {

    private static final String TEXTURE_PATH_2 = "textures/entity/creeper_fire_2.png";
    private static final String TEXTURE_PATH_1 = "textures/entity/creeper_fire_1.png";
    private static final String TEXTURE_PATH_0 = "textures/entity/creeper_fire_0.png";

    private static final String[] TEXTURE_PATHS = {
            TEXTURE_PATH_0,
            TEXTURE_PATH_1,
            TEXTURE_PATH_2,
    };

    private final float COUNTER_MAX = 10.0f;
    private float counter = 0;
    private final int SELECTOR_MAX = 3;
    private int selector = 0;

    public CreeperFireEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

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

}