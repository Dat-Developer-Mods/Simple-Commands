package com.datmodder.datsimplecommands.delayedevents;

import com.datmodder.datsimplecommands.utils.PlayerManager;
import com.demmodders.datmoddingapi.delayedexecution.delayedevents.DelayedTeleportEvent;
import com.demmodders.datmoddingapi.structures.Location;
import net.minecraft.entity.player.EntityPlayerMP;

public class DelayedTeleport extends DelayedTeleportEvent {
    public DelayedTeleport(Location Destination, EntityPlayerMP Player, int Delay) {
        super(Destination, Player, Delay);
    }

    @Override
    public void execute() {
        PlayerManager.getInstance().getPlayer(player.getUniqueID()).backLocation = new Location(player.dimension, player.posX, player.posY, player.posZ, player.cameraPitch, player.cameraYaw);
        super.execute();
    }
}
