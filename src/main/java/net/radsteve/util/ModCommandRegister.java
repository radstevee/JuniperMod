package net.radsteve.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.radsteve.command.DeathTPCommand;

public class ModCommandRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(DeathTPCommand::register);
    }
}
