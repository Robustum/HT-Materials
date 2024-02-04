package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.material.mixin.TagBuilderAccessor
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

internal object HTTagLoaderMixin {
    private val Tag.Builder.entries: List<Tag.TrackedEntry>
        get() = (this as TagBuilderAccessor).entries

    @JvmStatic
    fun loadTags(map: MutableMap<Identifier, Tag.Builder>, entryType: String) {
        HTMaterialsAPI.log("Current entry type: $entryType")
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
        HTMaterialsAPI.log("Removed empty tag builders!")
    }

    @JvmStatic
    fun fluidTags(map: MutableMap<Identifier, Tag.Builder>) {
        // Register Tags from HTFluidManagerOld
        HTMaterialsAPI.INSTANCE.fluidManager().materialToFluidsMap.forEach { key: HTMaterialKey, fluid: Fluid ->
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
                // copy builder to part id
                map[it] = builder
                // remove original id
                map.remove(id)
                HTMaterialsAPI.log("Migrated tag builder: $id -> $it")
            }
        }
        HTMaterialsAPI.log("Converted existing tags!")
        // Register Tags from HTPartManagerOld
        HTMaterialsAPI.INSTANCE.partManager().getAllEntries().forEach { entry ->
            val (materialKey: HTMaterialKey, shapeKey: HTShapeKey, item: Item) = entry
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
                getOrCreateBuilder(map, HTPart(materialKey, shapeKey).getPartId()),
                Registry.ITEM,
                item,
            )
        }
        HTMaterialsAPI.log("Registered Tags for HTPartManagerOld's Entries!")
    }

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, id: Identifier) =
        map.computeIfAbsent(id) { Tag.Builder.create() }

    @JvmStatic
    private fun <T> registerTag(builder: Tag.Builder, registry: Registry<T>, value: T) {
        builder.add(registry.getId(value), HTMaterialsAPI.MOD_NAME)
    }
}
