package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.delayedevents.teleportation.DelayedTeleport;
import com.datmodder.datsimplecommands.utils.PlayerManager;
import com.datmodder.datsimplecommands.utils.SimpleConfig;
import com.datmodder.datsimplecommands.utils.enums.TPDirection;
import com.datmodder.datsimplecommands.utils.structures.PlayerData;
import com.demmodders.datmoddingapi.delayedexecution.DelayHandler;
import com.demmodders.datmoddingapi.structures.Location;
import com.demmodders.datmoddingapi.util.DemConstants;
import com.demmodders.datmoddingapi.util.Permissions;
import net.minecraft.command.CommandBase;
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

public class TPAcceptCommand extends CommandBase {
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
        String message = "";
        if (sender instanceof EntityPlayerMP) {
            if (Permissions.checkPermission(sender, "datsimplecommands.teleportation.tpaccept", getRequiredPermissionLevel())) {
                UUID senderID = ((EntityPlayerMP) sender).getUniqueID();
                PlayerData player = PlayerManager.getInstance().getPlayer(senderID);
                if (args.length > 0) {
                    EntityPlayerMP otherPlayerEntity = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[0]);
                    if (otherPlayerEntity != null){
                        UUID otherPlayerID = otherPlayerEntity.getUniqueID();
                        if (player.tpaRequests.containsKey(otherPlayerID)) {
                            TPDirection dir = player.tpaRequests.get(otherPlayerID);
                            teleport(dir, otherPlayerEntity, (EntityPlayerMP) sender);
                            player.tpaRequests.remove(otherPlayerID);
                            String otherPlayerMessage = "";
                            if (dir == TPDirection.TOASKEE) {
                                message = DemConstants.TextColour.INFO + "Teleporting to " + otherPlayerEntity.getName() + " in " + SimpleConfig.TELEPORTATION.teleportationDelay + " seconds";
                                otherPlayerMessage = sender.getName() + DemConstants.TextColour.INFO + " Accepted your teleport request";
                            } else {
                                message = DemConstants.TextColour.INFO +"Accepted Teleport request";
                                otherPlayerMessage = DemConstants.TextColour.INFO + "Teleporting to " + sender.getName() + " in " + SimpleConfig.TELEPORTATION.teleportationDelay + " seconds";
                            }
                            otherPlayerEntity.sendMessage(new TextComponentString(otherPlayerMessage));
                        } else {
                            message = DemConstants.TextColour.ERROR + "You don't have a teleport request from that player";
                        }
                    } else {
                        message = DemConstants.TextColour.ERROR + "Unknown player";
                    }
                } else {
                    if (!player.tpaRequests.isEmpty()) {
                        PlayerList players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
                        for (UUID id : player.tpaRequests.keySet()) {
                            EntityPlayerMP otherPlayer = players.getPlayerByUUID(id);
                            teleport(player.tpaRequests.get(id), otherPlayer, (EntityPlayerMP) sender);

                            String otherPlayerMessage = "";
                            if (player.tpaRequests.get(id) == TPDirection.TOASKEE) {
                                otherPlayerMessage = DemConstants.TextColour.INFO + "Teleporting to " + sender.getName() + " in " + SimpleConfig.TELEPORTATION.teleportationDelay + " seconds";
                            } else {
                                otherPlayerMessage = DemConstants.TextColour.INFO + sender.getName() + " accepted your teleport request";
                            }
                            otherPlayer.sendMessage(new TextComponentString(otherPlayerMessage));
                        }
                        player.tpaRequests.clear();
                        message = DemConstants.TextColour.INFO + "Accepted all teleport requests";
                    } else {
                        message = DemConstants.TextColour.ERROR + "You don't have any teleport requests";
                    }
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
        aliases.add("tpaccept");
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibilities;
        if (args.length == 1) {
            possibilities = new ArrayList<>();
            PlayerList players = server.getPlayerList();
            for (UUID id : PlayerManager.getInstance().getPlayer(((EntityPlayerMP) sender).getUniqueID()).tpaRequests.keySet()) {
                EntityPlayerMP player = players.getPlayerByUUID(id);
                if (player != null) possibilities.add(player.getName());
            }
        } else {
            possibilities = super.getTabCompletions(server, sender, args, targetPos);
        }
        return getListOfStringsMatchingLastWord(args, possibilities);
    }
}
