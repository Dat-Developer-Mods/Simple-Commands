package com.datmodder.datsimplecommands.utils;

import com.datmodder.datsimplecommands.utils.structures.PlayerData;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private static PlayerManager INSTANCE;

    HashMap<UUID, PlayerData> players = new HashMap<>();

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }


}
