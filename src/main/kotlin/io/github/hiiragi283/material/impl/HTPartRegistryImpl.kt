package io.github.hiiragi283.material.impl

import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.nonAirOrNull
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartRegistry
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

object HTPartRegistryImpl : HTPartRegistry, SimpleSynchronousResourceReloadListener {
    private val registeredItemMap: MutableMap<Item, HTPart?> = hashMapOf()
    private val registeredTagMap: MutableMap<Tag<Item>, HTPart?> = hashMapOf()
    private val itemToPartMap: MutableMap<Item, HTPart> = linkedMapOf()
    private val partToItemsMap: Multimap<HTPart, Item> = LinkedHashMultimap.create()
    private var tagsPresent: Boolean = false

    init {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(this)
    }

    //    HTPartRegistry    //

    override val allEntries: Map<Item, HTPart>
        get() = itemToPartMap

    override fun add(item: ItemConvertible, value: HTPart?) {
        item.nonAirOrNull()?.let { registeredItemMap.put(it, value) }
    }

    override fun add(tag: Tag<Item>, value: HTPart?) {
        registeredTagMap[tag] = value
    }

    override fun get(item: ItemConvertible): HTPart? = item.nonAirOrNull()?.let(itemToPartMap::get)

    override fun getItem(part: HTPart): Collection<Item> = partToItemsMap.get(part)

    override fun remove(item: ItemConvertible) {
        add(item, null)
    }

    override fun remove(tag: Tag<Item>) {
        add(tag, null)
    }

    override fun clear(item: ItemConvertible) {
        item.nonAirOrNull()?.let(registeredItemMap::remove)
        if (tagsPresent) reload()
    }

    override fun clear(tag: Tag<Item>) {
        registeredTagMap.remove(tag)
        if (tagsPresent) reload()
    }

    override fun contains(item: ItemConvertible): Boolean = item.asItem() in itemToPartMap

    override fun contains(part: HTPart): Boolean = partToItemsMap.containsKey(part)

    //    SimpleSynchronousResourceReloadListener    //

    private fun reload() {
        itemToPartMap.clear()
        partToItemsMap.clear()

        registeredTagMap.forEach { (tag: Tag<Item>, part: HTPart?) ->
            if (part == null) return@forEach
            tag.values().forEach { item: Item ->
                itemToPartMap[item] = part
                partToItemsMap.put(part, item)
            }
        }

        registeredItemMap.forEach { (item: Item, part: HTPart?) ->
            if (part == null) return@forEach
            itemToPartMap[item] = part
            partToItemsMap.put(part, item)
        }
    }

    override fun reload(manager: ResourceManager) {
        reload()
        tagsPresent = true
    }

    override fun getFabricId(): Identifier = HTMaterialsAPI.id("part_registry")

    private val dependenciesTag: Collection<Identifier> = listOf(ResourceReloadListenerKeys.TAGS)

    override fun getFabricDependencies(): Collection<Identifier> = dependenciesTag
}
