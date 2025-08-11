package cftp.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class PillarFeature extends Feature<DefaultFeatureConfig> {

    public PillarFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        return false;
    }

    //public PillarFeature(Codec<NoFeatureConfig> codec) { super(codec); }
//
    //@Override
    //public boolean generate(FeatureContext<NoFeatureConfig> context) {
    //    var world = context.getWorld();
    //    var pos = context.getOrigin();
    //    // find surface (e.g., dirt with air above...)
    //    // iterate upward and place blocks...
    //    return success;
    //}
}
