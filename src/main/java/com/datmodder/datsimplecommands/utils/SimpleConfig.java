package com.datmodder.datsimplecommands.utils;

import com.datmodder.datsimplecommands.SimpleCommands;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

@Config(modid = SimpleCommands.MOD_ID, name="SimpleCommands/SimpleCommands")
public class SimpleConfig {
    public static TeleportationConfig TELEPORTATION = new TeleportationConfig();

    private static class TeleportationConfig {
        @Config.Name("Teleportation Delay")
        @Config.Comment("How long the player must wait after typing the command before they can teleport")
        @Config.RangeInt(min=0)
        public int teleportationDelay = 5;
    }


}
