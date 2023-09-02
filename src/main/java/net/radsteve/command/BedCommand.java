package net.radsteve.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BedCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        final LiteralCommandNode<ServerCommandSource> bedCommandNode = dispatcher.register(CommandManager.literal("bed")
                .executes(BedCommand::run));
        dispatcher.register(CommandManager.literal("b")
                .redirect(bedCommandNode));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity p = context.getSource().getPlayer();

        BlockPos bedPos = p.getSpawnPointPosition();
        RegistryKey<World> bedDimension = p.getSpawnPointDimension();

        if (bedPos != null && bedDimension != null) {
            // The player has a bed spawn point set, and the dimension is known
            // Set the player's spawn point to their bed position
            //p.setSpawnPoint(bedDimension, bedPos, 0, true, false);

            // Teleport the player to their bed spawn point in the correct dimension
            p.teleport(p.server.getWorld(bedDimension), bedPos.getX() + 0.5, bedPos.getY() + 0.1, bedPos.getZ() + 0.5, p.getPitch(), p.getYaw());
        }

        return 0;
    }
}
