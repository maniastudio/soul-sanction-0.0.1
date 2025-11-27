package popmania.soulsanction;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import popmania.soulsanction.blocks.SunGodsPortalBlock;
import popmania.soulsanction.items.SoulStealerItem;
import popmania.soulsanction.items.VoidinSuppressionItem;

public class SoulSanction implements ModInitializer {
	public static final String MOD_ID = "soul-sanction";


	public static final Item VOID_SHARD = new Item(new FabricItemSettings());
	public static final Item SOUL_INGOT = new Item(new FabricItemSettings());
	public static final Item SOUL_POWDER = new Item(new FabricItemSettings());
	public static final Item SOUL_STEALER = new SoulStealerItem(ToolMaterials.NETHERITE, new FabricItemSettings());
	public static final Item VOIDIN_SUPPRESSION = new VoidinSuppressionItem(new FabricItemSettings().maxCount(1));


	public static final Block SOUL_STONE = new Block(
			AbstractBlock.Settings.copy(Blocks.BEDROCK).strength(-1.0F, 3600000.0F).dropsNothing());
	public static final Item SOUL_STONE_ITEM = new BlockItem(SOUL_STONE, new FabricItemSettings());

	public static final Block SUN_GODS_PORTAL = new SunGodsPortalBlock(
			AbstractBlock.Settings.copy(Blocks.OBSIDIAN).strength(50.0f, 1200.0f).nonOpaque());
	public static final Item SUN_GODS_PORTAL_ITEM = new BlockItem(SUN_GODS_PORTAL, new FabricItemSettings());

	
	public static final ItemGroup SOUL_SANCTION_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(SOUL_INGOT))
			.displayName(Text.literal("Soul Sanction"))
			.entries((context, entries) -> {
				entries.add(VOID_SHARD);
				entries.add(SOUL_POWDER);
				entries.add(SOUL_INGOT);
				entries.add(SOUL_STEALER);
				entries.add(VOIDIN_SUPPRESSION);
				entries.add(SOUL_STONE_ITEM);
				entries.add(SUN_GODS_PORTAL_ITEM);
			})
			.build();

	public static final RegistryKey<World> VOID_REALM_KEY =
			RegistryKey.of(RegistryKeys.WORLD, new Identifier(MOD_ID, "void_realm"));

	@Override
	public void onInitialize() {

		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "void_shard"), VOID_SHARD);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "soul_ingot"), SOUL_INGOT);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "soul_powder"), SOUL_POWDER);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "soul_stealer"), SOUL_STEALER);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "voidin_suppression"), VOIDIN_SUPPRESSION);

		
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "soul_stone"), SOUL_STONE);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "soul_stone"), SOUL_STONE_ITEM);

		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "sun_gods_portal"), SUN_GODS_PORTAL);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "sun_gods_portal"), SUN_GODS_PORTAL_ITEM);

	
		Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "soul_sanction_tab"), SOUL_SANCTION_GROUP);

	
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if (entity instanceof ServerPlayerEntity victim) {
				if (source.getAttacker() instanceof ServerPlayerEntity attacker) {
					if (attacker.getOffHandStack().getItem() == VOIDIN_SUPPRESSION) {
						ServerWorld targetWorld = attacker.getServer().getWorld(VOID_REALM_KEY);
						if (targetWorld != null) {
							// Teleport the victim to the void realm at Y=118
							victim.setHealth(20.0F);
							victim.clearStatusEffects();
							victim.teleport(targetWorld, victim.getX(), 118, victim.getZ(), victim.getYaw(), victim.getPitch());
							victim.sendMessage(Text.literal("You were banished to the Void Realm..."), false);

							// Remove Voidin Suppression from attacker’s off-hand
							attacker.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);

							// Give 6 Void Shards
							ItemStack reward = new ItemStack(VOID_SHARD, 6);
							if (!attacker.getInventory().insertStack(reward)) {
								attacker.dropItem(reward, false);
							}
						}
					}
				}
			}
		});

		System.out.println("Soul Sanction mod initialized.");
	}
}
