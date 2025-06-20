package popmania.soulsanction.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Unique
    private static final RegistryKey<World> VOIDIN_REALM = RegistryKey.of(
            RegistryKeys.WORLD,
            new Identifier("soul-sanction", "void_realm")
    );

    @Unique
    private ItemStack[] soul_sanction$savedInventory = null;

    @Inject(method = "moveToWorld", at = @At("HEAD"))
    private void onMoveToWorld(ServerWorld destination, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        RegistryKey<World> fromDim = player.getWorld().getRegistryKey();
        RegistryKey<World> toDim = destination.getRegistryKey();

        if (!fromDim.equals(VOIDIN_REALM) && toDim.equals(VOIDIN_REALM)) {
            saveAndClearInventory(player);
            giveTorches(player);
        } else if (fromDim.equals(VOIDIN_REALM) && !toDim.equals(VOIDIN_REALM)) {
            restoreInventory(player);
        }
    }

    @Unique
    private void saveAndClearInventory(ServerPlayerEntity player) {
        PlayerInventory inv = player.getInventory();
        soul_sanction$savedInventory = new ItemStack[inv.size()];
        for (int i = 0; i < inv.size(); i++) {
            soul_sanction$savedInventory[i] = inv.getStack(i).copy();
            inv.setStack(i, ItemStack.EMPTY);
        }
    }

    @Unique
    private void giveTorches(ServerPlayerEntity player) {
        PlayerInventory inv = player.getInventory();
        inv.setStack(0, new ItemStack(Items.TORCH, 8));
    }

    @Unique
    private void restoreInventory(ServerPlayerEntity player) {
        if (soul_sanction$savedInventory == null) return;
        PlayerInventory inv = player.getInventory();
        for (int i = 0; i < soul_sanction$savedInventory.length; i++) {
            inv.setStack(i, soul_sanction$savedInventory[i]);
        }
        soul_sanction$savedInventory = null;
    }
}
