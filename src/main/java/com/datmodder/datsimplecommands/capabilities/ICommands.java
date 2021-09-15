package com.datmodder.datsimplecommands.capabilities;

import com.datmodder.datsimplecommands.utils.enums.TPDirection;
import com.demmodders.datmoddingapi.structures.Location;

import java.util.HashMap;
import java.util.UUID;

public interface ICommands {
    HashMap<String, Location> getHomeLocations();
    void setHomeLocations(HashMap<String, Location> homeLocations);

    Location getHomeLocation(String homeName);
    void putHomeLocation(String homeName, Location location);

    Location getBackLocation();
    void setBackLocation(Location backLocation);

    HashMap<UUID, TPDirection> getTpaRequests();
    void setTpaRequests(HashMap<UUID, TPDirection> tpaRequests);

    TPDirection getTpaRequest(UUID userUUID);
    void putTpaRequest(UUID userUUID, TPDirection direction);

    long getLastTeleport();
    void setLastTeleport(long lastTeleport);

    void set(ICommands commands);
}
