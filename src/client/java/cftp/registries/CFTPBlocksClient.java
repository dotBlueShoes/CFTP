package cftp.registries;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class CFTPBlocksClient {

    public static void register() {
        BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.SULPHUR_CLOUD, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.YELLOW_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.BLUE_MUSHROOM, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.YELLOW_COBWEB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CFTPBlocks.BLUE_COBWEB, RenderLayer.getCutout());
    }

}
