package hiiragi283.material.block.entity

import hiiragi283.material.init.HiiragiBlockEntities
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import java.util.*

@Suppress("UnstableApiUsage")
class MachineExtenderBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(
    HiiragiBlockEntities.MACHINE_EXTENDER,
    pos,
    state
), Storage<ItemVariant> {

    private fun getStorage(): Storage<ItemVariant>? {
        val direction: Direction = world?.getBlockState(pos)?.get(Properties.FACING) ?: return null
        val posTo: BlockPos = pos.offset(direction)
        return ItemStorage.SIDED.find(world, posTo, direction.opposite)
    }

    override fun insert(resource: ItemVariant, maxAmount: Long, transaction: TransactionContext): Long =
        getStorage()?.insert(resource, maxAmount, transaction) ?: 0

    override fun extract(resource: ItemVariant, maxAmount: Long, transaction: TransactionContext): Long =
        getStorage()?.extract(resource, maxAmount, transaction) ?: 0

    override fun iterator(transaction: TransactionContext): MutableIterator<StorageView<ItemVariant>> =
        getStorage()?.iterator(transaction) ?: Collections.emptyIterator()

}