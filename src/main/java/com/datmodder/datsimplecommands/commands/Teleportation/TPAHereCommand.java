package com.datmodder.datsimplecommands.commands.Teleportation;

import com.demmodders.datmoddingapi.util.DemConstants;
import jdk.internal.jline.internal.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

public class TPAHereCommand extends CommandBase {
    @Override
    public String getName() {
        return "dattpahere";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/tpahere <player> " + DemConstants.TextColour.INFO + " - Request to teleport a player to you";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = super.getAliases();
        aliases.add("tpahere");
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibilities;
        if (args.length == 1) {
            possibilities = Arrays.asList(server.getOnlinePlayerNames());
        } else {
            possibilities = super.getTabCompletions(server, sender, args, targetPos);
        }
        return possibilities;
    }
}
