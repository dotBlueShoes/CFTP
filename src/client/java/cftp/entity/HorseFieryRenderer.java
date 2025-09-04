package cftp.entity;

import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HorseMarkingFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.render.entity.state.HorseEntityRenderState;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.HorseMarking;
import net.minecraft.util.Identifier;

public class HorseFieryRenderer extends AbstractHorseEntityRenderer<HorseFieryEntity, HorseEntityRenderState, HorseEntityModel> {

    private static final Identifier TEXTURE = Identifier.of("cftp", "textures/entity/horse_fiery.png");

    public HorseFieryRenderer(EntityRendererFactory.Context context, HorseEntityModel model, HorseEntityModel babyModel) {
        super(context, model, babyModel);
    }

    public HorseFieryRenderer(EntityRendererFactory.Context context) {
        super(context, new HorseEntityModel(context.getPart(EntityModelLayers.HORSE)), new HorseEntityModel(context.getPart(EntityModelLayers.HORSE_BABY)));
        this.addFeature(new HorseMarkingFeatureRenderer(this));
        this.addFeature(new HorseArmorFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
    }

    @Override
    public Identifier getTexture(HorseEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public HorseEntityRenderState createRenderState() {
        return new HorseEntityRenderState();
    }

    public void updateRenderState(HorseFieryEntity horseEntity, HorseEntityRenderState horseEntityRenderState, float f) {
        super.updateRenderState(horseEntity, horseEntityRenderState, f);
        horseEntityRenderState.color = HorseColor.WHITE;    // horseEntity.getVariant();
        horseEntityRenderState.marking = HorseMarking.NONE; //horseEntity.getMarking();
        horseEntityRenderState.armor = horseEntity.getBodyArmor().copy();
    }

}
