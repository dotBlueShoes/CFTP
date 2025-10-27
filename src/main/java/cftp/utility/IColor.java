package cftp.utility;

import org.spongepowered.asm.mixin.Unique;

public interface IColor {

    boolean getIsPainted();
    void setIsPainted(boolean isPainted);

    int getPaintColor();
    void setPaintColor(int color);

}
