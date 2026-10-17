package yuuria.everlastingeffects.data;

import java.util.List;

public interface IEffectStorage {

    List<EffectInstance> getEffects();

    boolean addEffect(String name, int lvl, boolean showParticle);

    boolean removeEffect(String name);

    void clear();

}
