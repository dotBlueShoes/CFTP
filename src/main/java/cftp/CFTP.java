package cftp;

import cftp.blocks.ObsidianBlock;
import cftp.config.CFTPConfig;
import cftp.registries.CFTPEntities;
import cftp.registries.*;
import cftp.utility.JohnHelper;
import cftp.world.gen.CFTPEntitySpawns;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CFTP implements ModInitializer {

	public static final String MOD_ID = "cftp";
    public static final int currentConfigVersion = 2;

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello CFTP Server!");

        CFTPConfig.createOrAndLoad(false);

        //Registries.REGISTRIES.freeze()

        CFTPSoundEvents.register();
		CFTPItems.register();
		CFTPBlocks.register();
        CFTPParticles.register();
        CFTPStatusEffects.register();
		CFTPItemGroups.register();
		CFTPEntities.register();
		CFTPEntitySpawns.register();
        CFTPFeatures.register();
        CFTPTrades.register();

        {
            Item obsidianItem = Registries.ITEM.get(Identifier.of("minecraft", "obsidian"));
            if (obsidianItem != Items.OBSIDIAN) {
                LOGGER.warn("It's NOT!");
            }
            if (obsidianItem instanceof BlockItem blockItem) {
                if (blockItem.getBlock() instanceof ObsidianBlock) {
                    LOGGER.warn("IT IS!");
                }
            }

            System.out.println("OBSIDIAN item: " + Items.OBSIDIAN);
            System.out.println("OBSIDIAN block: " + Blocks.OBSIDIAN);
            System.out.println("OBSIDIAN asItem: " + Blocks.OBSIDIAN.asItem());
            System.out.println("OBSIDIAN itemBlock: " + Registries.ITEM.get(Identifier.of("minecraft", "obsidian")));
        }


        ServerLivingEntityEvents.AFTER_DAMAGE.register(
                (targetEntity, source, baseDamageTaken, damageTaken, blocked) -> {

                    // Check if we're executing on Server only.
                    if (!(targetEntity instanceof LivingEntity livingTarget)) {
                        return;
                    }

                    Entity attacker = source.getAttacker();
                    if (attacker instanceof LivingEntity livingAttacker) {
                        StatusEffectInstance johnEffect = livingAttacker
                                .getStatusEffect(CFTPStatusEffects.JOHN_STATUS_EFFECT);

                        if (johnEffect != null) {
                            JohnHelper.applyJohnBasedOnDifficulty(livingTarget);
                        }
                    }
                }
        );

	}
}

//(entity, source, baseDamageTaken, damageTaken, blocked) -> {
//    // Only run on the server.
//    if (!(entity instanceof LivingEntity victim)) {
//        return;
//    }
//
//    // Does the victim have our custom effect?
//    if (!victim.hasStatusEffect(ModEffects.SHARED_EFFECT)) {
//        return;
//    }
//
//    // Find the attacker.
//    Entity attacker = source.getAttacker();
//
//    if (!(attacker instanceof LivingEntity livingAttacker)) {
//        return;
//    }
//
//    // Get the effect instance from the victim.
//    StatusEffectInstance effect =
//            victim.getStatusEffect(ModEffects.SHARED_EFFECT);
//
//    if (effect == null) {
//        return;
//    }
//
//    // Apply the same effect to the attacker.
//    livingAttacker.addStatusEffect(
//            new StatusEffectInstance(
//                    ModEffects.SHARED_EFFECT,
//                    effect.getDuration(),
//                    effect.getAmplifier(),
//                    effect.isAmbient(),
//                    effect.shouldShowParticles(),
//                    effect.shouldShowIcon()
//            )
//    );
//}
