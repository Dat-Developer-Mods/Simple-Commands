package com.datmodder.datsimplecommands.delayedevents.teleportation;

import com.datmodder.datsimplecommands.capabilities.CommandsProvider;
import com.datmodder.datsimplecommands.capabilities.ICommands;
import com.demmodders.datmoddingapi.delayedexecution.delayedevents.BaseDelayedEvent;
import com.demmodders.datmoddingapi.util.DemConstants;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class TeleportRequest extends BaseDelayedEvent {
    private EntityPlayerMP player;
    private UUID teleportRequester;

    public TeleportRequest(int Delay, EntityPlayerMP Player, UUID TeleportRequester) {
        super(Delay);
        this.player = Player;
        teleportRequester = TeleportRequester;
    }

    @Override
    public void execute() {
        ICommands commands = player.getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);

        if (commands.getTpaRequests().containsKey(teleportRequester)) {
            commands.getTpaRequests().remove(teleportRequester);
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
        ICommands commands = player.getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);

        return super.shouldRequeue(hasFinished) && commands.getTpaRequests().containsKey(teleportRequester) && FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(teleportRequester) != null;
    }
}
