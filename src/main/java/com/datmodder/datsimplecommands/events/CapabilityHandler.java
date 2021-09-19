package com.datmodder.datsimplecommands.events;

import com.datmodder.datsimplecommands.SimpleCommands;
import com.datmodder.datsimplecommands.capabilities.CommandsProvider;
import com.datmodder.datsimplecommands.capabilities.ICommands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = SimpleCommands.MOD_ID)
public class CapabilityHandler {
    public static final ResourceLocation RESPAWN_CAPABILITY = new ResourceLocation(SimpleCommands.MOD_ID, "command");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof EntityPlayer)) return;

        event.addCapability(RESPAWN_CAPABILITY, new CommandsProvider());
    }

    @SubscribeEvent
    public static void playerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone e) {
        if (e.isWasDeath()) {

            ICommands respawn = e.getEntityPlayer().getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);
            ICommands oldRespawn = e.getOriginal().getCapability(CommandsProvider.COMMANDS_CAPABILITY, null);

            respawn.set(oldRespawn);
        }
    }
}