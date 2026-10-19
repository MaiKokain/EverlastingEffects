package yuuria.everlastingeffects.commands;

import yuuria.everlastingeffects.commands.everlasting.Add;
import yuuria.everlastingeffects.commands.everlasting.List;
import yuuria.everlastingeffects.commands.everlasting.Remove;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandEverlastingEffects extends CommandTreeBase {

    public CommandEverlastingEffects() {
        this.addSubcommand(new Add());
        this.addSubcommand(new Remove());
        this.addSubcommand(new List());
    }

    @Override
    public String getName() {
        return "everlasting_effects";
    }

    @Override
    public String getUsage(ICommandSender var1) {
        return "everlasting_effects";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

}
