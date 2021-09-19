package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.capabilities.CommandsProvider;
import com.datmodder.datsimplecommands.capabilities.ICommands;
import com.demmodders.datmoddingapi.commands.DatCommandBase;
import com.demmodders.datmoddingapi.util.DemConstants;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TPDenyCommand extends DatCommandBase {
    @Override
    public String getName() {
        return "dattpdeny";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/tpdeny [player] " + DemConstants.TextColour.INFO + " - Deny a request to teleport from another player";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            sendError(sender, "You must be a player to use this command");
            return;
        }

        ICommands commands = ((EntityPlayerMP) sender).getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);
        if (args.length > 0) {
            EntityPlayerMP otherPlayerEntity = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[0]);
            if (otherPlayerEntity == null) {
                sendError(sender, "Unknown player");
                return;
            }

            UUID otherPlayerID = otherPlayerEntity.getUniqueID();
            if (!commands.getTpaRequests().containsKey(otherPlayerID)) {
                sendError(sender, "You don't have a teleport request from that player");
                return;
            }

            commands.getTpaRequests().remove(otherPlayerID);
            sendMessage(otherPlayerEntity, new TextComponentString(DemConstants.playerColour.ONLINE + sender.getName() + DemConstants.TextColour.ERROR + " denied your teleport request"));
        } else {
            if (!commands.getTpaRequests().isEmpty()) {
                PlayerList players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
                for (UUID id : commands.getTpaRequests().keySet()) {
                    EntityPlayerMP otherPlayer = players.getPlayerByUUID(id);
                    otherPlayer.sendMessage(new TextComponentString(DemConstants.TextColour.ERROR + sender.getName() + " denied your teleport request"));
                }
                commands.getTpaRequests().clear();
                sendInfo(sender, "Denied all teleport requests");
            } else {
                sendError(sender, "You don't have any teleport requests");
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("tpdeny");
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibilities;
        if (args.length == 1 && sender instanceof EntityPlayerMP) {
            possibilities = new ArrayList<>();

            PlayerList players = server.getPlayerList();
            for (UUID id : ((EntityPlayerMP) sender).getCapability(CommandsProvider.COMMANDS_CAPABILITY, null).getTpaRequests().keySet()) {
                EntityPlayerMP player = players.getPlayerByUUID(id);
                if (player != null) possibilities.add(player.getName());
            }
        } else {
            possibilities = super.getTabCompletions(server, sender, args, targetPos);
        }
        return getListOfStringsMatchingLastWord(args, possibilities);
    }

    @Override
    protected String getPermissionNode() {
        return "datsimplecommands.teleportation.tpdeny";
    }
}
