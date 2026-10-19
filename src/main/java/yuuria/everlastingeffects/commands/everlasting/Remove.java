package yuuria.everlastingeffects.commands.everlasting;

import org.jspecify.annotations.Nullable;
import yuuria.everlastingeffects.EverlastingEffects;
import yuuria.everlastingeffects.capability.EffectCapabilities;
import yuuria.everlastingeffects.data.EffectInstance;
import yuuria.everlastingeffects.data.IEffectStorage;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Remove extends CommandBase {

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getUsage(ICommandSender var1) {
        return "everlasting_effects remove <player> <effect>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EverlastingEffects.LOGGER.info("args for remove is {} = {}", args.length, args);
        String pName = args[0];
        String eName = args[1];

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
            if (eName.equals("all")) {
                storage.getEffects()
                    .iterator()
                    .forEachRemaining(ei -> p.removePotionEffect(Potion.getPotionFromResourceLocation(ei.name)));
                storage.clear();
            } else {
                storage.removeEffect(eName);
                p.removePotionEffect(Potion.getPotionFromResourceLocation(eName));
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
        @Nullable BlockPos pos
    ) {
        if (args.length == 1) {
            List<String> out = new ArrayList<>(Arrays.asList(server.getOnlinePlayerNames()));
            out.add("@s");
            return getListOfStringsMatchingLastWord(args, out);
        }
        if (args.length == 2) {
            List<String> out = new ArrayList<>();
            out.add("all");
            String nameArgs = args[0];
            EntityPlayerMP playerMP;
            if (nameArgs.equals("@s")) {
                try {
                    playerMP = EntitySelector.matchOnePlayer(sender, nameArgs);
                } catch (CommandException e) {
                    throw new RuntimeException(e);
                }
            } else {
                playerMP = server.getPlayerList().getPlayerByUsername(args[0]);
            }
            if (playerMP != null) {
                IEffectStorage storage = playerMP.getCapability(EffectCapabilities.EFFECT_STORAGE, null);
                if (storage != null) {
                    for (EffectInstance e : storage.getEffects()) {
                        out.add(e.name);
                    }
                }
            }
            return getListOfStringsMatchingLastWord(args, out);
        }
        return Collections.emptyList();
    }

}
