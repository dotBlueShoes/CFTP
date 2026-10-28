package cftp.entity.john;

import cftp.CFTP;
import cftp.entity.JohnEntity;
import cftp.registries.CFTPModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class JohnEntityRenderer extends MobEntityRenderer<JohnEntity, JohnEntityRenderState, JohnEntityModel> {

    private static final Identifier TEXTURE = Identifier.of(CFTP.MOD_ID, "textures/entity/john/john.png");
    private static final float SHADOW_SIZE = 0.25f;

    public JohnEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new JohnEntityModel(context.getPart(CFTPModelLayers.JOHN)), SHADOW_SIZE);
    }

    @Override
    protected void scale(JohnEntityRenderState state, MatrixStack matrices) {
        matrices.scale(0.75f, 0.75f, 0.75f);
        super.scale(state, matrices);
    }

    @Override
    public Identifier getTexture(JohnEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void updateRenderState(JohnEntity entity, JohnEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.handSwingProgress = entity.getHandSwingProgress(tickDelta);
        //creeperEntityRenderState.invisibility = creeperEntity.getInvisibleAge();
    }

    @Override
    public JohnEntityRenderState createRenderState() {
        return new JohnEntityRenderState();
    }

}
