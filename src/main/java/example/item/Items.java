package example.item;

import example.ExampleMod;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items {

    //public static final Item CREEPER_COOKIE_SPAWN_EGG = registerItem(
    //        "creeper_cookie_spawn_egg",
    //        new SpawnEggItem(Entities.CREEPER_COOKIE, new Item.Settings())
    //);
    //0x9dc783, 0xbfaf5f

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ExampleMod.MOD_ID, name), item);
    }

    //public static void register() {
    //    //ItemGroupEvents.modifyEntriesEvent(ItemGroups.)
    //}

}
