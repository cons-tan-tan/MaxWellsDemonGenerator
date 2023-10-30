package constantan.mxdemon.common.init;

import constantan.mxdemon.lib.LibMisc;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister
            .create(ForgeRegistries.ITEMS, LibMisc.MOD_ID);
}
