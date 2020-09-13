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

    public PlayerData getPlayer(UUID PlayerID) {
        return players.get(PlayerID);
    }

    // TODO: Finish

    public void savePlayer(UUID PlayerID) {

    }

    public void loadPlayer(UUID PlayerID) {

    }
}
