package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.delayedevents.DelayedTeleport;
import com.datmodder.datsimplecommands.utils.PlayerManager;
import com.datmodder.datsimplecommands.utils.SimpleConfig;
import com.datmodder.datsimplecommands.utils.structures.PlayerData;
import com.demmodders.datmoddingapi.delayedexecution.DelayHandler;
import com.demmodders.datmoddingapi.delayedexecution.delayedevents.DelayedTeleportEvent;
import com.demmodders.datmoddingapi.util.DemConstants;
import com.demmodders.datmoddingapi.util.Permissions;
import jdk.internal.jline.internal.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.List;

public class BackCommand extends CommandBase {
    @Override
    public String getName() {
        return "datback";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/back" + DemConstants.TextColour.INFO + " - Teleport to your last location";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String message = "";
        if (sender instanceof EntityPlayerMP) {
            if (Permissions.checkPermission(sender, "datsimplecommands.teleportation.back", getRequiredPermissionLevel())) {
                PlayerData player = PlayerManager.getInstance().getPlayer(((EntityPlayerMP) sender).getUniqueID());
                if (player.backLocation != null) {
                    long expectedTime = player.lastTeleport + ((long) SimpleConfig.TELEPORTATION.teleportationCoolDown * 1000L);
                    if (expectedTime < System.currentTimeMillis()) {
                        DelayHandler.addEvent(new DelayedTeleport(player.backLocation, (EntityPlayerMP) sender, SimpleConfig.TELEPORTATION.teleportationDelay));
                        message = DemConstants.TextColour.INFO + "Teleporting to your last location in " + SimpleConfig.TELEPORTATION.teleportationDelay + " seconds";
                    } else {
                        message = DemConstants.TextColour.ERROR + "You must wait " + (int) ((System.currentTimeMillis() - expectedTime) / 1000) + " seconds before you can do that";
                    }
                } else {
                    message = DemConstants.TextColour.ERROR + "You don't have a location to go back to";
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
    public List<String> getAliases() {
        List<String> aliases = super.getAliases();
        aliases.add("back");
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
