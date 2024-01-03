package constantan.mxdemon;

import constantan.mxdemon.client.ClientSetup;
import constantan.mxdemon.common.ModSetup;
import constantan.mxdemon.lib.LibMisc;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(LibMisc.MOD_ID)
public class MaxwellsDemonGenerator {
    public static final Logger LOGGER = LoggerFactory.getLogger(LibMisc.MOD_NAME);

    public MaxwellsDemonGenerator() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModSetup.setup(modEventBus);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(ClientSetup::init));
    }
}
