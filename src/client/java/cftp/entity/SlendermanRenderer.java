package cftp.entity;

import cftp.CFTPClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EndermanBlockFeatureRenderer;
import net.minecraft.client.render.entity.feature.EndermanEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.EndermanEntityRenderState;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class SlendermanRenderer extends MobEntityRenderer<SlendermanEntity, EndermanEntityRenderState, EndermanEntityModel<EndermanEntityRenderState>> {

    private static final Identifier TEXTURE = Identifier.of("cftp", "textures/entity/slenderman/slenderman.png");
    private final Random random = Random.create();

    public SlendermanRenderer(EntityRendererFactory.Context context) {
        super(context, new EndermanEntityModel<>(context.getPart(EntityModelLayers.ENDERMAN)), 0.5F);
        //this.addFeature(new EndermanEyesFeatureRenderer(this));
        //this.addFeature(new EndermanBlockFeatureRenderer(this, context.getBlockRenderManager()));
    }

    public Vec3d getPositionOffset(EndermanEntityRenderState endermanEntityRenderState) {
        Vec3d vec3d = super.getPositionOffset(endermanEntityRenderState);
        if (endermanEntityRenderState.angry) {
            double d = 0.02 * endermanEntityRenderState.baseScale;
            return vec3d.add(this.random.nextGaussian() * d, 0.0, this.random.nextGaussian() * d);
        } else {
            return vec3d;
        }
    }

    @Override
    public EndermanEntityRenderState createRenderState() {
        return new EndermanEntityRenderState();
    }

    @Override
    public Identifier getTexture(EndermanEntityRenderState state) {

        //CFTPClient.noiseProcessor = MinecraftClient.getInstance().getShaderLoader().loadPostEffect(
        //        Identifier.of("minecraft", "shaders/post/noise"),
        //        DefaultFramebufferSet.MAIN_ONLY
        //);

        return TEXTURE;
    }

    public void updateRenderState(SlendermanEntity slendermanEntity, EndermanEntityRenderState endermanEntityRenderState, float f) {
        super.updateRenderState(slendermanEntity, endermanEntityRenderState, f);
        BipedEntityRenderer.updateBipedRenderState(slendermanEntity, endermanEntityRenderState, f, this.itemModelResolver);
        //endermanEntityRenderState.angry = slendermanEntity.isAngry();
        //endermanEntityRenderState.carriedBlock = slendermanEntity.getCarriedBlock();
    }

}
