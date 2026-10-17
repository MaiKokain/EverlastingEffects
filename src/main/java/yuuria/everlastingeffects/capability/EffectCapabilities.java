package yuuria.everlastingeffects.capability;

import org.jspecify.annotations.Nullable;
import yuuria.everlastingeffects.data.EffectInstance;
import yuuria.everlastingeffects.data.EffectStorage;
import yuuria.everlastingeffects.data.IEffectStorage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public final class EffectCapabilities {

    @CapabilityInject(IEffectStorage.class) public static Capability<IEffectStorage> EFFECT_STORAGE = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IEffectStorage.class,
            new Capability.IStorage<IEffectStorage>() {

                @Override
                public @Nullable
                NBTBase writeNBT(Capability<IEffectStorage> capability, IEffectStorage instance, EnumFacing side) {
                    NBTTagList list = new NBTTagList();
                    for (EffectInstance e : instance.getEffects()) {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setString("effectName", e.name);
                        tag.setInteger("effectLvl", e.level);
                        tag.setBoolean("effectShowParticle", e.isParticle);
                        list.appendTag(tag);
                    }
                    NBTTagCompound root = new NBTTagCompound();
                    root.setTag("everlastingEffects", list);
                    return root;
                }

                @Override
                public void readNBT(Capability<IEffectStorage> capability, IEffectStorage instance, EnumFacing side,
                    NBTBase nbt
                ) {
                    instance.clear();
                    NBTTagList list = ((NBTTagCompound) nbt).getTagList("everlastingEffects", 10);
                    for (int i = 0; i < list.tagCount(); i++) {
                        NBTTagCompound tag = list.getCompoundTagAt(i);
                        instance.getEffects()
                            .add(new EffectInstance(tag.getString("effectName"), tag.getInteger("effectLevel"),
                                tag.getBoolean("effectShowParticle")
                            ));
                    }
                }

            },
            EffectStorage::new
        );
    }

}
