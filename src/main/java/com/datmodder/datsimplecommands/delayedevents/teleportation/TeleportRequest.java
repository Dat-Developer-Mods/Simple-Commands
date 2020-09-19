package com.datmodder.datsimplecommands.delayedevents.teleportation;

import com.datmodder.datsimplecommands.utils.structures.PlayerData;
import com.demmodders.datmoddingapi.delayedexecution.delayedevents.BaseDelayedEvent;
import com.demmodders.datmoddingapi.util.DemConstants;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class TeleportRequest extends BaseDelayedEvent {
    private PlayerData player;
    private UUID teleportRequester;

    public TeleportRequest(int Delay, PlayerData Player, UUID TeleportRequester) {
        super(Delay);
        player = Player;
        teleportRequester = TeleportRequester;
    }

    @Override
    public void execute() {
        if (player.tpaRequests.containsKey(teleportRequester)) {
            player.tpaRequests.remove(teleportRequester);
            EntityPlayerMP otherPlayer = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(teleportRequester);
            if (otherPlayer != null) otherPlayer.sendMessage(new TextComponentString(DemConstants.TextColour.ERROR + "Teleport request timed out"));
        }
    }

    @Override
    public boolean canExecute() {
        return super.canExecute();
    }

    @Override
    public boolean shouldRequeue(boolean hasFinished) {
        return super.shouldRequeue(hasFinished) && player.tpaRequests.containsKey(teleportRequester) && FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(teleportRequester) != null;
    }
}
