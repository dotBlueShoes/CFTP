package cftp.item;

import cftp.CFTP;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CFTPItemGroups {

    private static final String ITEM_GROUP_ITEMS = "itemgroup.cftp.cftp_items";

    public static final ItemGroup CFTP_ITEMS = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(CFTP.MOD_ID, "coin_copper"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(CFTPItems.COIN_COPPER))
                    .displayName(Text.translatable(ITEM_GROUP_ITEMS))
                    .entries(
                            (displayContext, entries) -> {
                                entries.add(CFTPItems.ELEMENTAL_POWDER);
                                entries.add(CFTPItems.SAW_DUST);
                                entries.add(CFTPItems.COIN_COPPER);
                                entries.add(CFTPItems.COIN_IRON);
                                entries.add(CFTPItems.COIN_GOLD);
                            }
                    )
                    .build()
    );

    // HACK. Java is weird. We need to call a method even if empty. To make the unreferenced variables
    //  we made compile from this file-class.
    public static void register() {

    }

}
