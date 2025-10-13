package cftp.registries;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

import java.util.Optional;

public class CFTPTrades {

    public static void register() {

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    Optional.of(new TradedItem(CFTPItems.SULPHUR, 3)),
                    new ItemStack(Items.GUNPOWDER, 3),
                    6,
                    7,
                    0.10F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 5),
                    Optional.of(new TradedItem(CFTPItems.SULPHUR, 2)),
                    new ItemStack(CFTPItems.SULPHUR_DUST_BUCKET, 1),
                    3,
                    9,
                    0.05F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    Optional.of(new TradedItem(CFTPItems.SAW_DUST, 16)),
                    new ItemStack(Items.CARROT, 8),
                    3,
                    1,
                    0.15F
            ));
        });

    }

}
