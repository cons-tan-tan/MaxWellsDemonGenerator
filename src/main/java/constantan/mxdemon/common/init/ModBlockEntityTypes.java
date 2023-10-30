package constantan.mxdemon.common.init;

import constantan.mxdemon.lib.LibMisc;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, LibMisc.MOD_ID);
}
