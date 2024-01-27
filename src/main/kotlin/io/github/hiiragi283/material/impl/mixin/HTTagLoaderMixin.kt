package io.github.hiiragi283.material.impl.mixin

import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.part.getMaterialKey
import io.github.hiiragi283.material.api.part.getShapeKey
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.mixin.TagBuilderAccessor
import io.github.hiiragi283.material.util.HTMixinLogger
import net.minecraft.fluid.Fluid
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

internal object HTTagLoaderMixin {
    private val Tag.Builder.entries: List<Tag.TrackedEntry>
        get() = (this as TagBuilderAccessor).entries

    @JvmStatic
    fun loadTags(map: MutableMap<Identifier, Tag.Builder>, entryType: String) {
        HTMixinLogger.INSTANCE.info("Current entry type: $entryType")
        when (entryType) {
            "block" -> {}
            "entity_type" -> {}
            "fluid" -> fluidTags(map)
            "item" -> itemTags(map)
            else -> {}
        }
        // Remove Empty Builder
        HashMap(map).forEach { (id: Identifier, builder: Tag.Builder) ->
            if (builder.entries.isEmpty()) {
                map.remove(id)
            }
        }
        HTMixinLogger.INSTANCE.info("Removed empty tag builders!")
    }

    @JvmStatic
    fun fluidTags(map: MutableMap<Identifier, Tag.Builder>) {
        // Register Tags from HTFluidManager
        HTFluidManager.getMaterialToFluidsMap().forEach { key: HTMaterialKey, fluid: Fluid ->
            registerTag(
                getOrCreateBuilder(map, key.getCommonId()),
                Registry.FLUID,
                fluid,
            )
        }
    }

    @JvmStatic
    fun itemTags(map: MutableMap<Identifier, Tag.Builder>) {
        // Convert tags into part format
        HashMap(map).forEach { (id: Identifier, builder: Tag.Builder) ->
            HTPart.fromId(id)?.getPartId()?.let {
                // copy builder to converted id
                map[it] = builder
                // builder with original id replaced with redirected one
                map[id] = Tag.Builder.create().apply { addTag(it, HTMaterials.MOD_NAME) }
            }
        }
        HTMixinLogger.INSTANCE.info("Converted existing tags!")
        // Register Tags from HTPartManager
        HTPartManager.getAllItems().forEach { item ->
            val materialKey: HTMaterialKey = item.getMaterialKey() ?: return@forEach
            val shapeKey: HTShapeKey = item.getShapeKey() ?: return@forEach
            // Shape tag
            registerTag(
                getOrCreateBuilder(map, shapeKey.getShapeId()),
                Registry.ITEM,
                item,
            )
            // Material tag
            registerTag(
                getOrCreateBuilder(map, materialKey.getMaterialId()),
                Registry.ITEM,
                item,
            )
            // Part tag
            registerTag(
                getOrCreateBuilder(map, shapeKey.getPartId(materialKey)),
                Registry.ITEM,
                item,
            )
        }
        HTMixinLogger.INSTANCE.info("Registered Tags for HTPartManager's Entries!")
    }

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, id: Identifier) =
        map.computeIfAbsent(id) { Tag.Builder.create() }

    @JvmStatic
    private fun <T> registerTag(builder: Tag.Builder, registry: Registry<T>, value: T) {
        builder.add(registry.getId(value), HTMaterials.MOD_NAME)
    }
}
