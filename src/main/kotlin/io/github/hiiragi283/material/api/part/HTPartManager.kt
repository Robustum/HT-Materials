package io.github.hiiragi283.material.api.part

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.material.api.item.MaterialItemConvertible
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.ItemConvertible
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("unused")
object HTPartManager {

    private val logger: Logger = LogManager.getLogger("HTPartManager")

    private val itemToPart: MutableMap<ItemConvertible, HTPart> = mutableMapOf()

    @JvmStatic
    val ITEM_TO_PART: Map<ItemConvertible, HTPart> = itemToPart

    private val partToItems: Multimap<HTPart, ItemConvertible> = HashMultimap.create()

    @JvmStatic
    val PART_TO_ITEMS: Multimap<HTPart, ItemConvertible> = partToItems

    @JvmStatic
    fun register(material: HTMaterial, shape: HTShape, itemConvertible: ItemConvertible) {
        register(HTPart(material, shape), itemConvertible)
    }

    private fun register(part: HTPart, itemConvertible: ItemConvertible) {
        itemToPart.putIfAbsent(itemConvertible, part)
        if (!MaterialItemConvertible.hasDefaultItem(part)) {
            MaterialItemConvertible.registerDefaultItem(part, itemConvertible)
        }
        partToItems.put(part, itemConvertible)
        logger.info("The item: $itemConvertible linked to part: $part!")
    }

    @JvmStatic
    fun getPart(itemConvertible: ItemConvertible): HTPart? = itemToPart[itemConvertible]

    @JvmStatic
    fun getItem(part: HTPart): ItemConvertible? = MaterialItemConvertible.getDefaultItem(part)

    @JvmStatic
    fun getItems(part: HTPart): MutableCollection<ItemConvertible> = partToItems[part]

}