package constantan.mxdemon.common.blockentity;

import constantan.mxdemon.common.container.TestGeneratorMenu;
import constantan.mxdemon.common.init.ModBlockEntities;
import constantan.mxdemon.common.init.ModItems;
import constantan.mxdemon.common.network.Messages;
import constantan.mxdemon.common.network.packet.EnergySyncS2CPacket;
import constantan.mxdemon.common.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class TestGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            // GUI以外でクライアント同期させたい場合はここに処理を書く
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public TestGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TEST_GENERATOR_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TextComponent("Test Generator");// todo langファイルの名前を参照させる
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new TestGeneratorMenu(pContainerId, pPlayerInventory, this);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        ENERGY_STORAGE.setEnergy(energy);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("test_generator.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(nbt.getInt("test_generator.energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, TestGeneratorBlockEntity pBlockEntity) {
        if (pLevel.isClientSide()) {
            return;
        }

        if (hasDemonInSlot(pBlockEntity)) {
            pBlockEntity.ENERGY_STORAGE.receiveEnergy(64, false);
        }
    }

    public void syncClientEnergy(ServerPlayer player) {
        Messages.sendToPlayer(new EnergySyncS2CPacket(ENERGY_STORAGE.getEnergyStored(), getBlockPos()), player);
    }

    private static boolean hasDemonInSlot(TestGeneratorBlockEntity pBlockEntity) {
        return pBlockEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.TEST_DEMON.get();
    }
}
