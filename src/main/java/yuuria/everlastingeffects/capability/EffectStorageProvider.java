package yuuria.everlastingeffects.capability;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import yuuria.everlastingeffects.data.EffectStorage;
import yuuria.everlastingeffects.data.IEffectStorage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EffectStorageProvider implements ICapabilitySerializable<NBTBase> {

    private final IEffectStorage inst = new EffectStorage();

    @Override
    public boolean hasCapability(@NonNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == EffectCapabilities.EFFECT_STORAGE;
    }

    @Override
    public @Nullable
    <T> T getCapability(@NonNull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == EffectCapabilities.EFFECT_STORAGE ? EffectCapabilities.EFFECT_STORAGE.cast(inst) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return EffectCapabilities.EFFECT_STORAGE.writeNBT(inst, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        EffectCapabilities.EFFECT_STORAGE.readNBT(inst, null, nbt);
    }

}
