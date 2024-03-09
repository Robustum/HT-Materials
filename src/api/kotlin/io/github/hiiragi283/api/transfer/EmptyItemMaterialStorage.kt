package io.github.hiiragi283.api.transfer

import io.github.hiiragi283.api.material.HTMaterial
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.storage.base.BlankVariantView
import net.fabricmc.fabric.api.transfer.v1.storage.base.InsertionOnlyStorage
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleViewIterator
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.minecraft.item.Item

@Suppress("UnstableApiUsage")
class EmptyItemMaterialStorage(
    private val context: ContainerItemContext,
    private val insertableMaterial: HTMaterial,
    private val insertableAmount: Long,
    private val emptyToFullMapping: (ItemVariant) -> ItemVariant,
) : InsertionOnlyStorage<HTMaterialVariant> {
    constructor(
        context: ContainerItemContext,
        fullItem: Item,
        insertableMaterial: HTMaterial,
        insertableAmount: Long,
    ) : this(
        context,
        insertableMaterial,
        insertableAmount,
        { ItemVariant.of(fullItem, it.nbt) },
    )

    private val emptyItem: Item = context.itemVariant.item

    init {
        StoragePreconditions.notNegative(insertableAmount)
    }

    override fun insert(resource: HTMaterialVariant, maxAmount: Long, transaction: TransactionContext): Long {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount)

        if (!context.itemVariant.isOf(emptyItem)) return 0

        if (resource.isOf(insertableMaterial) && maxAmount >= insertableAmount) {
            val newVariant = emptyToFullMapping(context.itemVariant)
            if (context.exchange(newVariant, 1, transaction) == 1L) {
                return insertableAmount
            }
        }

        return 0
    }

    override fun iterator(transaction: TransactionContext): Iterator<StorageView<HTMaterialVariant>> =
        SingleViewIterator.create(BlankVariantView(HTMaterialVariant.BLANK, insertableAmount), transaction)
}
