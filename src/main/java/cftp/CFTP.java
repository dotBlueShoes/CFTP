package cftp;

import cftp.entity.CFTPEntities;
import cftp.registries.CFTPBlocks;
import cftp.registries.CFTPItemGroups;
import cftp.registries.CFTPItems;
import cftp.registries.CFTPTags;
import cftp.world.gen.CFTPEntitySpawns;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CFTP implements ModInitializer {
	public static final String MOD_ID = "cftp";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric Server!");

		CFTPItems.register();
		CFTPBlocks.register();
		CFTPItemGroups.register();
		CFTPEntities.register();
		CFTPEntitySpawns.register();

		//ItemStack itemStack = new ItemStack(CFTPItems.EARTH_CHARGE, 1);
		//if (itemStack.isIn(CFTPTags.CHARGES)) {
		//	LOGGER.error("Matched charge tag!");
		//} else {
		//	LOGGER.error("NOPE! Matched charge tag!");
		//}
	}
}