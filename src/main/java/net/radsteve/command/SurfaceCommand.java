package net.radsteve.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class SurfaceCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("surface")
                .executes(SurfaceCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity p = context.getSource().getPlayer();
        Vec3d pos = p.getPos();
        ServerWorld world = p.getServerWorld();

        int x = (int) Math.round(pos.x);
        int y = (int) Math.round(pos.y);
        int z = (int) Math.round(pos.z);

        BlockPos blockPos = new BlockPos(new Vec3i(x, y, z));

        BlockPos surfacePos = findSurface(world, new BlockPos(blockPos), 10);

        if (surfacePos != null) {
            p.teleport(surfacePos.getX() + 0.5, surfacePos.getY() + 1.0, surfacePos.getZ() + 0.5);
        }

        return 0;
    }

    private static BlockPos findSurface(ServerWorld world, BlockPos start, int maxAirBlocksAbove) {
        int maxSearchHeight = 319; // Adjust this value as needed

        for (int y = start.getY(); y < maxSearchHeight; y++) {
            boolean foundAir = true;

            for (int yOffset = 1; yOffset <= maxAirBlocksAbove; yOffset++) {
                BlockPos pos = new BlockPos(start.getX(), y + yOffset, start.getZ());

                if (!world.isAir(pos)) {
                    foundAir = false;
                    break;
                }
            }

            if (foundAir) {
                return new BlockPos(start.getX(), y, start.getZ());
            }
        }

        return null; // No suitable location found
    }
}
