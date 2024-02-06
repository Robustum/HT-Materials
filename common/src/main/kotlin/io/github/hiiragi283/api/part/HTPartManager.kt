package io.github.hiiragi283.api.part

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShapeKey
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.util.registry.Registry
import java.util.function.Predicate

@JvmDefaultWithCompatibility
interface HTPartManager {
    // Item -> HTPart

    val itemToPartMap: ImmutableMap<Item, HTPart>

    fun getPart(itemConvertible: ItemConvertible): HTPart? = itemToPartMap[itemConvertible.asItem()]

    fun hasPart(itemConvertible: ItemConvertible): Boolean = itemConvertible.asItem() in itemToPartMap

    // HTPart -> Item

    fun getDefaultItem(materialKey: HTMaterialKey, shapeKey: HTShapeKey): Item? {
        val items: Collection<Item> = getItems(materialKey, shapeKey)
        for (item: Item in items) {
            val namespace: String = Registry.ITEM.getId(item).namespace
            return when (namespace) {
                "minecraft" -> item
                HTMaterialsAPI.MOD_ID -> item
                else -> continue
            }
        }
        return items.firstOrNull()
    }

    // HTPart -> Collection<Item>

    val partToItemsMap: ImmutableMultimap<HTPart, Item>

    fun getAllEntries(): Collection<Entry> {
        val allEntries: Collection<Entry> = buildSet {
            for ((part: HTPart, item: Item) in partToItemsMap.entries()) {
                add(Entry(part.materialKey, part.shapeKey, item))
            }
        }
        return allEntries
    }

    fun getFilteredItems(predicate: Predicate<Entry>): Collection<Entry> = getAllEntries().filter(predicate::test)

    fun getItems(materialKey: HTMaterialKey, shapeKey: HTShapeKey): Collection<Item> = getItems(HTPart(materialKey, shapeKey))

    fun getItems(part: HTPart): Collection<Item> = partToItemsMap.get(part)

    fun hasItem(materialKey: HTMaterialKey, shapeKey: HTShapeKey): Boolean = hasItem(HTPart(materialKey, shapeKey))

    fun hasItem(part: HTPart): Boolean = partToItemsMap.containsKey(part)

    data class Entry(val materialKey: HTMaterialKey, val shapeKey: HTShapeKey, val item: Item)

    interface Builder {
        fun add(materialKey: HTMaterialKey, shapeKey: HTShapeKey, itemConvertible: ItemConvertible)
    }
}
