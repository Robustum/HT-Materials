package io.github.hiiragi283.forge

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.modify
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.resource.Resource
import net.minecraft.resource.ResourceManager
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.resource.IResourceType
import net.minecraftforge.resource.ISelectiveResourceReloadListener
import java.util.*
import java.util.function.Predicate

object HTTagReloadListener : ISelectiveResourceReloadListener {
    @JvmStatic
    val GSON = Gson()

    override fun onResourceManagerReload(manager: ResourceManager, predicate: Predicate<IResourceType>) {
        // Reload Fluid Manager
        val fluidTagMap: Map<Identifier, Tag<Fluid>> =
            getTagMap("tags/fluids", manager, ForgeRegistries.FLUIDS::getValue)
        HTMaterialsAPIForge.fluidManager = HTFluidManager.Builder().apply {
            // Register fluids from common tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                fluidTagMap[materialKey.getCommonId()]?.values()?.forEach { fluid ->
                    add(materialKey, fluid)
                }
            }
        }.let(::HTFluidManager)
        HTMaterialsAPI.log("Reloaded Fluid Manager!")

        // Reload Part Manager
        val itemTagMap: Map<Identifier, Tag<Item>> =
            getTagMap("tags/items", manager, ForgeRegistries.ITEMS::getValue)
        HTMaterialsAPIForge.partManager = HTPartManager.Builder().apply {
            // Register items from part tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                HTMaterialsAPI.INSTANCE.shapeRegistry().getKeys().forEach { shapeKey: HTShapeKey ->
                    itemTagMap[HTPart(materialKey, shapeKey).getPartId()]?.values()?.forEach { item ->
                        add(materialKey, shapeKey, item)
                    }
                }
            }
        }.let(::HTPartManager)
        HTMaterialsAPI.log("Reloaded Part Manager!")
    }

    private fun <T> getTagMap(prefix: String, manager: ResourceManager, getter: (Identifier) -> T?): Map<Identifier, Tag<T>> {
        // Load tag builder map from json
        val builderMap: MutableMap<Identifier, Tag.Builder> =
            manager.findResources(prefix) { it.endsWith(".json") }.associate { id ->
                val resource: Resource = manager.getResource(id)
                val builder: Tag.Builder = Tag.Builder.create()
                resource.inputStream
                    .reader()
                    .buffered()
                    .let { JsonHelper.deserialize(GSON, it, JsonObject::class.java) }
                    ?.let { builder.read(it, resource.resourcePackName) }
                id.modify { it.removePrefix(prefix).removeSuffix(".json") } to builder
            }.toMutableMap()
        // Create tags from tag builder map
        val tagMap: MutableMap<Identifier, Tag<T>> = hashMapOf()
        while (builderMap.isNotEmpty()) {
            var done = false
            val iterator: MutableIterator<MutableMap.MutableEntry<Identifier, Tag.Builder>> =
                builderMap.entries.iterator()
            while (iterator.hasNext()) {
                val (id: Identifier, builder: Tag.Builder) = iterator.next()
                val optionalTag: Optional<Tag<T>> = builder.build(tagMap::get, getter)
                if (!optionalTag.isPresent) continue
                tagMap[id] = optionalTag.get()
                iterator.remove()
                done = true
            }
            if (done) continue
            break
        }
        return tagMap
    }
}
