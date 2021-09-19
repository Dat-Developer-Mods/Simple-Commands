package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.capabilities.CommandsProvider;
import com.datmodder.datsimplecommands.capabilities.ICommands;
import com.datmodder.datsimplecommands.delayedevents.teleportation.TeleportRequest;
import com.datmodder.datsimplecommands.utils.SimpleConfig;
import com.datmodder.datsimplecommands.utils.enums.TPDirection;
import com.demmodders.datmoddingapi.commands.DatCommandBase;
import com.demmodders.datmoddingapi.delayedexecution.DelayHandler;
import com.demmodders.datmoddingapi.util.DemConstants;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TPAHereCommand extends DatCommandBase {
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
        if (!(sender instanceof EntityPlayerMP)) {
            sendError(sender, "You must be a player to use this command");
            return;
        }

        if (args.length <= 0) {
            sendError(sender, "Bad arguments, see usage: " + getUsage(sender));
            return;
        }

        UUID senderID = ((EntityPlayerMP) sender).getUniqueID();
        EntityPlayerMP otherPlayerEntity = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[0]);

        if (otherPlayerEntity == null) {
            sendError(sender, "Unknown player");
            return;
        }

        ICommands otherCommands = otherPlayerEntity.getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);

        if (otherCommands.getTpaRequests().containsKey(senderID)) {
            sendError(sender, "That player already has a teleport request from you");
            return;
        }

        otherCommands.putTpaRequest(senderID, TPDirection.TOASKER);
        sendMessage(otherPlayerEntity, new TextComponentString(DemConstants.TextColour.INFO + "You have received a tpahere request from " + sender.getName() + ". Accept it with " + DemConstants.TextColour.COMMAND + "/tpaccept " + DemConstants.TextColour.INFO + "or deny it with " + DemConstants.TextColour.COMMAND + "/tpdeny"));
        DelayHandler.addEvent(new TeleportRequest(SimpleConfig.TELEPORTATION.tpaTimeout, otherPlayerEntity, senderID));
        sendInfo(sender, "Teleport here request sent");
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("tpahere");
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibilities;
        if (args.length == 1) {
            possibilities = new ArrayList<>(Arrays.asList(server.getOnlinePlayerNames()));
            possibilities.remove(sender.getName());
        } else {
            possibilities = super.getTabCompletions(server, sender, args, targetPos);
        }
        return getListOfStringsMatchingLastWord(args, possibilities);
    }

    @Override
    protected String getPermissionNode() {
        return "datsimplecommands.teleportation.tpahere";
    }
}
