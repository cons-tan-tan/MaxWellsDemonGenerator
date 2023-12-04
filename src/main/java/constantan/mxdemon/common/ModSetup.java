package constantan.mxdemon.common;

import constantan.mxdemon.common.init.ModBlockEntities;
import constantan.mxdemon.common.init.ModBlocks;
import constantan.mxdemon.common.init.ModItems;
import constantan.mxdemon.common.init.ModMenuTypes;
import constantan.mxdemon.common.network.Messages;
import constantan.mxdemon.lib.LibMisc;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

public class ModSetup {

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(LibMisc.MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Blocks.GLASS);
        }
    };

    public static void setup(IEventBus modEventBus) {
        modEventBus.addListener(ModSetup::init);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
    }

    public static void init(final FMLCommonSetupEvent event) {
        Messages.register();
    }
}
