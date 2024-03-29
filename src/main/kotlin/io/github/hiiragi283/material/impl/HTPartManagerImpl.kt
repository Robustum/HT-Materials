package io.github.hiiragi283.material.impl

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.tag.TagsUpdatedEvent
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.tag.TagManager

object HTPartManagerImpl : HTPartManager, TagsUpdatedEvent {
    init {
        TagsUpdatedEvent.EVENT.register(::onUpdated)
    }

    //    HTPartRegistry    //

    override var itemToEntryMap: Map<Item, HTPart> = mapOf()
        private set

    override var partToEntriesMap: Map<HTPart, Set<Item>> = mapOf()
        private set

    override fun onUpdated(tagManager: TagManager, isClient: Boolean) {
        if (isClient) return
        HTPartManager.Builder().run {
            // Reload from Addons
            HTMaterialsAPI.INSTANCE.forEachAddon { it.modifyPartManager(this) }
            // Reload
            val itemToPart: MutableMap<Item, HTPart> = mutableMapOf()
            val partToItem: MutableMap<HTPart, MutableSet<Item>> = mutableMapOf()
            // Reload from tags
            registeredTagMap.forEach { (tag: Tag<Item>, part: HTPart) ->
                tag.values().forEach { item: Item ->
                    itemToPart[item] = part
                    partToItem.computeIfAbsent(part) { mutableSetOf() }.add(item)
                }
            }
            // Reload from items
            registeredItemMap.forEach { (item: Item, part: HTPart) ->
                itemToPart[item] = part
                partToItem.computeIfAbsent(part) { mutableSetOf() }.add(item)
            }
            // Initialize
            this@HTPartManagerImpl.itemToEntryMap = itemToPart
            this@HTPartManagerImpl.partToEntriesMap = partToItem
        }
        HTMaterialsAPI.LOGGER.info("HTPartManager reloaded!")
    }
}
