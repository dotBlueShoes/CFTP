package cftp.registries;

import cftp.entity.base.CreeperElementalEntityModel;
import cftp.entity.creeper.model.CreeperSpectreEntityModel;
import cftp.entity.herobrine.HerobrineEntityModel;
import cftp.entity.john.JohnEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class CFTPModelLayersClient {

    public static final EntityModelLayer GHOST_CREEPER =
            new EntityModelLayer(
                    Identifier.of("cftp", "creeper_model_ghost"),
                    "main"
            );

    public static final EntityModelLayer SPECTRE_CREEPER =
            new EntityModelLayer(
                    Identifier.of("cftp", "creeper_model_spectre"),
                    "main"
            );

    public static final EntityModelLayer HEROBRINE_CREEPER =
            new EntityModelLayer(
                    Identifier.of("cftp", "creeper_model_herobrine"),
                    "main"
            );

    public static final EntityModelLayer JOHN =
            new EntityModelLayer(
                    Identifier.of("cftp", "john"),
                    "main"
            );

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(
                GHOST_CREEPER,
                CreeperElementalEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                SPECTRE_CREEPER,
                CreeperSpectreEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                HEROBRINE_CREEPER,
                HerobrineEntityModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                JOHN,
                JohnEntityModel::getTexturedModelData
        );
    }
}
