package cftp.entity.base;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.util.math.MathHelper;

public class CreeperSpectreEntityModel extends EntityModel<CreeperEntityRenderState> {

    private final ModelPart head;
    private static final int HEAD_AND_BODY_Y_PIVOT = 6;

    public CreeperSpectreEntityModel(ModelPart modelPart) {
        super(modelPart);
        this.head = modelPart.getChild(EntityModelPartNames.HEAD);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                EntityModelPartNames.HEAD,
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-4.0F, -8.001F, -4.0F, 8.0F, 8.0F, 8.0F, Dilation.NONE),
                ModelTransform.pivot(0.0F, 6.0F, 0.0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create()
                        .uv(16, 16)
                        .cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, Dilation.NONE),
                ModelTransform.pivot(0.0F, 6.0F, 0.0F)
        );
        //ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 16).cuboid(
        //        -2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, Dilation.NONE
        //);
        return TexturedModelData.of(modelData, 64, 32);
    }

    public void setAngles(CreeperEntityRenderState creeperEntityRenderState) {
        super.setAngles(creeperEntityRenderState);
        this.head.yaw = creeperEntityRenderState.yawDegrees * (float) (Math.PI / 180.0);
        this.head.pitch = creeperEntityRenderState.pitch * (float) (Math.PI / 180.0);
        //float f = creeperEntityRenderState.limbAmplitudeMultiplier;
        //float g = creeperEntityRenderState.limbFrequency;
    }

}
