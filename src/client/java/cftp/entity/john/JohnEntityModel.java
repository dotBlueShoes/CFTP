package cftp.entity.john;

import cftp.CFTP;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.animation.WardenAnimations;
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
                                -6.0f, -11.0f, -6.0f,
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
                                -5.0f, -1.0f, -4.0f,
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
                                -4.0f, -2.0f, -4.0f,
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
                                0.0f, -3.0f, -3.0f,
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
        super.setAngles(state);

        //this.setHeadAngle(wardenEntityRenderState.yawDegrees, wardenEntityRenderState.pitch);
        //this.setLimbAngles(wardenEntityRenderState.limbFrequency, wardenEntityRenderState.limbAmplitudeMultiplier);
        //this.setHeadAndBodyAngles(wardenEntityRenderState.age);

        this.setBaseAngles();
        this.setLimbRanges(state.yawDegrees, state.pitch, state.age);
        this.setLimbAngles(state.limbFrequency, state.limbAmplitudeMultiplier);

        //this.animate(state.attackingAnimationState, WardenAnimations.ATTACKING, state.age);
        //this.animate(state.chargingSonicBoomAnimationState, WardenAnimations.CHARGING_SONIC_BOOM, state.age);
        //this.animate(state.diggingAnimationState, WardenAnimations.DIGGING, state.age);
        //this.animate(state.emergingAnimationState, WardenAnimations.EMERGING, state.age);
        //this.animate(state.roaringAnimationState, WardenAnimations.ROARING, state.age);
        //this.animate(state.sniffingAnimationState, WardenAnimations.SNIFFING, state.age);
    }


    private void setBaseAngles() {
        // pitch -> up-down range
        // yaw -> left-right range
        // roll -> 2d-rotation range
        // ---
        this.head.pitch         = 0.7853982f;
        this.head.yaw           = 0.111f;
        this.head.roll          = 0.0f;
        // ---
        this.body.pitch         = 0.1f;
        this.body.yaw           = 0.0f;
        this.body.roll          = 0.0f;
        // ---
        this.rightArm.pitch     = -0.082f;
        this.rightArm.yaw       = 0.0f;
        this.rightArm.roll      = 0.1f;
        // ---
        this.rightLeg.pitch     = 0.0f;
        this.rightLeg.yaw       = 0.0f;
        this.rightLeg.roll      = 0.0f;
        // ---
        this.leftArm.pitch      = 0.1371f;
        this.leftArm.yaw        = 0.0f;
        this.leftArm.roll       = -0.1f;
        // ---
        this.leftLeg.pitch     = 0.0f;
        this.leftLeg.yaw       = 0.0f;
        this.leftLeg.roll      = 0.0f;
    }

    private void setLimbRanges(float yaw, float pitch, float animationProgress) {
        float f = animationProgress * 0.1f;
        float g = MathHelper.cos(f);
        float h = MathHelper.sin(f);

        this.head.pitch += pitch * ((float)Math.PI / (180 * 2));
        this.head.yaw += yaw * ((float)Math.PI / (180 * 2));
        this.head.roll += 0.03f * g;
    }

    // todo. understand whats range whats speed.
    private void setLimbAngles(float angle, float distance) {
        float f = Math.min(0.5f, 3.0f * distance); // I think this might be range !
        float g = angle * 0.8662f;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        //float j = Math.min(0.35f, f);
        //float k = MathHelper.cos(g + (float)Math.PI);

        this.head.roll += 0.3f * i * f;
        //this.head.pitch += 1.2f * MathHelper.cos(g + 1.5707964f) * j;
        //this.body.roll = 0.1f * i * f;
        //this.body.pitch = 1.0f * h * j;

        this.leftArm.pitch += -(0.8f * h * f);
        this.leftArm.roll += 0.01f * i * f; // I think this is speed not range !

        this.rightArm.pitch += -(0.8f * i * f);
        this.rightArm.roll += 0.01f * h * f; // I think this is speed not range !

        this.leftLeg.pitch += 1.0f * h * f;
        this.leftLeg.roll += 0.01f * i * f; // I think this is speed not range !

        this.rightLeg.pitch += 1.0f * i * f;
        this.rightLeg.roll += 0.01f * h * f; // I think this is speed not range !

        //this.setArmPivots();
    }

    //private void setArmPivots() {
    //    this.leftArm.yaw = 0.0f;
    //    this.leftArm.pivotZ = 1.0f;
    //    this.leftArm.pivotX = 13.0f;
    //    this.leftArm.pivotY = -13.0f;
    //    this.rightArm.yaw = 0.0f;
    //    this.rightArm.pivotZ = 1.0f;
    //    this.rightArm.pivotX = -13.0f;
    //    this.rightArm.pivotY = -13.0f;
    //}

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
