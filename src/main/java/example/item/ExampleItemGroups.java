package example.item;

import example.ExampleMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ExampleItemGroups {

    public static final ItemGroup EXAMPLE_ITEMS = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(ExampleMod.MOD_ID, "coin_copper"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ExampleItems.COIN_COPPER))
                    .displayName(Text.translatable("itemgroup.example.example_items"))
                    .entries(
                            (displayContext, entries) -> {
                                entries.add(ExampleItems.ELEMENTAL_POWDER);
                                entries.add(ExampleItems.COIN_COPPER);
                                entries.add(ExampleItems.COIN_IRON);
                                entries.add(ExampleItems.COIN_GOLD);
                            }
                    )
                    .build()
    );

    public static void register() {

        // An item has to be placed inside a group to be registered correctly.

        //ItemGroupEvents.modifyEntriesEvent(net.minecraft.item.ItemGroups.INGREDIENTS).register(
        //        entries -> entries.add(Items.ELEMENTAL_POWDER)
        //);

        //ItemGroupEvents.modifyEntriesEvent(net.minecraft.item.ItemGroups.INGREDIENTS).register(
        //        entries -> entries.add(ExampleItems.COIN_COPPER)
        //);
//
        //ItemGroupEvents.modifyEntriesEvent(net.minecraft.item.ItemGroups.INGREDIENTS).register(
        //        entries -> entries.add(Items.COIN_IRON)
        //);
//
        //ItemGroupEvents.modifyEntriesEvent(net.minecraft.item.ItemGroups.INGREDIENTS).register(
        //        entries -> entries.add(Items.COIN_GOLD)
        //);

    }

}
