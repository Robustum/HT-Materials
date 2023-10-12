package hiiragi283.material.block.entity

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import java.util.*

@Suppress("UnstableApiUsage")
abstract class StorageExtenderBlockEntity<T>(
    type: BlockEntityType<*>,
    pos: BlockPos,
    state: BlockState,
    private val lookUp: () -> BlockApiLookup<Storage<T>, Direction>
) : BlockEntity(
    type,
    pos,
    state
), Storage<T> {

    private fun getStorage(): Storage<T>? {
        val direction: Direction = world?.getBlockState(pos)?.get(Properties.FACING) ?: return null
        val posTo: BlockPos = pos.offset(direction)
        return lookUp().find(world, posTo, direction.opposite)
    }

    override fun insert(resource: T, maxAmount: Long, transaction: TransactionContext): Long =
        getStorage()?.insert(resource, maxAmount, transaction) ?: 0

    override fun extract(resource: T, maxAmount: Long, transaction: TransactionContext?): Long =
        getStorage()?.extract(resource, maxAmount, transaction) ?: 0

    override fun iterator(transaction: TransactionContext): MutableIterator<StorageView<T>> =
        getStorage()?.iterator(transaction) ?: Collections.emptyIterator()

}