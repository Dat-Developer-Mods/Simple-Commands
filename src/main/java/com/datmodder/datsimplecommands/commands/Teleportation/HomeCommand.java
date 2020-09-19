package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.delayedevents.teleportation.DelayedTeleport;
import com.datmodder.datsimplecommands.utils.PlayerManager;
import com.datmodder.datsimplecommands.utils.SimpleConfig;
import com.datmodder.datsimplecommands.utils.structures.PlayerData;
import com.demmodders.datmoddingapi.delayedexecution.DelayHandler;
import com.demmodders.datmoddingapi.util.DemConstants;
import com.demmodders.datmoddingapi.util.Permissions;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class HomeCommand extends CommandBase {
    @Override
    public String getName() {
        return "dathome";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/home [Home Name]" + DemConstants.TextColour.INFO + " - Teleport to your home";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String message = "";
        if (sender instanceof EntityPlayerMP) {
            String homeName = (args.length > 0 ? args[0].toLowerCase() : "default");
            if (Permissions.checkPermission(sender, "datsimplecommands.teleportation.homemultiple", 2) || (homeName.equals("default") && Permissions.checkPermission(sender, "datsimplecommands.teleportation.home", getRequiredPermissionLevel()))) {
                PlayerData player = PlayerManager.getInstance().getPlayer(((EntityPlayerMP) sender).getUniqueID());

                if (player.homeLocations.containsKey(homeName)) {
                    DelayHandler.addEvent(new DelayedTeleport(player.homeLocations.get(homeName), (EntityPlayerMP) sender, SimpleConfig.TELEPORTATION.teleportationDelay));
                    message = DemConstants.TextColour.INFO + "Teleporting to your home in " + SimpleConfig.TELEPORTATION.teleportationDelay + " seconds";
                } else {
                    message = DemConstants.TextColour.ERROR + (homeName.equals("default") ? "You do not have a home" : "That home doesn't exist");
                }
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
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("home");
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibilities;
        if (args.length == 1) {
            possibilities = new ArrayList<>(PlayerManager.getInstance().getPlayer(((EntityPlayerMP) sender).getUniqueID()).homeLocations.keySet());
        } else {
            possibilities = super.getTabCompletions(server, sender, args, targetPos);
        }
        return possibilities;
    }
}
