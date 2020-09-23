package com.datmodder.datsimplecommands.delayedevents.teleportation;

import com.datmodder.datsimplecommands.SimpleCommands;
import com.datmodder.datsimplecommands.utils.PlayerManager;
import com.demmodders.datmoddingapi.delayedexecution.delayedevents.DelayedTeleportEvent;
import com.demmodders.datmoddingapi.structures.Location;
import com.demmodders.datmoddingapi.util.DemConstants;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public class DelayedTeleport extends DelayedTeleportEvent {
    public DelayedTeleport(Location Destination, EntityPlayerMP Player, int Delay) {
        super(Destination, Player, Delay);
    }

    @Override
    public void execute() {
        PlayerManager.getInstance().updatePlayerBackLocation(player.getUniqueID(), new Location(player.dimension, player.posX, player.posY, player.posZ, player.rotationPitch, player.rotationYaw));
        player.sendMessage(new TextComponentString(DemConstants.TextColour.INFO + "Teleporting"));
        SimpleCommands.LOGGER.info("Teleported " + player.getName() + " to " + destination.x + ", " + destination.y + ", " + destination.z);
        super.execute();
    }
}
