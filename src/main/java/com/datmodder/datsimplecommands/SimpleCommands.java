package com.datmodder.datsimplecommands;

import com.datmodder.datsimplecommands.commands.CommandRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = SimpleCommands.MOD_ID,
        name = SimpleCommands.MOD_NAME,
        version = SimpleCommands.VERSION,
        acceptableRemoteVersions = "*",
        serverSideOnly = true,
        dependencies = "required-after:datmoddingapi@[1.2.1,)"
)
public class SimpleCommands {

    public static final String MOD_ID = "datsimplecommands";
    public static final String MOD_NAME = "Simple Commands";
    public static final String VERSION = "1.0.2";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

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
