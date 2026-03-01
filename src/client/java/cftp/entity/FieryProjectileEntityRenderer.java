package cftp.entity;

import cftp.registries.CFTPBlocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TntMinecartEntityRenderer;
import net.minecraft.client.render.entity.state.TntEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class FieryProjectileEntityRenderer extends EntityRenderer<FieryBlockEntity, TntEntityRenderState> {

    private final BlockRenderManager blockRenderManager;

    public FieryProjectileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.blockRenderManager = context.getBlockRenderManager();
    }

    public void render(
            TntEntityRenderState tntEntityRenderState,
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int i
    ) {
        matrixStack.push();
        matrixStack.translate(0.0F, 0.5F, 0.0F);

        final float scale = 0.7f;
        matrixStack.scale(scale, scale, scale);

        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
        matrixStack.translate(-0.5F, -0.5F, 0.5F);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));

        TntMinecartEntityRenderer.renderFlashingBlock(
                this.blockRenderManager,
                CFTPBlocks.FIERY_BLOCK.getDefaultState(),
                matrixStack,
                vertexConsumerProvider, i,
                false
        );

        matrixStack.pop();
        super.render(tntEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    public TntEntityRenderState createRenderState() {
        return new TntEntityRenderState();
    }

    @Override
    public void updateRenderState(FieryBlockEntity entity, TntEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.fuse = (float)entity.getFuse() - tickDelta + 1.0F;
        state.blockState = entity.getBlockState();
    }


}
