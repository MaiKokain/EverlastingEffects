package yuuria.everlastingeffects.data;

import net.minecraft.command.CommandBase;

public class EffectInstance {

    public final String name;
    public final int level;
    public final boolean isParticle;

    public EffectInstance(String name, int level, boolean particle) {
        this.name = name;
        this.level = level;
        this.isParticle = particle;
    }

}
