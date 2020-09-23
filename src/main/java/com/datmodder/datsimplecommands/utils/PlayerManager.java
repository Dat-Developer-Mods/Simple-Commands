package com.datmodder.datsimplecommands.utils;

import com.datmodder.datsimplecommands.SimpleCommands;
import com.datmodder.datsimplecommands.utils.structures.PlayerData;
import com.demmodders.datmoddingapi.structures.Location;
import com.demmodders.datmoddingapi.util.FileHelper;
import com.google.gson.Gson;
import ibxm.Player;

import java.io.*;
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

    public PlayerData newPlayer(UUID PlayerID) {
        PlayerData player = new PlayerData();

        players.put(PlayerID, player);

        savePlayer(PlayerID, player);
        return player;
    }

    public void updatePlayerBackLocation(UUID PlayerID, Location BackLocation) {
        getPlayer(PlayerID).backLocation = BackLocation;
        savePlayer(PlayerID);
    }

    public void addPlayerHome(UUID PlayerID, String HomeName, Location HomeLocation) {
        getPlayer(PlayerID).addHome(HomeName, HomeLocation);
        savePlayer(PlayerID);
    }

    public PlayerData getPlayer(UUID PlayerID) {
        return players.get(PlayerID);
    }


    public void savePlayer(UUID PlayerID, PlayerData Player) {
        File playerFile = new File(FileHelper.getConfigSubDir(SimpleCommands.MOD_ID), "Players/" + PlayerID.toString() + ".json");
        Gson gson = new Gson();
        try {
            FileHelper.openFile(playerFile);
            BufferedWriter writer = new BufferedWriter(new FileWriter(playerFile));
            writer.write(gson.toJson(Player));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePlayer(UUID PlayerID) {
        savePlayer(PlayerID, players.get(PlayerID));
    }

    public void loadPlayer(UUID PlayerID) {
        File playerFile = new File(FileHelper.getConfigSubDir(SimpleCommands.MOD_ID), "Players/" + PlayerID.toString() + ".json");
        PlayerData player;
        if (playerFile.exists()) {
            Gson gson = new Gson();
            try (Reader jsonReader = new FileReader(playerFile)) {
                player = gson.fromJson(jsonReader, PlayerData.class);
            } catch (IOException e) {
                e.printStackTrace();
                player = new PlayerData();
            }
        } else {
            player = new PlayerData();
        }
        players.put(PlayerID, player);
    }

    public void unloadPlayer(UUID PlayerID) {
        savePlayer(PlayerID);
        players.remove(PlayerID);
    }
}
