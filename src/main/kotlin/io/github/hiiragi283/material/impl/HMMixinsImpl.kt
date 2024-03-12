package io.github.hiiragi283.material.impl

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.modify
import io.github.hiiragi283.api.tag.TagsBuildingEvent
import net.minecraft.resource.Resource
import net.minecraft.resource.ResourceManager
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import java.io.BufferedReader
import java.io.InputStream

object HMMixinsImpl {
    private val gson = Gson()

    @JvmStatic
    fun loadTagMap(manager: ResourceManager, dataType: String): Map<Identifier, Tag.Builder> {
        val result: MutableMap<Identifier, Tag.Builder> = hashMapOf()
        manager.findResources(dataType) { it.endsWith(".json") }.forEach { id: Identifier ->
            val tagId: Identifier = id.modify { it.removePrefix("$dataType/").removeSuffix(".json") }
            manager.getAllResources(id).forEach { resource: Resource ->
                resource.use {
                    resource
                        .let(Resource::getInputStream)
                        .let(InputStream::bufferedReader)
                        .let { reader: BufferedReader -> JsonHelper.deserialize(gson, reader, JsonObject::class.java) }
                        ?.let { jsonObject: JsonObject ->
                            result.computeIfAbsent(tagId) { Tag.Builder.create() }
                                .read(jsonObject, resource.resourcePackName)
                        }
                }
            }
        }
        // Invoke TagsBuildingEvent
        HTMaterialsAPI.LOGGER.info("Current loading tag type; $dataType")
        when (dataType) {
            "tags/blocks" -> TagsBuildingEvent.BLOCK
            "tags/items" -> TagsBuildingEvent.ITEM
            "tags/fluids" -> TagsBuildingEvent.FLUID
            "tags/entity_types" -> TagsBuildingEvent.ENTITY_TYPE
            else -> null
        }?.invoker()?.register(TagsBuildingEvent.Handler(result))
        // Remove empty builder
        HashMap(result).forEach { (id: Identifier, builder: Tag.Builder) ->
            if (!builder.streamEntries().findAny().isPresent) {
                result.remove(id)
            }
        }
        HTMaterialsAPI.LOGGER.info("Removed empty tag builders!")
        return result
    }
}
