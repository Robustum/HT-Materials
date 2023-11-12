package io.github.hiiragi283.material.api.item

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.ItemConvertible
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("unused")
interface MaterialItemConvertible : ItemConvertible {

    val material: HTMaterial
    val shape: HTShape

    fun registerPart() {
        register(material, shape, this)
    }

    companion object {

        private val logger: Logger = LogManager.getLogger("MaterialItemConvertible")

        private val itemToPart: MutableMap<ItemConvertible, HTPart> = mutableMapOf()

        @JvmStatic
        val ITEM_TO_PART: Map<ItemConvertible, HTPart> = itemToPart

        private val partToItem: MutableMap<HTPart, ItemConvertible> = mutableMapOf()

        @JvmStatic
        val PART_TO_ITEM: Map<HTPart, ItemConvertible> = partToItem

        private val partToItems: Multimap<HTPart, ItemConvertible> = HashMultimap.create()

        @JvmStatic
        val PART_TO_ITEMS: Multimap<HTPart, ItemConvertible> = partToItems


        @JvmStatic
        fun register(material: HTMaterial, shape: HTShape, itemConvertible: ItemConvertible) {
            register(HTPart(material, shape), itemConvertible)
        }

        private fun register(part: HTPart, itemConvertible: ItemConvertible) {
            itemToPart.putIfAbsent(itemConvertible, part)
            if (!partToItem.containsKey(part)) {
                partToItem[part] = itemConvertible
                logger.info("The item: $itemConvertible registered as default item for part: $part!")
            }
            partToItems.put(part, itemConvertible)
            logger.info("The item: $itemConvertible linked to part: $part!")
        }

        @JvmStatic
        fun getPart(itemConvertible: ItemConvertible): HTPart? = itemToPart[itemConvertible]

        @JvmStatic
        fun getItem(part: HTPart): ItemConvertible? = partToItem[part]

        @JvmStatic
        fun getItems(part: HTPart): MutableCollection<ItemConvertible> = partToItems[part]

    }

    data class Wrapper(
        override val material: HTMaterial,
        override val shape: HTShape,
        val itemConvertible: ItemConvertible
    ) : MaterialItemConvertible, ItemConvertible by itemConvertible

}