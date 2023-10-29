package constantan.mxdemon.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    public static void setup(IEventBus modEventBus) {
        modEventBus.addListener(ModSetup::init);
    }

    public static void init(final FMLCommonSetupEvent event) {
    }
}
