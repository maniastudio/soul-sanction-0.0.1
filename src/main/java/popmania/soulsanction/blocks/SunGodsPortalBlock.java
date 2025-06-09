package popmania.soulsanction.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SunGodsPortalBlock extends Block {

    public SunGodsPortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer) {
            ServerWorld overworld = serverPlayer.getServer().getOverworld();
            BlockPos targetPos = serverPlayer.getSpawnPointPosition();

            if (targetPos == null) {
                targetPos = overworld.getSpawnPos(); // fallback to world spawn
            }

            serverPlayer.teleport(overworld,
                    targetPos.getX() + 0.5,
                    targetPos.getY(),
                    targetPos.getZ() + 0.5,
                    serverPlayer.getYaw(),
                    serverPlayer.getPitch());

            overworld.playSound(null, targetPos, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        return ActionResult.SUCCESS;
    }
}