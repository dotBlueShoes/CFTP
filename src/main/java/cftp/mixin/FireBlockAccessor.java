package cftp.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FireBlock.class)
public interface FireBlockAccessor {

    @Accessor("spreadChances")
    Object2IntMap<Block> getSpreadChances();

    @Accessor("burnChances")
    Object2IntMap<Block> getBurnChances();

}
