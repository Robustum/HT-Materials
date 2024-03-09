package io.github.hiiragi283.material.impl

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.runTryAndCatch
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.material.HTMaterialsCore
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.item.Item
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.tag.Tag
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

object HTPartManagerImpl : HTPartManager, ServerWorldEvents.Load {
    init {
        ServerWorldEvents.LOAD.register(::onWorldLoad)
    }

    //    HTPartRegistry    //

    override var itemToEntryMap: Map<Item, HTPart> = mapOf()
        private set

    override var partToEntriesMap: Map<HTPart, Set<Item>> = mapOf()
        private set

    override fun onWorldLoad(server: MinecraftServer, world: ServerWorld) {
        HTPartManager.Builder().run {
            // Reload from Addons
            HTMaterialsCore.addons.forEach { runTryAndCatch { it.modifyPartManager(this) } }
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
        HTMaterialsAPI.log("HTPartManager reloaded!")
    }
}
