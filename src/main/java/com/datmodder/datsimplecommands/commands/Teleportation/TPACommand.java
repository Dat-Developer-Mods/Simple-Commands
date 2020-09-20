package com.datmodder.datsimplecommands.commands.Teleportation;

import com.datmodder.datsimplecommands.delayedevents.teleportation.TeleportRequest;
import com.datmodder.datsimplecommands.utils.PlayerManager;
import com.datmodder.datsimplecommands.utils.SimpleConfig;
import com.datmodder.datsimplecommands.utils.enums.TPDirection;
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
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TPACommand extends CommandBase {
    @Override
    public String getName() {
        return "dattpa";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return DemConstants.TextColour.COMMAND + "/tpa <player> " + DemConstants.TextColour.INFO + " - Request to teleport to a player";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String message = "";
        if (sender instanceof EntityPlayerMP) {
            if (Permissions.checkPermission(sender, "datsimplecommands.teleportation.tpa", getRequiredPermissionLevel())) {
                if (args.length > 0) {
                    UUID senderID = ((EntityPlayerMP) sender).getUniqueID();
                    EntityPlayerMP otherPlayerEntity = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[0]);

                    if (otherPlayerEntity != null){
                        PlayerData otherPlayer = PlayerManager.getInstance().getPlayer(otherPlayerEntity.getUniqueID());
                        if (!otherPlayer.tpaRequests.containsKey(senderID)){
                            otherPlayer.tpaRequests.put(senderID, TPDirection.TOASKEE);
                            otherPlayerEntity.sendMessage(new TextComponentString(DemConstants.TextColour.INFO + "You have recieved a tpa request from " + sender.getName() + ". Accept it with " + DemConstants.TextColour.COMMAND + "/tpaccept " + DemConstants.TextColour.INFO + "or deny it with " + DemConstants.TextColour.COMMAND + "/tpdeny"));
                            DelayHandler.addEvent(new TeleportRequest(SimpleConfig.TELEPORTATION.tpaTimeout, otherPlayer, senderID));
                            message = DemConstants.TextColour.INFO + "Teleport request sent";
                        } else {
                            message = DemConstants.TextColour.ERROR + "That player already has a teleport request from you";
                        }
                    } else {
                        message = DemConstants.TextColour.ERROR + "Unknown player";
                    }
                } else {
                    message = DemConstants.TextColour.ERROR + "Bad arguments, see usage: " + getUsage(sender);
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
        aliases.add("tpa");
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
}
