package com.datmodder.datsimplecommands.events;

import com.datmodder.datsimplecommands.capabilities.CommandsProvider;
import com.datmodder.datsimplecommands.capabilities.ICommands;
import com.demmodders.datmoddingapi.structures.Location;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PlayerEvents {
    @SubscribeEvent
    public static void playerDeath(LivingDeathEvent e) {
        if (e.getEntity() instanceof EntityPlayerMP) {
            ICommands commands = ((EntityPlayerMP) e.getEntityLiving()).getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);

            EntityPlayerMP player = (EntityPlayerMP) e.getEntity();
            commands.setBackLocation(new Location(player.dimension, player.posX, player.posY, player.posZ, player.rotationPitch, player.rotationYaw));
        }
    }
}
