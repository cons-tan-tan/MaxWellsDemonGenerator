package constantan.mxdemon.common.init;

import constantan.mxdemon.common.container.TestGeneratorMenu;
import constantan.mxdemon.lib.LibMisc;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, LibMisc.MOD_ID);

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static final RegistryObject<MenuType<TestGeneratorMenu>> TEST_GENERATOR_MENU =
            registerMenuType(TestGeneratorMenu::new, "test_generator_menu");
}
