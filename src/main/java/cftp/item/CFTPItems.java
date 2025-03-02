package cftp.item;

import cftp.CFTP;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class CFTPItems {

    public static final Item ELEMENTAL_POWDER = register("elemental_powder", Item::new,
            new Item.Settings()
    );

    public static final Item COIN_COPPER = register("coin_copper", Item::new,
            new Item.Settings()
    );

    public static final Item COIN_IRON = register("coin_iron", Item::new,
            new Item.Settings()
    );

    public static final Item COIN_GOLD = register("coin_gold", Item::new,
            new Item.Settings()
    );

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(
                RegistryKeys.ITEM,
                Identifier.of(CFTP.MOD_ID, path)
        );

        return net.minecraft.item.Items.register(registryKey, factory, settings);
    }

    // HACK. Java is weird. We need to call a method even if empty. To make the unreferenced variables
    //  we made compile from this file-class.
    public static void register() {

    }

}
