package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.delayedevents.DelayedTeleport;
import com.datmodder.datsimplecommands.utils.PlayerManager;
import com.datmodder.datsimplecommands.utils.SimpleConfig;
import com.datmodder.datsimplecommands.utils.structures.PlayerData;
import com.demmodders.datmoddingapi.delayedexecution.DelayHandler;
import com.demmodders.datmoddingapi.structures.Location;
import com.demmodders.datmoddingapi.util.DemConstants;
import com.demmodders.datmoddingapi.util.Permissions;
import jdk.internal.jline.internal.Nullable;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.Arrays;
import java.util.List;

public class SetHomeCommand extends CommandBase {
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
        if (sender instanceof EntityPlayerMP) {
            String homeName = (args.length > 0 ? args[0].toLowerCase() : "default");
            if (Permissions.checkPermission(sender, "datsimplecommands.teleportation.homemultiple", getRequiredPermissionLevel()) || (homeName.equals("default") && Permissions.checkPermission(sender, "datsimplecommands.teleportation.home", getRequiredPermissionLevel()))) {
                PlayerData player = PlayerManager.getInstance().getPlayer(((EntityPlayerMP) sender).getUniqueID());
                EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;
                player.homeLocations.put(homeName, new Location(entityPlayerMP.dimension, entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.cameraPitch, entityPlayerMP.cameraYaw));
                message = DemConstants.TextColour.INFO + "Set your home to your location";
            } else {
                message = DemConstants.TextColour.ERROR + "You don't have permission to do that";
            }
        } else {
            message = DemConstants.TextColour.ERROR + "You must be a player to use this command";
        }

        sender.sendMessage(new TextComponentString(message));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = super.getAliases();
        aliases.add("sethome");
        return aliases;
    }
}
