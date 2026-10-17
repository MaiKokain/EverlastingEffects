package yuuria.everlastingeffects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuuria.everlastingeffects.capability.EffectCapabilities;
import yuuria.everlastingeffects.commands.CommandEverlastingEffects;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "@{MOD_ID}", name = "@{MOD_NAME}", version = "@{MOD_VERSION}", serverSideOnly = true,
    acceptableRemoteVersions = "*"
)
public class EverlastingEffects {

    public static final Logger LOGGER = LogManager.getLogger("@{MOD_NAME}");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        EffectCapabilities.register();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandEverlastingEffects());
    }

}
