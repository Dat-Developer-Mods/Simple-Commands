package com.datmodder.datsimplecommands.commands.Teleportation;

import com.demmodders.datmoddingapi.util.DemConstants;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TPAcceptCommand extends CommandBase {
    @Override
    public String getName() {
        return "dattpaccept";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/tpaccept [player] " + DemConstants.TextColour.INFO + " - Accept a request to teleport from another player";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

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
        aliases.add("tpaccept");
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
