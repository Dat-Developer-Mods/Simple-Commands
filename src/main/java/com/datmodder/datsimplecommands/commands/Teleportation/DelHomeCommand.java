package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.capabilities.CommandsProvider;
import com.datmodder.datsimplecommands.capabilities.ICommands;
import com.demmodders.datmoddingapi.commands.DatCommandBase;
import com.demmodders.datmoddingapi.util.DemConstants;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DelHomeCommand extends DatCommandBase {
    @Override
    public String getName() {
        return "datdelhome";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/delhome [Home Name]" + DemConstants.TextColour.INFO + " - Teleport to your home";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            sendError(sender, "You must be a player to use this command");
            return;
        }

        String homeName = (args.length > 0 ? args[0].toLowerCase() : "default");
        ICommands commands = ((EntityPlayerMP) sender).getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);

        if (commands.getHomeLocations().containsKey(homeName)) {
            commands.getHomeLocations().remove(homeName);
            sendInfo(sender, "Successfully removed home");
        } else {
            sendError(sender, "That home doesn't exist");
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("delhome");
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibilities;
        if (args.length == 1 && sender instanceof EntityPlayerMP) {
            possibilities = new ArrayList<>(((EntityPlayerMP) sender).getCapability(CommandsProvider.COMMANDS_CAPABILITY, null).getHomeLocations().keySet());
        } else {
            possibilities = super.getTabCompletions(server, sender, args, targetPos);
        }
        return getListOfStringsMatchingLastWord(args, possibilities);
    }

    @Override
    protected String getPermissionNode() {
        return "datsimplecommands.teleportation.delhome";
    }
}
