package net.radsteve.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;


public class DeathTPCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("deathtp")
                .executes(DeathTPCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity p = context.getSource().getPlayer();
        if(p.getLastDeathPos().isEmpty()) {
            context.getSource().sendMessage(Text.literal("You did not die yet!"));
            return 0;
        }
        GlobalPos deathPos = p.getLastDeathPos().orElse(null);

        if (deathPos != null) {
            RegistryKey<World> dimensionKey = deathPos.getDimension();
            ServerWorld world = p.server.getWorld(dimensionKey);

            if (world != null) {
                double x = deathPos.getPos().getX();
                double y = deathPos.getPos().getY();
                double z = deathPos.getPos().getZ();
                p.teleport(world, x, y, z, p.getPitch(), p.getPitch());
            }
        }
        return 0;
    }
}