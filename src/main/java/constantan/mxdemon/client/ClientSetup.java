package constantan.mxdemon.client;

import constantan.mxdemon.client.gui.TestGeneratorScreen;
import constantan.mxdemon.common.init.ModBlocks;
import constantan.mxdemon.common.init.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TEST_GENERATOR.get(), RenderType.translucent());

        MenuScreens.register(ModMenuTypes.TEST_GENERATOR_MENU.get(), TestGeneratorScreen::new);
    }
}
