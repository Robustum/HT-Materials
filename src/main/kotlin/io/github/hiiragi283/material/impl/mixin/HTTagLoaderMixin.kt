package io.github.hiiragi283.material.impl.mixin

import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.part.getMaterialKey
import io.github.hiiragi283.material.api.part.getShapeKey
import io.github.hiiragi283.material.api.shape.HTShape
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
        // Register Tags from HTPartManager
        HTPartManager.getAllItems().forEach { item ->
            val materialKey: HTMaterialKey = item.getMaterialKey() ?: return@forEach
            val shapeKey: HTShapeKey = item.getShapeKey() ?: return@forEach
            registerTag(
                getOrCreateBuilder(map, shapeKey.getCommonTag(materialKey).id),
                Registry.ITEM,
                item,
            )
        }
        HTMixinLogger.INSTANCE.info("Registered Tags for HTPartManager's Entries!")
        // Sync ForgeTag and CommonTag entries
        HTMaterial.getMaterialKeys().forEach { materialKey: HTMaterialKey ->
            HTShape.getShapeKeys().forEach shape@{ shapeKey: HTShapeKey ->
                val forgeBuilder: Tag.Builder = getOrCreateBuilder(map, shapeKey.getForgeTag(materialKey))
                val commonBuilder: Tag.Builder = getOrCreateBuilder(map, shapeKey.getCommonTag(materialKey))
                syncBuilder(commonBuilder, forgeBuilder)
                syncBuilder(forgeBuilder, commonBuilder)
            }
        }
        HTMixinLogger.INSTANCE.info("Synced Forge Tags and Common Tags!")
    }

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, tagKey: Tag.Identified<*>): Tag.Builder =
        getOrCreateBuilder(map, tagKey.id)

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, id: Identifier) =
        map.computeIfAbsent(id) { Tag.Builder.create() }

    @JvmStatic
    private fun <T> registerTag(builder: Tag.Builder, registry: Registry<T>, value: T) {
        builder.add(registry.getId(value), HTMaterials.MOD_NAME)
    }

    @JvmStatic
    private fun syncBuilder(parentBuilder: Tag.Builder, childBuilder: Tag.Builder) {
        childBuilder.entries.forEach(parentBuilder::add)
    }
}
