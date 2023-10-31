package constantan.mxdemon.common.init;

import constantan.mxdemon.common.block.TestGeneratorBlock;
import constantan.mxdemon.lib.LibMisc;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LibMisc.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static final RegistryObject<Block> TEST_GENERATOR = BLOCKS.register("test_generator_block",
            () -> new TestGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
}
