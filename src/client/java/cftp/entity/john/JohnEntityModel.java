package cftp.entity.john;

import cftp.CFTP;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

public class JohnEntityModel extends EntityModel<JohnEntityRenderState> implements
        ModelWithArms,
        ModelWithHead
{

    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

    public JohnEntityModel(ModelPart modelPart) {
        this(modelPart, RenderLayer::getEntityCutoutNoCull);
    }

    public JohnEntityModel(ModelPart modelPart, Function<Identifier, RenderLayer> function) {
        super(modelPart, function);
        this.head = modelPart.getChild(EntityModelPartNames.HEAD);
        this.body = modelPart.getChild(EntityModelPartNames.BODY);
        this.rightArm = modelPart.getChild(EntityModelPartNames.RIGHT_ARM);
        this.leftArm = modelPart.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightLeg = modelPart.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftLeg = modelPart.getChild(EntityModelPartNames.LEFT_LEG);
    }

    public static ModelData getModelData(Dilation dilation, float pivotOffsetY) {
        ModelData modelData = new ModelData();
        ModelPartData rootPart = modelData.getRoot();

        rootPart.addChild(
                EntityModelPartNames.HEAD, ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(
                                -6, -11, -4,
                                10, 12, 8,
                                dilation,
                                0.5f, 0.5f
                        ),
                ModelTransform.pivot(0.0f, 0.0f + pivotOffsetY, 0.0f)
        );

        rootPart.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create()
                        .uv(0, 37)
                        .cuboid(
                                -5.0f, -1.0f, -2.0f,
                                //8.0f, 12.0f, 4.0f,
                                10, 13, 6,
                                dilation,
                                0.5f, 0.5f
                        ),
                ModelTransform.pivot(0.0f, 0.0f + pivotOffsetY, 0.0f)
        );

        rootPart.addChild(
                EntityModelPartNames.RIGHT_ARM,
                ModelPartBuilder.create()
                        .uv(0, 20)
                        .cuboid(
                                -4.0f, -2.0f, -2.0f,
                                4.0f, 13.0f, 4.0f,
                                dilation,
                                0.5f, 0.5f
                        ),
                ModelTransform.pivot(-5.0f, 2.0f + pivotOffsetY, 0.0f)
        );

        rootPart.addChild(
                EntityModelPartNames.LEFT_ARM,
                ModelPartBuilder.create()
                        .uv(0, 20)
                        .mirrored()
                        .cuboid(
                                0.0f, -3.0f, -2.0f,
                                4.0f, 13.0f, 4.0f,
                                dilation,
                                0.5f, 0.5f
                        ),
                ModelTransform.pivot(5.0f, 2.0f + pivotOffsetY, 0.0f)
        );

        rootPart.addChild(
                EntityModelPartNames.RIGHT_LEG,
                ModelPartBuilder.create()
                        .uv(0, 20)
                        .cuboid(
                                -3.0f, 0.0f, -2.0f,
                                4.0f, 13.0f, 4.0f,
                                dilation,
                                0.5f, 0.5f
                        ),
                ModelTransform.pivot(-1.9f, 12.0f + pivotOffsetY, 0.0f)
        );

        rootPart.addChild(
                EntityModelPartNames.LEFT_LEG,
                ModelPartBuilder.create()
                        .uv(0, 20)
                        .mirrored()
                        .cuboid(
                                -1.0f, 0.0f, -2.0f,
                                4.0f, 13.0f, 4.0f,
                                dilation,
                                0.5f, 0.5f
                        ),
                ModelTransform.pivot(1.9f, 12.0f + pivotOffsetY, 0.0f)
        );

        return modelData;
    }

    // BipedEntityRenderState

    @Override
    public void setAngles(JohnEntityRenderState state) {
        this.head.pitch = 0.7853982f;

        //float f = bipedEntityRenderState.leaningPitch;

        //float k;
        //float i = g % 26.0f;

        //Arm arm = ((BipedEntityRenderState)bipedEntityRenderState).preferredArm;
        //float rArmSwing = arm == Arm.RIGHT && bipedEntityRenderState.handSwingProgress > 0.0f ? 0.0f : f;
        //float lArmSwing = k = arm == Arm.LEFT && bipedEntityRenderState.handSwingProgress > 0.0f ? 0.0f : f;

        //float rArmSwing = 1;
        //float lArmSwing = 1;
        //float l = 1;

        // WORKS !
        //float g = state.limbFrequency;
        //CFTP.LOGGER.info("freq: {}", g);

        //this.leftArm.pitch = MathHelper.lerpAngleRadians(k, this.leftArm.pitch, 1.5707964f - 1.5707964f * l);
        //this.rightArm.pitch = MathHelper.lerp(j, this.rightArm.pitch, 1.5707964f - 1.5707964f * l);
        //
        //this.leftArm.yaw = MathHelper.lerpAngleRadians(k, this.leftArm.yaw, (float)Math.PI);
        //this.rightArm.yaw = MathHelper.lerp(j, this.rightArm.yaw, (float)Math.PI);
        //
        //this.leftArm.roll = MathHelper.lerpAngleRadians(k, this.leftArm.roll, (float)Math.PI);
        //this.rightArm.roll = MathHelper.lerp(j, this.rightArm.roll, (float)Math.PI);

        //boolean bl2;
        //super.setAngles(bipedEntityRenderState);
        //BipedEntityModel.ArmPose armPose = ((BipedEntityRenderState)bipedEntityRenderState).leftArmPose;
        //BipedEntityModel.ArmPose armPose2 = ((BipedEntityRenderState)bipedEntityRenderState).rightArmPose;
        //float f = ((BipedEntityRenderState)bipedEntityRenderState).leaningPitch;
        //boolean bl = ((BipedEntityRenderState)bipedEntityRenderState).isGliding;
        //this.head.pitch = ((BipedEntityRenderState)bipedEntityRenderState).pitch * ((float)Math.PI / 180);
        //this.head.yaw = ((BipedEntityRenderState)bipedEntityRenderState).yawDegrees * ((float)Math.PI / 180);
        //if (bl) {
        //    this.head.pitch = -0.7853982f;
        //} else if (f > 0.0f) {
        //    this.head.pitch = MathHelper.lerpAngleRadians((float)f, (float)this.head.pitch, (float)-0.7853982f);
        //}
        //float g = ((BipedEntityRenderState)bipedEntityRenderState).limbFrequency;
        //float h = ((BipedEntityRenderState)bipedEntityRenderState).limbAmplitudeMultiplier;
        //this.rightArm.pitch = MathHelper.cos((float)(g * 0.6662f + (float)Math.PI)) * 2.0f * h * 0.5f / ((BipedEntityRenderState)bipedEntityRenderState).limbAmplitudeInverse;
        //this.leftArm.pitch = MathHelper.cos((float)(g * 0.6662f)) * 2.0f * h * 0.5f / ((BipedEntityRenderState)bipedEntityRenderState).limbAmplitudeInverse;
        //this.rightLeg.pitch = MathHelper.cos((float)(g * 0.6662f)) * 1.4f * h / ((BipedEntityRenderState)bipedEntityRenderState).limbAmplitudeInverse;
        //this.leftLeg.pitch = MathHelper.cos((float)(g * 0.6662f + (float)Math.PI)) * 1.4f * h / ((BipedEntityRenderState)bipedEntityRenderState).limbAmplitudeInverse;
        //this.rightLeg.yaw = 0.005f;
        //this.leftLeg.yaw = -0.005f;
        //this.rightLeg.roll = 0.005f;
        //this.leftLeg.roll = -0.005f;
        //if (((BipedEntityRenderState)bipedEntityRenderState).hasVehicle) {
        //    this.rightArm.pitch += -0.62831855f;
        //    this.leftArm.pitch += -0.62831855f;
        //    this.rightLeg.pitch = -1.4137167f;
        //    this.rightLeg.yaw = 0.31415927f;
        //    this.rightLeg.roll = 0.07853982f;
        //    this.leftLeg.pitch = -1.4137167f;
        //    this.leftLeg.yaw = -0.31415927f;
        //    this.leftLeg.roll = -0.07853982f;
        //}
        //boolean bl3 = bl2 = ((BipedEntityRenderState)bipedEntityRenderState).mainArm == Arm.RIGHT;
        //if (((BipedEntityRenderState)bipedEntityRenderState).isUsingItem) {
        //    boolean bl4 = bl3 = ((BipedEntityRenderState)bipedEntityRenderState).activeHand == Hand.MAIN_HAND;
        //    if (bl3 == bl2) {
        //        this.positionRightArm(bipedEntityRenderState, armPose2);
        //    } else {
        //        this.positionLeftArm(bipedEntityRenderState, armPose);
        //    }
        //} else {
        //    boolean bl5 = bl3 = bl2 ? armPose.isTwoHanded() : armPose2.isTwoHanded();
        //    if (bl2 != bl3) {
        //        this.positionLeftArm(bipedEntityRenderState, armPose);
        //        this.positionRightArm(bipedEntityRenderState, armPose2);
        //    } else {
        //        this.positionRightArm(bipedEntityRenderState, armPose2);
        //        this.positionLeftArm(bipedEntityRenderState, armPose);
        //    }
        //}
        //this.animateArms(bipedEntityRenderState, bipedEntityRenderState.age);
        //if (((BipedEntityRenderState)bipedEntityRenderState).isInSneakingPose) {
        //    this.body.pitch = 0.5f;
        //    this.rightArm.pitch += 0.4f;
        //    this.leftArm.pitch += 0.4f;
        //    this.rightLeg.pivotZ += 4.0f;
        //    this.leftLeg.pivotZ += 4.0f;
        //    this.head.pivotY += 4.2f;
        //    this.body.pivotY += 3.2f;
        //    this.leftArm.pivotY += 3.2f;
        //    this.rightArm.pivotY += 3.2f;
        //}
        //if (armPose2 != BipedEntityModel.ArmPose.SPYGLASS) {
        //    CrossbowPosing.swingArm(this.rightArm, ((BipedEntityRenderState)bipedEntityRenderState).age, 1.0f);
        //}
        //if (armPose != BipedEntityModel.ArmPose.SPYGLASS) {
        //    CrossbowPosing.swingArm(this.leftArm, ((BipedEntityRenderState)bipedEntityRenderState).age, -1.0f);
        //}
        //if (f > 0.0f) {
        //    float l;
        //    float k;
        //    float i = g % 26.0f;
        //    Arm arm = ((BipedEntityRenderState)bipedEntityRenderState).preferredArm;
        //    float j = arm == Arm.RIGHT && ((BipedEntityRenderState)bipedEntityRenderState).handSwingProgress > 0.0f ? 0.0f : f;
        //    float f2 = k = arm == Arm.LEFT && ((BipedEntityRenderState)bipedEntityRenderState).handSwingProgress > 0.0f ? 0.0f : f;
        //    if (!((BipedEntityRenderState)bipedEntityRenderState).isUsingItem) {
        //        if (i < 14.0f) {
        //            this.leftArm.pitch = MathHelper.lerpAngleRadians((float)k, (float)this.leftArm.pitch, (float)0.0f);
        //            this.rightArm.pitch = MathHelper.lerp((float)j, (float)this.rightArm.pitch, (float)0.0f);
        //            this.leftArm.yaw = MathHelper.lerpAngleRadians((float)k, (float)this.leftArm.yaw, (float)((float)Math.PI));
        //            this.rightArm.yaw = MathHelper.lerp((float)j, (float)this.rightArm.yaw, (float)((float)Math.PI));
        //            this.leftArm.roll = MathHelper.lerpAngleRadians((float)k, (float)this.leftArm.roll, (float)((float)Math.PI + 1.8707964f * this.method_2807(i) / this.method_2807(14.0f)));
        //            this.rightArm.roll = MathHelper.lerp((float)j, (float)this.rightArm.roll, (float)((float)Math.PI - 1.8707964f * this.method_2807(i) / this.method_2807(14.0f)));
        //        } else if (i >= 14.0f && i < 22.0f) {
        //            l = (i - 14.0f) / 8.0f;
        //            this.leftArm.pitch = MathHelper.lerpAngleRadians((float)k, (float)this.leftArm.pitch, (float)(1.5707964f * l));
        //            this.rightArm.pitch = MathHelper.lerp((float)j, (float)this.rightArm.pitch, (float)(1.5707964f * l));
        //            this.leftArm.yaw = MathHelper.lerpAngleRadians((float)k, (float)this.leftArm.yaw, (float)((float)Math.PI));
        //            this.rightArm.yaw = MathHelper.lerp((float)j, (float)this.rightArm.yaw, (float)((float)Math.PI));
        //            this.leftArm.roll = MathHelper.lerpAngleRadians((float)k, (float)this.leftArm.roll, (float)(5.012389f - 1.8707964f * l));
        //            this.rightArm.roll = MathHelper.lerp((float)j, (float)this.rightArm.roll, (float)(1.2707963f + 1.8707964f * l));
        //        } else if (i >= 22.0f && i < 26.0f) {
        //            l = (i - 22.0f) / 4.0f;
        //            this.leftArm.pitch = MathHelper.lerpAngleRadians((float)k, (float)this.leftArm.pitch, (float)(1.5707964f - 1.5707964f * l));
        //            this.rightArm.pitch = MathHelper.lerp((float)j, (float)this.rightArm.pitch, (float)(1.5707964f - 1.5707964f * l));
        //            this.leftArm.yaw = MathHelper.lerpAngleRadians((float)k, (float)this.leftArm.yaw, (float)((float)Math.PI));
        //            this.rightArm.yaw = MathHelper.lerp((float)j, (float)this.rightArm.yaw, (float)((float)Math.PI));
        //            this.leftArm.roll = MathHelper.lerpAngleRadians((float)k, (float)this.leftArm.roll, (float)((float)Math.PI));
        //            this.rightArm.roll = MathHelper.lerp((float)j, (float)this.rightArm.roll, (float)((float)Math.PI));
        //        }
        //    }
        //    l = 0.3f;
        //    float m = 0.33333334f;
        //    this.leftLeg.pitch = MathHelper.lerp((float)f, (float)this.leftLeg.pitch, (float)(0.3f * MathHelper.cos((float)(g * 0.33333334f + (float)Math.PI))));
        //    this.rightLeg.pitch = MathHelper.lerp((float)f, (float)this.rightLeg.pitch, (float)(0.3f * MathHelper.cos((float)(g * 0.33333334f))));
        //}
    }

    protected void animateArms(JohnEntityRenderState state, float animationProgress) {
        float handSwingProgress = state.handSwingProgress;
        float ageScale = state.ageScale;

        CFTP.LOGGER.info("swing: {}, ageScale: {}", handSwingProgress, ageScale);

        if (handSwingProgress <= 0.0f) {
            return;
        }

        //Arm arm = state.preferredArm;
        //ModelPart modelPart = this.getArm(arm);

        ModelPart modelPreferredArm = this.rightArm;

        this.body.yaw = MathHelper.sin(
                MathHelper.sqrt(handSwingProgress) * ((float)Math.PI * 2)
        ) * 0.2f;

        this.rightArm.pivotZ =  MathHelper.sin(this.body.yaw) * 5.0f * ageScale;
        this.rightArm.pivotX = -MathHelper.cos(this.body.yaw) * 5.0f * ageScale;
        this.leftArm.pivotZ  = -MathHelper.sin(this.body.yaw) * 5.0f * ageScale;
        this.leftArm.pivotX  =  MathHelper.cos(this.body.yaw) * 5.0f * ageScale;

        this.rightArm.yaw  += this.body.yaw;
        this.leftArm.yaw   += this.body.yaw;
        this.leftArm.pitch += this.body.yaw;

        float g = 1.0f - handSwingProgress;
        g *= g;
        g *= g;
        g = 1.0f - g;

        float i = MathHelper.sin(g * (float)Math.PI);
        float j = MathHelper.sin(handSwingProgress * (float)Math.PI) *
                -(this.head.pitch - 0.7f) * 0.75f;

        modelPreferredArm.pitch -= i * 1.2f + j;
        modelPreferredArm.yaw += this.body.yaw * 2.0f;
        modelPreferredArm.roll += MathHelper.sin(handSwingProgress * (float)Math.PI) * -0.4f;

        //float g = handSwingProgress;
        //
        //this.body.yaw = MathHelper.sin(MathHelper.sqrt(g) * ((float)Math.PI * 2)) * 0.2f;
        //if (arm == Arm.LEFT) {
        //    this.body.yaw *= -1.0f;
        //}
        //
        //float h = state.ageScale;
        //
        //this.rightArm.pivotZ = MathHelper.sin(this.body.yaw) * 5.0f * h;
        //this.rightArm.pivotX = -MathHelper.cos(this.body.yaw) * 5.0f * h;
        //this.leftArm.pivotZ = -MathHelper.sin(this.body.yaw) * 5.0f * h;
        //this.leftArm.pivotX = MathHelper.cos(this.body.yaw) * 5.0f * h;
        //
        //this.rightArm.yaw += this.body.yaw;
        //this.leftArm.yaw += this.body.yaw;
        //this.leftArm.pitch += this.body.yaw;
        //
        //g = 1.0f - handSwingProgress;
        //g *= g;
        //g *= g;
        //g = 1.0f - g;
        //
        //float i = MathHelper.sin(g * (float)Math.PI);
        //float j = MathHelper.sin(handSwingProgress * (float)Math.PI) * -(this.head.pitch - 0.7f) * 0.75f;
        //
        //modelPart.pitch -= i * 1.2f + j;
        //modelPart.yaw += this.body.yaw * 2.0f;
        //modelPart.roll += MathHelper.sin(handSwingProgress * (float)Math.PI) * -0.4f;
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        this.root.rotate(matrices);
        this.getArm(arm).rotate(matrices);
    }

    protected ModelPart getArm(Arm arm) {
        if (arm == Arm.LEFT) {
            return this.leftArm;
        }
        return this.rightArm;
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData(Dilation.NONE, 0.0f);
        return TexturedModelData.of(modelData, 128, 128);
    }

}
