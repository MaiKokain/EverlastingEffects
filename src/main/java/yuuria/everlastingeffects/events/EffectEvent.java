package yuuria.everlastingeffects.events;

import yuuria.everlastingeffects.capability.EffectCapabilities;
import yuuria.everlastingeffects.capability.EffectStorageProvider;
import yuuria.everlastingeffects.data.EffectInstance;
import yuuria.everlastingeffects.data.IEffectStorage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;

@Mod.EventBusSubscriber(modid = "@{MOD_ID}")
public class EffectEvent {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> entity) {
        if (entity.getObject() instanceof EntityPlayer) {
            entity.addCapability(new ResourceLocation("@{MOD_ID}", "everlastingeffects"), new EffectStorageProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        IEffectStorage old = event.getOriginal().getCapability(EffectCapabilities.EFFECT_STORAGE, null);
        IEffectStorage cur = event.getEntityPlayer().getCapability(EffectCapabilities.EFFECT_STORAGE, null);
        if (old != null && cur != null) {
            cur.clear();
            cur.getEffects().addAll(old.getEffects());
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote) {
            return;
        }
        EntityPlayer player = event.player;
        if (player.ticksExisted % 200 == 0) {
            IEffectStorage storage = player.getCapability(EffectCapabilities.EFFECT_STORAGE, null);
            if (storage == null || storage.getEffects().isEmpty()) {
                return;
            }
            Iterator<EffectInstance> it = storage.getEffects().iterator();
            while (it.hasNext()) {
                EffectInstance e = it.next();
                PotionEffect effect = new PotionEffect(Potion.getPotionFromResourceLocation(e.name), 1600, e.level,
                    false, !e.isParticle
                );
                player.addPotionEffect(effect);
            }
        }
    }

}
