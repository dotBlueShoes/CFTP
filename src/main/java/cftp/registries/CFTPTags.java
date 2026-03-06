package cftp.registries;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class CFTPTags {

    public static final TagKey<Block> VAPORIZABLES =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of("cftp", "vaporizables"));

    public static final TagKey<Block> FERTILIZED_DIRTS =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of("cftp", "fertilized_dirts"));

    public static final TagKey<Item> CHARGES =
            TagKey.of(RegistryKeys.ITEM, Identifier.of("cftp", "charges"));

}
