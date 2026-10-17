package yuuria.everlastingeffects.commands.everlasting;

import org.jspecify.annotations.Nullable;
import yuuria.everlastingeffects.EverlastingEffects;
import yuuria.everlastingeffects.capability.EffectCapabilities;
import yuuria.everlastingeffects.data.IEffectStorage;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Add extends CommandBase {

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "everlasting_effects add <player> <effect> <level> <is_particle>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EverlastingEffects.LOGGER.info("args for add is {} = {}", args.length, args);
        String pName = args[0];
        String eName = args[1];
        int eLvl = Integer.parseInt(args[2]);
        boolean eParticle = Boolean.parseBoolean(args[3]);
        List<Entity> targets;
        if (pName.startsWith("@")) {
            targets = EntitySelector.matchEntities(sender, pName, Entity.class);
        } else {
            EntityPlayerMP p = getPlayer(server, sender, pName);
            targets = p == null ? Collections.emptyList() : Collections.singletonList(p);
        }

        for (Entity e : targets) {
            if (!(e instanceof EntityPlayerMP)) {
                continue;
            }
            EntityPlayerMP p = (EntityPlayerMP) e;
            IEffectStorage storage = p.getCapability(EffectCapabilities.EFFECT_STORAGE, null);
            if (storage == null) {
                continue;
            }
            if (!storage.addEffect(eName, eLvl, eParticle)) {
                sender.sendMessage(new TextComponentString("Failed to add " + eName + " to " + p.getName()));
            }
            ((EntityPlayerMP) e).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(eName), 1600,
                eLvl, false, !eParticle
            ));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
        @Nullable BlockPos pos
    ) {
        if (args.length == 1) {
            List<String> out = new ArrayList<>(Arrays.asList(server.getOnlinePlayerNames()));
            out.addAll(Arrays.asList("@a", "@p", "@r", "@s"));
            return getListOfStringsMatchingLastWord(args, out);
        }
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, Potion.REGISTRY.getKeys());
        }
        if (args.length == 4) {
            return getListOfStringsMatchingLastWord(args, "true", "false");
        }
        return Collections.emptyList();
    }

}
