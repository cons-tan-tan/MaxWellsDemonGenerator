package constantan.mxdemon.common.init;

import constantan.mxdemon.common.block.entity.TestGeneratorBlockEntity;
import constantan.mxdemon.lib.LibMisc;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LibMisc.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final RegistryObject<BlockEntityType<TestGeneratorBlockEntity>> TEST_GENERATOR_ENTITY =
            BLOCK_ENTITIES.register("test_generator_entity",
                    () -> BlockEntityType.Builder.of(TestGeneratorBlockEntity::new, ModBlocks.TEST_GENERATOR.get()).build(null));
}
