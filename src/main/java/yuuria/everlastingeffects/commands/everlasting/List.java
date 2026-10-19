package yuuria.everlastingeffects.commands.everlasting;

import org.jspecify.annotations.Nullable;
import yuuria.everlastingeffects.capability.EffectCapabilities;
import yuuria.everlastingeffects.data.EffectInstance;
import yuuria.everlastingeffects.data.IEffectStorage;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.Collections;

public class List extends CommandBase {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getUsage(ICommandSender var1) {
        return "everlasting_effects list <player>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String pName;
        if (args.length >= 1) {
            pName = args[0];
        } else {
            pName = getCommandSenderAsPlayer(sender).getName();
        }
        EntityPlayerMP entityPlayerMP = getPlayer(server, sender, pName);
        IEffectStorage effectStorage = entityPlayerMP.getCapability(EffectCapabilities.EFFECT_STORAGE, null);
        if (effectStorage == null) {
            throw new CommandException("Effects storage is missing for %s", pName);
        }
        if (effectStorage.getEffects().isEmpty()) {
            sender.sendMessage(new TextComponentString(pName + " has no everlasting effects added"));
            return;
        }
        sender.sendMessage(new TextComponentString(String.format("%s effects (%d)", pName,
            effectStorage.getEffects().size()
        )));
        for (EffectInstance e : effectStorage.getEffects()) {
            sender.sendMessage(new TextComponentString(String.format("\t- {%s, %d, %b}", e.name, e.level, !e.isParticle
            )));
        }
    }

    @Override
    public java.util.List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
        @Nullable BlockPos pos
    ) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        return Collections.emptyList();
    }

}
