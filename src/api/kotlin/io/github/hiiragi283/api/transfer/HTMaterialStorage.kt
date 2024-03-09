package io.github.hiiragi283.api.transfer

import io.github.hiiragi283.api.HTMaterialsAPI
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.minecraft.item.Item
import net.minecraft.util.math.Direction

@Suppress("UnstableApiUsage")
object HTMaterialStorage {
    @JvmField
    val SIDED: BlockApiLookup<Storage<HTMaterialVariant>, Direction> = BlockApiLookup.get(
        HTMaterialsAPI.id("sided_material_storage"),
        Storage.asClass(),
        Direction::class.java,
    )

    @JvmField
    val ITEM: ItemApiLookup<Storage<HTMaterialVariant>, ContainerItemContext> = ItemApiLookup.get(
        HTMaterialsAPI.id("material_storage"),
        Storage.asClass(),
        ContainerItemContext::class.java,
    )

    @JvmStatic
    fun combinedItemApiProvider(item: Item): Event<CombinedItemApiProvider> = HTCombinedProvidersImpl.getOrCreateItemEvent(item)

    @JvmField
    val GENERAL_COMBINED_PROVIDER: Event<CombinedItemApiProvider> = HTCombinedProvidersImpl.createEvent(false)

    fun interface CombinedItemApiProvider {
        fun find(context: ContainerItemContext): Storage<HTMaterialVariant>?
    }
}
