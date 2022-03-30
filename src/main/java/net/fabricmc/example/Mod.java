package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	public static final BellItem BELL_ITEM = new BellItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
	public static final ToneGrowthItem GROWTH_TONE  = new ToneGrowthItem(ToolMaterials.WOOD, new Item.Settings().group(ItemGroup.MISC));
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ITEM, new Identifier("resonance", "bell"), BELL_ITEM);
		Registry.register(Registry.ITEM, new Identifier("resonance", "tone_of_growth"), GROWTH_TONE);
		LOGGER.info("Hello Fabric world!");
	}
}
