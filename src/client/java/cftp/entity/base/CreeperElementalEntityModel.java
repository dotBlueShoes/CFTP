package cftp.entity.base;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

public class CreeperElementalEntityModel extends EntityModel<CreeperElementalEntityRenderState> {

    private final ModelPart head;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private static final int HEAD_AND_BODY_Y_PIVOT = 6;

    public CreeperElementalEntityModel(ModelPart modelPart) {
        super(modelPart);
        this.head = modelPart.getChild(EntityModelPartNames.HEAD);
        this.rightHindLeg = modelPart.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = modelPart.getChild(EntityModelPartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = modelPart.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = modelPart.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                EntityModelPartNames.HEAD,
                ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.001F, -4.0F, 8.0F, 8.0F, 8.0F, Dilation.NONE),
                ModelTransform.pivot(0.0F, 6.0F, 0.0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, Dilation.NONE),
                ModelTransform.pivot(0.0F, 6.0F, 0.0F)
        );
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, Dilation.NONE);
        modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-2.0F, 18.0F, 4.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(2.0F, 18.0F, 4.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-2.0F, 18.0F, -4.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(2.0F, 18.0F, -4.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    public void setAngles(CreeperElementalEntityRenderState creeperEntityRenderState) {
        super.setAngles(creeperEntityRenderState);
        this.head.yaw = creeperEntityRenderState.yawDegrees * (float) (Math.PI / 180.0);
        this.head.pitch = creeperEntityRenderState.pitch * (float) (Math.PI / 180.0);
        float f = creeperEntityRenderState.limbAmplitudeMultiplier;
        float g = creeperEntityRenderState.limbFrequency;
        this.leftHindLeg.pitch = MathHelper.cos(g * 0.6662F) * 1.4F * f;
        this.rightHindLeg.pitch = MathHelper.cos(g * 0.6662F + (float) Math.PI) * 1.4F * f;
        this.leftFrontLeg.pitch = MathHelper.cos(g * 0.6662F + (float) Math.PI) * 1.4F * f;
        this.rightFrontLeg.pitch = MathHelper.cos(g * 0.6662F) * 1.4F * f;
    }

}
