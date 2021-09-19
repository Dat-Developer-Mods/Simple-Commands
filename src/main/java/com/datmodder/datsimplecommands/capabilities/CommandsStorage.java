package com.datmodder.datsimplecommands.capabilities;

import com.datmodder.datsimplecommands.utils.enums.TPDirection;
import com.demmodders.datmoddingapi.structures.Location;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class CommandsStorage implements Capability.IStorage<ICommands> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICommands> capability, ICommands instance, EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();

        // Bed Map
        NBTTagCompound homeMap = new NBTTagCompound();
        for (String key : instance.getHomeLocations().keySet()) {
            homeMap.setTag(key, getLocationAsNBT(instance.getHomeLocations().get(key)));
        }

        tag.setTag("homeMap", homeMap);

        tag.setTag("lastLocation", getLocationAsNBT(instance.getBackLocation()));

        tag.setLong("lastTeleport", instance.getLastTeleport());

        NBTTagCompound tpaRequests = new NBTTagCompound();
        for (UUID key : instance.getTpaRequests().keySet()) {
            tpaRequests.setString(key.toString(), instance.getTpaRequests().get(key).name());
        }

        tag.setTag("tpaRequests", tpaRequests);

        return tag;
    }

    private NBTTagCompound getLocationAsNBT(Location loc) {
        NBTTagCompound homeLoc = new NBTTagCompound();

        homeLoc.setDouble("x", loc.x);
        homeLoc.setDouble("y", loc.y);
        homeLoc.setDouble("z", loc.z);
        homeLoc.setInteger("dim", loc.dim);
        homeLoc.setFloat("pitch", loc.pitch);
        homeLoc.setFloat("yaw", loc.yaw);
        return homeLoc;
    }

    private Location getLocationFromNBT(NBTTagCompound tag) {
        return new Location(tag.getInteger("dim"), tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"), tag.getFloat("pitch"), tag.getFloat("yaw"));
    }

    @Override
    public void readNBT(Capability<ICommands> capability, ICommands instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;

        instance.setBackLocation(getLocationFromNBT(tag.getCompoundTag("lastLocation")));

        // Bed Map
        HashMap<String, Location> homeMap = new HashMap<>();
        NBTTagCompound homeTag = tag.getCompoundTag("homeMap");
        for (String key : homeTag.getKeySet()) {
            homeMap.put(key, getLocationFromNBT(homeTag.getCompoundTag(key)));
        }

        instance.setHomeLocations(homeMap);

    }
}
