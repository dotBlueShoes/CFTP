package cftp.mixin;

import cftp.utility.IColor;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockState.class)
public abstract class BlockStateMixin implements IColor {

    @Unique public boolean isPainted = false;
    @Unique public int paintColor = 0b11111111111111111111111111111111;

    @Override
    public boolean getIsPainted() {
        return isPainted;
    }

    @Override
    public void setIsPainted(boolean newIsPainted) {
        isPainted = newIsPainted;
    }

    @Override
    public int getPaintColor() {
        return paintColor;
    }

    @Override
    public void setPaintColor(int newPaintColor) {
        paintColor = newPaintColor;
    }

}
