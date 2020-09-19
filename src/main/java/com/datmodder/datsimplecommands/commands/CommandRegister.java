package com.datmodder.datsimplecommands.commands;

import com.datmodder.datsimplecommands.commands.Teleportation.*;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class CommandRegister {
    public static void registerPermissions() {
        PermissionAPI.registerNode("datsimplecommands.teleportation.back", DefaultPermissionLevel.ALL, "Teleports you back to your last location");
        PermissionAPI.registerNode("datsimplecommands.teleportation.home", DefaultPermissionLevel.ALL, "Teleports you back to your home");
        PermissionAPI.registerNode("datsimplecommands.teleportation.homemultiple", DefaultPermissionLevel.OP, "Teleports you back to your named home");
        PermissionAPI.registerNode("datsimplecommands.teleportation.tpa", DefaultPermissionLevel.ALL, "Request to teleport to someone");
        PermissionAPI.registerNode("datsimplecommands.teleportation.tpahere", DefaultPermissionLevel.ALL, "Request to teleport someone to you");
        PermissionAPI.registerNode("datsimplecommands.teleportation.tpaccept", DefaultPermissionLevel.ALL, "Accept a teleport request");
        PermissionAPI.registerNode("datsimplecommands.teleportation.tpdeny", DefaultPermissionLevel.ALL, "Deny a teleport request");
    }

    public static void registerCommands(FMLServerStartingEvent e) {
        e.registerServerCommand(new BackCommand());
        e.registerServerCommand(new HomeCommand());
        e.registerServerCommand(new SetHomeCommand());
        e.registerServerCommand(new TPACommand());
        e.registerServerCommand(new TPAHereCommand());
        e.registerServerCommand(new TPAcceptCommand());
        e.registerServerCommand(new TPDenyCommand());
    }
}
