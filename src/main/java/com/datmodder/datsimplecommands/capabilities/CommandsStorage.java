package com.datmodder.datsimplecommands.capabilities;

import com.demmodders.datmoddingapi.structures.Location;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class CommandsStorage implements Capability.IStorage<ICommands> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICommands> capability, ICommands instance, EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();

        // Bed Map
        NBTTagCompound bedMap = new NBTTagCompound();
        for (String key : instance.getHomeLocations().keySet()) {
            Location loc = instance.getHomeLocations().get(key);

            bedMap.setTag(key, getLocationAsNBT(loc));
        }

        tag.setTag("bedMap", bedMap);

        tag.setTag("lastLocation", getLocationAsNBT(instance.getBackLocation()));

        return tag;
    }

    private NBTTagCompound getLocationAsNBT(Location loc) {
        NBTTagCompound bedLoc = new NBTTagCompound();

        bedLoc.setDouble("x", loc.x);
        bedLoc.setDouble("y", loc.y);
        bedLoc.setDouble("z", loc.z);
        bedLoc.setInteger("dim", loc.dim);
        bedLoc.setDouble("pitch", loc.pitch);
        bedLoc.setDouble("yaw", loc.yaw);
        return bedLoc;
    }

    @Override
    public void readNBT(Capability<ICommands> capability, ICommands instance, EnumFacing side, NBTBase nbt) {

    }
}
