package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.capabilities.CommandsProvider;
import com.datmodder.datsimplecommands.capabilities.ICommands;
import com.datmodder.datsimplecommands.delayedevents.teleportation.DelayedTeleport;
import com.datmodder.datsimplecommands.utils.SimpleConfig;
import com.demmodders.datmoddingapi.commands.DatCommandBase;
import com.demmodders.datmoddingapi.delayedexecution.DelayHandler;
import com.demmodders.datmoddingapi.util.DemConstants;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BackCommand extends DatCommandBase {
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
        if (!(sender instanceof EntityPlayerMP)) {
            sendError(sender, "You must be a player to use this command");
            return;
        }
        ICommands command = ((EntityPlayerMP) sender).getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);
        if (command.getBackLocation() == null) {
            sendError(sender, "You don't have a location to go back to");
            return;
        }

        long expectedTime = command.getLastTeleport() + ((long) SimpleConfig.TELEPORTATION.teleportationCoolDown * 1000L);
        if (expectedTime >= System.currentTimeMillis()) {
            sendError(sender, "You must wait " + (int) ((expectedTime - System.currentTimeMillis()) / 1000) + " seconds before you can do that");
            return;
        }

        DelayHandler.addEvent(new DelayedTeleport(command.getBackLocation(), (EntityPlayerMP) sender, SimpleConfig.TELEPORTATION.teleportationDelay));
        sendInfo(sender, "Teleporting to your last location in " + SimpleConfig.TELEPORTATION.teleportationDelay + " seconds");
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("back");
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return super.getTabCompletions(server, sender, args, targetPos);
    }

    @Override
    protected String getPermissionNode() {
        return "datsimplecommands.teleportation.back";
    }
}
