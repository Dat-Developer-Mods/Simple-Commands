package com.datmodder.datsimplecommands.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CommandsProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(ICommands.class)
    public static final Capability<ICommands> COMMANDS_CAPABILITY = null;

    private ICommands instance = COMMANDS_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == COMMANDS_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == COMMANDS_CAPABILITY ? COMMANDS_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return COMMANDS_CAPABILITY.getStorage().writeNBT(COMMANDS_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        COMMANDS_CAPABILITY.getStorage().readNBT(COMMANDS_CAPABILITY, this.instance, null, nbt);
    }
}