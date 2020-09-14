package com.datmodder.datsimplecommands.utils.structures;

import com.datmodder.datsimplecommands.utils.enums.TPDirection;
import com.demmodders.datmoddingapi.structures.Location;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
    public HashMap<String, Location> homeLocations = new HashMap<>();
    transient public Location backLocation = null;
    transient public HashMap<UUID, TPDirection> tpaRequests = new HashMap<>();
    transient public long lastTeleport = 0L;

    public PlayerData(){}

    public void addHome(String HomeName, Location HomeLocation) {
        homeLocations.put(HomeName, HomeLocation);

    }
}
