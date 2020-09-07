package com.datmodder.datsimplecommands.utils.structures;

import com.datmodder.datsimplecommands.utils.enums.TPDirection;
import com.demmodders.datmoddingapi.structures.Location;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
    public HashMap<String, Location> homeLocation = new HashMap<>();
    transient public Location backLocation = null;
    transient public HashMap<UUID, TPDirection> tpaRequests = new HashMap<>();

    PlayerData(){}
}
