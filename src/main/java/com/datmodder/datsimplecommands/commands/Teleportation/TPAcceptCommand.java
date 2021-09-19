package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.capabilities.CommandsProvider;
import com.datmodder.datsimplecommands.capabilities.ICommands;
import com.datmodder.datsimplecommands.delayedevents.teleportation.DelayedTeleport;
import com.datmodder.datsimplecommands.utils.SimpleConfig;
import com.datmodder.datsimplecommands.utils.enums.TPDirection;
import com.demmodders.datmoddingapi.commands.DatCommandBase;
import com.demmodders.datmoddingapi.delayedexecution.DelayHandler;
import com.demmodders.datmoddingapi.structures.Location;
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

public class TPAcceptCommand extends DatCommandBase {
    @Override
    public String getName() {
        return "dattpaccept";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/tpaccept [player] " + DemConstants.TextColour.INFO + " - Accept a request to teleport from another player";
    }

    public void teleport(TPDirection Direction, EntityPlayerMP Asker, EntityPlayerMP Askee) {
        EntityPlayerMP teleporter;
        EntityPlayerMP destination;
        if (Direction == TPDirection.TOASKEE) {
            destination = Askee;
            teleporter = Asker;
        } else {
            destination = Asker;
            teleporter = Askee;
        }

        DelayHandler.addEvent(new DelayedTeleport(new Location(destination.dimension, destination.posX, destination.posY, destination.posZ, destination.cameraPitch, destination.cameraYaw), teleporter, SimpleConfig.TELEPORTATION.teleportationDelay));
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            sendError(sender, "You must be a player to use this command");
            return;
        }

        UUID senderID = ((EntityPlayerMP) sender).getUniqueID();
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

            TPDirection dir = commands.getTpaRequest(otherPlayerID);
            teleport(dir, otherPlayerEntity, (EntityPlayerMP) sender);
            commands.getTpaRequests().remove(otherPlayerID);

            if (dir == TPDirection.TOASKEE) {
                sendMessage(sender,  new TextComponentString("Teleporting to " + DemConstants.playerColour.ONLINE + otherPlayerEntity.getName() + DemConstants.TextColour.INFO + " in " + SimpleConfig.TELEPORTATION.teleportationDelay + " seconds"));
                sendMessage(otherPlayerEntity, new TextComponentString(DemConstants.playerColour.ONLINE + sender.getName() + DemConstants.TextColour.INFO + " Accepted your teleport request"));
            } else {
                sendInfo(sender, "Accepted Teleport request");
                sendMessage(otherPlayerEntity, new TextComponentString(DemConstants.TextColour.INFO + "Teleporting to " + DemConstants.playerColour.ONLINE + sender.getName() + DemConstants.TextColour.INFO + " in " + SimpleConfig.TELEPORTATION.teleportationDelay + " seconds"));
            }
        } else {
            if (commands.getTpaRequests().isEmpty()) {
                sendError(sender, "You don't have any teleport requests");
                return;
            }

            PlayerList players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
            for (UUID id : commands.getTpaRequests().keySet()) {
                EntityPlayerMP otherPlayer = players.getPlayerByUUID(id);
                teleport(commands.getTpaRequest(id), otherPlayer, (EntityPlayerMP) sender);

                if (commands.getTpaRequest(id) == TPDirection.TOASKEE) {
                    sendMessage(otherPlayer, new TextComponentString(DemConstants.TextColour.INFO + "Teleporting to " + DemConstants.playerColour.ONLINE + sender.getName() + DemConstants.TextColour.INFO + " in " + SimpleConfig.TELEPORTATION.teleportationDelay + " seconds"));
                } else {
                    sendMessage(otherPlayer, new TextComponentString(DemConstants.playerColour.ONLINE + sender.getName() + DemConstants.TextColour.INFO + " accepted your teleport request"));
                }
            }
            commands.getTpaRequests().clear();
            sendInfo(sender, "Accepted all teleport requests");
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
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
        return "datsimplecommands.teleportation.tpaccept";
    }
}
