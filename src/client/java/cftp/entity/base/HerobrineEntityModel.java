package cftp.entity.base;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class HerobrineEntityModel extends BipedEntityModel<CreeperHerobrineEntityRenderState> {

    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_pants";
    private static final String RIGHT_PANTS = "right_pants";
    private static final String JACKET = "jacket";
    private static final String BODY = "body";
    private static final String LEFT_ARM = "left_arm";
    private static final String RIGHT_ARM = "right_arm";
    private static final String LEFT_LEG = "left_leg";
    private static final String RIGHT_LEG = "right_leg";

    private final List<ModelPart> parts;
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;
    private final boolean thinArms;

    public HerobrineEntityModel(ModelPart modelPart, boolean thinArms) {
        super(modelPart, RenderLayer::getEntityTranslucent);

        this.thinArms = thinArms;
        this.leftSleeve = this.leftArm.getChild(LEFT_SLEEVE);
        this.rightSleeve = this.rightArm.getChild(RIGHT_SLEEVE);
        this.leftPants = this.leftLeg.getChild(LEFT_PANTS);
        this.rightPants = this.rightLeg.getChild(RIGHT_PANTS);
        this.jacket = this.body.getChild(JACKET);

        this.parts = List.of(
            this.head, this.body, this.leftArm,
            this.rightArm, this.leftLeg, this.rightLeg
        );
    }

    public static TexturedModelData getTexturedModelData() {
        Dilation dilation = new Dilation(0);
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();

        {
            ModelPartData dataLeftArm = modelPartData.addChild(LEFT_ARM, ModelPartBuilder.create().uv(32, 48).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(5.0F, 2.0F, 0.0F));
            ModelPartData dataRightArm = modelPartData.getChild(RIGHT_ARM);
            dataLeftArm.addChild(LEFT_SLEEVE, ModelPartBuilder.create().uv(48, 48).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);
            dataRightArm.addChild(RIGHT_SLEEVE, ModelPartBuilder.create().uv(40, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);
        }

        {
            ModelPartData dataLeftLeg = modelPartData.addChild(LEFT_LEG, ModelPartBuilder.create().uv(16, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(1.9F, 12.0F, 0.0F));
            ModelPartData dataRightLeg = modelPartData.getChild(RIGHT_LEG);


            dataLeftLeg.addChild(LEFT_PANTS, ModelPartBuilder.create().uv(0, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);
            dataRightLeg.addChild(RIGHT_PANTS, ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);
        }

        {
            ModelPartData dataBody = modelPartData.getChild(BODY);
            dataBody.addChild(JACKET, ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE);
        }

        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setAngles(CreeperHerobrineEntityRenderState playerEntityRenderState) {
        boolean bl = true;
        this.body.visible = bl;
        this.rightArm.visible = bl;
        this.leftArm.visible = bl;
        this.rightLeg.visible = bl;
        this.leftLeg.visible = bl;

        this.hat.visible = false;
        this.jacket.visible = false;
        this.leftPants.visible = false;
        this.rightPants.visible = false;
        this.leftSleeve.visible = false;
        this.rightSleeve.visible = false;

        super.setAngles(playerEntityRenderState);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.leftSleeve.visible = visible;
        this.rightSleeve.visible = visible;
        this.leftPants.visible = visible;
        this.rightPants.visible = visible;
        this.jacket.visible = visible;
    }

    public void setArmAngle(Arm arm, MatrixStack matrices) {
        this.getRootPart().rotate(matrices);
        ModelPart modelPart = this.getArm(arm);
        if (this.thinArms) {
            float f = 0.5F * (float)(arm == Arm.RIGHT ? 1 : -1);
            modelPart.pivotX += f;
            modelPart.rotate(matrices);
            modelPart.pivotX -= f;
        } else {
            modelPart.rotate(matrices);
        }

    }

    public ModelPart getRandomPart(Random random) {
        return (ModelPart)Util.getRandom(this.parts, random);
    }
}

