package net.radsteve.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.radsteve.command.BedCommand;
import net.radsteve.command.DeathTPCommand;
import net.radsteve.command.SurfaceCommand;

public class ModCommandRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(DeathTPCommand::register);
        CommandRegistrationCallback.EVENT.register(SurfaceCommand::register);
        CommandRegistrationCallback.EVENT.register(BedCommand::register);
    }
}
