package io.github.hiiragi283.material.impl

import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.nonAirOrNull
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartRegistry
import io.github.hiiragi283.api.tag.TagReloadedEvent
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.Tag

object HTPartRegistryImpl : HTPartRegistry {
    private val registeredItemMap: MutableMap<Item, HTPart?> = hashMapOf()
    private val registeredTagMap: MutableMap<Tag<Item>, HTPart?> = hashMapOf()
    private var itemToPartMap: MutableMap<Item, HTPart>? = null
    private var partToItemsMap: Multimap<HTPart, Item>? = null

    init {
        TagReloadedEvent.EVENT.register {
            itemToPartMap = null
            partToItemsMap = null
        }
    }

    //    HTPartRegistry    //

    private fun getEntryMapPair(): Pair<Map<Item, HTPart>, Multimap<HTPart, Item>> {
        var itemToPart: MutableMap<Item, HTPart>? = itemToPartMap
        var partToItem: Multimap<HTPart, Item>? = partToItemsMap
        if (itemToPart == null || partToItem == null) {
            itemToPart = mutableMapOf()
            partToItem = LinkedHashMultimap.create()!!
            // Reload from tags
            registeredTagMap.forEach { (tag: Tag<Item>, part: HTPart?) ->
                if (part == null) return@forEach
                tag.values().forEach { item: Item ->
                    itemToPart[item] = part
                    partToItem.put(part, item)
                }
            }
            // Reload from items
            registeredItemMap.forEach { (item: Item, part: HTPart?) ->
                if (part == null) return@forEach
                itemToPart[item] = part
                partToItem.put(part, item)
            }
            itemToPartMap = itemToPart
            partToItemsMap = partToItem
            HTMaterialsAPI.log("HTPartRegistry reloaded!")
        }
        return itemToPart to partToItem
    }

    override val allEntries: Map<Item, HTPart>
        get() = getEntryMapPair().first

    override fun add(item: ItemConvertible, value: HTPart?) {
        item.nonAirOrNull?.let { registeredItemMap.put(it, value) }
    }

    override fun add(tag: Tag<Item>, value: HTPart?) {
        registeredTagMap[tag] = value
    }

    override fun get(item: ItemConvertible): HTPart? = item.nonAirOrNull?.let { getEntryMapPair().first[it] }

    override fun getItem(part: HTPart): Collection<Item> = getEntryMapPair().second.get(part)

    override fun remove(item: ItemConvertible) {
        add(item, null)
    }

    override fun remove(tag: Tag<Item>) {
        add(tag, null)
    }

    override fun clear(item: ItemConvertible) {
        remove(item)
    }

    override fun clear(tag: Tag<Item>) {
        remove(tag)
    }

    override fun contains(item: ItemConvertible): Boolean = item.asItem() in getEntryMapPair().first

    override fun contains(part: HTPart): Boolean = getEntryMapPair().second.containsKey(part)
}
