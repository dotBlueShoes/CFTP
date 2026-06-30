package cftp;

import cftp.blocks.ObsidianBlock;
import cftp.config.CFTPConfig;
import cftp.config.CFTPData;
import cftp.registries.CFTPEntities;
import cftp.registries.*;
import cftp.world.gen.CFTPEntitySpawns;
import net.fabricmc.api.ModInitializer;

import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
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

		LOGGER.info("Hello CFTP Server!");

        CFTPConfig.createOrAndLoad(true);

        //Registries.REGISTRIES.freeze()

		CFTPItems.register();
		CFTPBlocks.register();
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

	}
}