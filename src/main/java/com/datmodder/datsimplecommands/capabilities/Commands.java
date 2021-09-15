package com.datmodder.datsimplecommands.capabilities;

import com.datmodder.datsimplecommands.utils.enums.TPDirection;
import com.demmodders.datmoddingapi.structures.Location;

import java.util.HashMap;
import java.util.UUID;

public class Commands implements ICommands {
    private HashMap<String, Location> homeLocations = new HashMap<>();
    private Location backLocation = null;
    private HashMap<UUID, TPDirection> tpaRequests = new HashMap<>();
    private long lastTeleport = 0L;

    @Override
    public HashMap<String, Location> getHomeLocations() {
        return homeLocations;
    }

    @Override
    public void setHomeLocations(HashMap<String, Location> homeLocations) {
        this.homeLocations = homeLocations;
    }

    @Override
    public Location getHomeLocation(String homeName) {
        return homeLocations.get(homeName);
    }

    @Override
    public void putHomeLocation(String homeName, Location location) {
        homeLocations.put(homeName, location);
    }

    @Override
    public Location getBackLocation() {
        return backLocation;
    }

    @Override
    public void setBackLocation(Location backLocation) {
        this.backLocation = backLocation;
    }

    @Override
    public HashMap<UUID, TPDirection> getTpaRequests() {
        return tpaRequests;
    }

    @Override
    public void setTpaRequests(HashMap<UUID, TPDirection> tpaRequests) {
        this.tpaRequests = tpaRequests;
    }

    @Override
    public TPDirection getTpaRequest(UUID userUUID) {
        return tpaRequests.get(userUUID);
    }

    @Override
    public void putTpaRequest(UUID userUUID, TPDirection direction) {
        tpaRequests.put(userUUID, direction);
    }

    @Override
    public long getLastTeleport() {
        return lastTeleport;
    }

    @Override
    public void setLastTeleport(long lastTeleport) {
        this.lastTeleport = lastTeleport;
    }
}
