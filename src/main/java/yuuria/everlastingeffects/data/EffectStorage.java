package yuuria.everlastingeffects.data;

import java.util.ArrayList;
import java.util.List;

public class EffectStorage implements IEffectStorage {

    private final List<EffectInstance> effects = new ArrayList<>();

    @Override
    public List<EffectInstance> getEffects() {
        return effects;
    }

    @Override
    public boolean addEffect(String name, int lvl, boolean showParticle) {
        removeEffect(name);
        return effects.add(new EffectInstance(name, lvl, showParticle));
    }

    @Override
    public boolean removeEffect(String name) {
        return effects.removeIf(e -> e.name.equals(name));
    }

    @Override
    public void clear() {
        effects.clear();
    }

}
