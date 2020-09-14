package com.datmodder.datsimplecommands;

import com.datmodder.datsimplecommands.commands.CommandRegister;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
        modid = SimpleCommands.MOD_ID,
        name = SimpleCommands.MOD_NAME,
        version = SimpleCommands.VERSION,
        acceptableRemoteVersions = "*",
        serverSideOnly = true
)
public class SimpleCommands {

    public static final String MOD_ID = "simplecommands";
    public static final String MOD_NAME = "Simple Commands";
    public static final String VERSION = "1.0.0";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static SimpleCommands INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Register permissions
        CommandRegister.registerPermissions();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent e){
        // register commands
        CommandRegister.registerCommands(e);
    }
}
