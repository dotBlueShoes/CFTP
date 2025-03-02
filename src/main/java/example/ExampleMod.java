package example;

import example.entity.CreeperLightingEntity;
import example.entity.Entities;
import example.entity.CreeperCookieEntity;
import example.item.ExampleItemGroups;
import example.item.ExampleItems;
import example.world.gen.EntitySpawns;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "example";

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

		ExampleItems.sample();
		ExampleItemGroups.register();

		FabricDefaultAttributeRegistry.register(Entities.CREEPER_LIGHTING, CreeperLightingEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(Entities.CREEPER_COOKIE, CreeperCookieEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(Entities.CREEPER_WATER, CreeperCookieEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(Entities.CREEPER_FIRE, CreeperCookieEntity.createAttributes());

		EntitySpawns.addSpawns();
	}
}