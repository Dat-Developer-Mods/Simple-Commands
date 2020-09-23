package com.datmodder.datsimplecommands.commands.Information;

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

public class RulesCommands extends CommandBase {
    @Override
    public String getName() {
        return "datrules";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/rules" + DemConstants.TextColour.INFO + " - Display the rules of the server";
    }

    private String getRulesPage(int Page) {
        String[] rules = SimpleConfig.INFORMATION.ruleArray;
        int pageOffset = (Page - 1) * SimpleConfig.INFORMATION.rulesPerPage;

        if (pageOffset > rules.length) return DemConstants.TextColour.ERROR + "There aren't that many pages";

        StringBuilder ruleMessage = new StringBuilder();
        if (rules.length > SimpleConfig.INFORMATION.rulesPerPage) ruleMessage.append(DemConstants.TextColour.HEADER).append("Page ").append(Page).append(" of ").append(rules.length / SimpleConfig.INFORMATION.rulesPerPage).append("\n");
        ruleMessage.append(DemConstants.TextColour.INFO).append(pageOffset + 1).append(". ").append(rules[pageOffset]);

        for (int i = pageOffset + 1; i < rules.length; i++) {
            ruleMessage.append("\n").append(DemConstants.TextColour.INFO).append(i + 1).append(". ").append(rules[i]);
        }

        return ruleMessage.toString();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String message = "";
        if (Permissions.checkPermission(sender, "datsimplecommands.information.rules", getRequiredPermissionLevel())) {
            message = getRulesPage((args.length > 0 ? Integer.parseInt(args[0]) : 1));
        } else {
            message = DemConstants.TextColour.ERROR + "You don't have permission to do that";
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
        aliases.add("rules");
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
