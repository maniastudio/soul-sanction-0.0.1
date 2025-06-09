package popmania.soulsanction;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import popmania.soulsanction.blocks.SunGodsPortalBlock;
import popmania.soulsanction.items.SoulStealerItem;

public class SoulSanction implements ModInitializer {
	public static final String MOD_ID = "soul-sanction";

	// Items
	public static final Item VOID_SHARD = new Item(new FabricItemSettings());
	public static final Item SOUL_INGOT = new Item(new FabricItemSettings());
	public static final Item SOUL_POWDER = new Item(new FabricItemSettings());

	// Blocks
	public static final Block SOUL_STONE = new Block(AbstractBlock.Settings.copy(Blocks.BEDROCK).strength(-1.0F, 3600000.0F).dropsNothing());
	public static final Item SOUL_STONE_ITEM = new BlockItem(SOUL_STONE, new FabricItemSettings());

	public static final Item SOUL_STEALER = new SoulStealerItem(ToolMaterials.NETHERITE, new FabricItemSettings());

	// Sun Gods Portal Block + Item
	public static final Block SUN_GODS_PORTAL = new SunGodsPortalBlock(
			AbstractBlock.Settings.copy(Blocks.OBSIDIAN).strength(50.0f, 1200.0f).nonOpaque()
	);
	public static final Item SUN_GODS_PORTAL_ITEM = new BlockItem(SUN_GODS_PORTAL, new FabricItemSettings());

	// Creative Tab
	public static final ItemGroup SOUL_SANCTION_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(SOUL_INGOT))
			.displayName(Text.literal("Soul Sanction"))
			.entries((context, entries) -> {
				entries.add(VOID_SHARD);
				entries.add(SOUL_POWDER);
				entries.add(SOUL_INGOT);
				entries.add(SOUL_STEALER);
				entries.add(SOUL_STONE_ITEM);
				entries.add(SUN_GODS_PORTAL_ITEM); // Add Sun Gods Portal item here
			})
			.build();

	@Override
	public void onInitialize() {
		// Register items
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "void_shard"), VOID_SHARD);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "soul_ingot"), SOUL_INGOT);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "soul_powder"), SOUL_POWDER);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "soul_stealer"), SOUL_STEALER);

		// Register blocks
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "soul_stone"), SOUL_STONE);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "soul_stone"), SOUL_STONE_ITEM);

		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "sun_gods_portal"), SUN_GODS_PORTAL);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "sun_gods_portal"), SUN_GODS_PORTAL_ITEM);

		// Register creative tab
		Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "soul_sanction_tab"), SOUL_SANCTION_GROUP);

		System.out.println("Soul Sanction mod initialized.");
	}
}
