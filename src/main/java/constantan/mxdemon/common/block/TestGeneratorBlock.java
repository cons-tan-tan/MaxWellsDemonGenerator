package constantan.mxdemon.common.block;

import constantan.mxdemon.common.blockentity.TestGeneratorBlockEntity;
import constantan.mxdemon.common.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TestGeneratorBlock extends BaseEntityBlock {

    public TestGeneratorBlock(Properties properties) {
        super(properties);
    }

    /* Block Entity */

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof TestGeneratorBlockEntity) {
                ((TestGeneratorBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel,
                                          @NotNull BlockPos pPos, @NotNull Player pPlayer,
                                          @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof TestGeneratorBlockEntity testGeneratorBlockEntity) {
                testGeneratorBlockEntity.syncClientEnergy((ServerPlayer) pPlayer);// クライアント側のエネルギーを同期
                NetworkHooks.openGui((ServerPlayer) pPlayer, (TestGeneratorBlockEntity) entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new TestGeneratorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState,
                                                                  @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.TEST_GENERATOR_ENTITY.get(),
                TestGeneratorBlockEntity::tick);
    }

    /* copy from Glass Block */

    @Override
    public boolean skipRendering(@NotNull BlockState pState, BlockState pAdjacentBlockState, @NotNull Direction pSide) {
        return pAdjacentBlockState.is(this) || super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    public @NotNull VoxelShape getVisualShape(@NotNull BlockState pState, @NotNull BlockGetter pReader,
                                              @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Shapes.empty();
    }

    public float getShadeBrightness(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(@NotNull BlockState pState, @NotNull BlockGetter pReader, @NotNull BlockPos pPos) {
        return true;
    }
}
