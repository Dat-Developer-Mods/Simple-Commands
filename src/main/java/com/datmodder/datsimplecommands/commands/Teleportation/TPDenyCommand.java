package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.utils.PlayerManager;
import com.datmodder.datsimplecommands.utils.structures.PlayerData;
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

public class TPDenyCommand extends CommandBase {
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
        String message = "";
        if (sender instanceof EntityPlayerMP) {
            if (Permissions.checkPermission(sender, "datsimplecommands.teleportation.tpdeny", getRequiredPermissionLevel())) {
                UUID senderID = ((EntityPlayerMP) sender).getUniqueID();
                PlayerData player = PlayerManager.getInstance().getPlayer(senderID);
                if (args.length > 0) {
                    EntityPlayerMP otherPlayerEntity = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[0]);
                    if (otherPlayerEntity != null){
                        UUID otherPlayerID = otherPlayerEntity.getUniqueID();
                        if (player.tpaRequests.containsKey(otherPlayerID)) {
                            player.tpaRequests.remove(otherPlayerID);
                            otherPlayerEntity.sendMessage(new TextComponentString(DemConstants.TextColour.ERROR + sender.getName() + " denied your teleport request"));
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
                            otherPlayer.sendMessage(new TextComponentString(DemConstants.TextColour.ERROR + sender.getName() + " denied your teleport request"));
                        }
                        player.tpaRequests.clear();
                        message = DemConstants.TextColour.INFO + "Denied all teleport requests";
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
        aliases.add("tpdeny");
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
