package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.capabilities.CommandsProvider;
import com.datmodder.datsimplecommands.capabilities.ICommands;
import com.datmodder.datsimplecommands.utils.SimpleConfig;
import com.demmodders.datmoddingapi.commands.DatCommandBase;
import com.demmodders.datmoddingapi.structures.Location;
import com.demmodders.datmoddingapi.util.DemConstants;
import com.demmodders.datmoddingapi.util.Permissions;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class SetHomeCommand extends DatCommandBase {
    @Override
    public String getName() {
        return "datsethome";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/sethome [Home Name]" + DemConstants.TextColour.INFO + " - Sets your home to your location";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String message = "";
        if (!(sender instanceof EntityPlayerMP)) {
            sendError(sender,  "You must be a player to use this command");
            return;
        }

        String homeName = (args.length > 0 ? args[0].toLowerCase() : "default");

        ICommands commands = ((EntityPlayerMP) sender).getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);
        if (homeName.equals("default") || (commands.getHomeLocations().containsKey(homeName) || (Permissions.checkPermission(sender, "datsimplecommands.teleportation.homemultiple", 2) && (commands.getHomeLocations().size() < SimpleConfig.TELEPORTATION.maxHomes - 1)))) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;
            commands.putHomeLocation(homeName, new Location(entityPlayerMP.dimension, entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.cameraPitch, entityPlayerMP.cameraYaw));

            sendInfo(sender, "Set your home to your location");
        } else {
            sendError(sender, "You aren't allowed any more homes");
            return;
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("sethome");
        return aliases;
    }

    @Override
    protected String getPermissionNode() {
        return "datsimplecommands.teleportation.home";
    }
}
