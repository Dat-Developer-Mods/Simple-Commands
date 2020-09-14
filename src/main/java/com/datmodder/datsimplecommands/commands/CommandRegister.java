package com.datmodder.datsimplecommands.commands;

import com.datmodder.datsimplecommands.commands.Teleportation.BackCommand;
import com.datmodder.datsimplecommands.commands.Teleportation.HomeCommand;
import com.datmodder.datsimplecommands.commands.Teleportation.SetHomeCommand;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class CommandRegister {
    public static void registerPermissions() {
        PermissionAPI.registerNode("datsimplecommands.teleportation.back", DefaultPermissionLevel.ALL, "Teleports you back to your last location");
        PermissionAPI.registerNode("datsimplecommands.teleportation.home", DefaultPermissionLevel.ALL, "Teleports you back to your home");
        PermissionAPI.registerNode("datsimplecommands.teleportation.homemultiple", DefaultPermissionLevel.ALL, "Teleports you back to your named home");
    }

    public static void registerCommands(FMLServerStartingEvent e) {
        e.registerServerCommand(new BackCommand());
        e.registerServerCommand(new HomeCommand());
        e.registerServerCommand(new SetHomeCommand());
    }
}
