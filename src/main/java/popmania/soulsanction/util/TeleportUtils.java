package popmania.soulsanction.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class TeleportUtils {

    public static final RegistryKey<World> VOID_REALM_KEY = RegistryKey.of(
            RegistryKeys.WORLD, // NOT RegistryKeys.DIMENSION
            new Identifier("soul-sanction", "void_realm")
    );

    public static void teleportPlayerToVoidRealm(ServerPlayerEntity player) {
        ServerWorld targetWorld = player.getServer().getWorld(VOID_REALM_KEY);
        if (targetWorld != null) {
            player.teleport(targetWorld, 0.5, 118, 0.5, player.getYaw(), player.getPitch());
        }
    }
}
