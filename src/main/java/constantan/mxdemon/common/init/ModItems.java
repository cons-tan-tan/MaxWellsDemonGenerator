package constantan.mxdemon.common.init;

import constantan.mxdemon.common.ModSetup;
import constantan.mxdemon.common.item.TestDemonItem;
import constantan.mxdemon.lib.LibMisc;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister
            .create(ForgeRegistries.ITEMS, LibMisc.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> TEST_GENERATOR = ITEMS.register("test_generator_item",
            () -> new BlockItem(ModBlocks.TEST_GENERATOR.get(), new Item.Properties().tab(ModSetup.CREATIVE_TAB)));

    public static final RegistryObject<Item> TEST_DEMON = ITEMS.register("test_demon",
            () -> new TestDemonItem(new Item.Properties().tab(ModSetup.CREATIVE_TAB)));
}
