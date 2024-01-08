package io.github.hiiragi283.material

import com.google.common.collect.Table
import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.mixin.TagBuilderAccessor
import io.github.hiiragi283.material.util.HTMixinLogger
import io.github.hiiragi283.material.util.forEach
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

internal object HTTagLoaderMixin {

    @JvmStatic
    fun loadTags(map: MutableMap<Identifier, Tag.Builder>, entryType: String) {
        when (entryType) {
            "block" -> {}
            "entity_type" -> {}
            "fluid" -> fluidTags(map)
            "item" -> itemTags(map)
            else -> {}
        }
    }

    @JvmStatic
    fun fluidTags(map: MutableMap<Identifier, Tag.Builder>) {
        //Register Tags from HTFluidManager
        HTFluidManager.getMaterialToFluidsMap().forEach { key: HTMaterialKey, fluid: Fluid ->
            registerTag(
                getOrCreateBuilder(map, key.getCommonId()),
                Registry.FLUID,
                fluid
            )
        }
    }

    @JvmStatic
    fun itemTags(map: MutableMap<Identifier, Tag.Builder>) {
        //Register Tags from HTPartManager
        HTPartManager.getPartToItemTable().forEach { cell: Table.Cell<HTMaterialKey, HTShapeKey, Collection<Item>> ->
            val materialKey: HTMaterialKey = cell.rowKey ?: return@forEach
            val shapeKey: HTShapeKey = cell.columnKey ?: return@forEach
            val items: Collection<Item> = cell.value ?: return@forEach
            items.forEach { item: Item ->
                registerTag(
                    getOrCreateBuilder(map, shapeKey.getCommonTag(materialKey).id),
                    Registry.ITEM,
                    item
                )
            }
        }
        HTMixinLogger.INSTANCE.info("Registered Tags for HTPartManager's Entries!")
        //Sync ForgeTag and CommonTag entries
        HTMaterial.REGISTRY.keys.forEach { materialKey: HTMaterialKey ->
            HTShape.REGISTRY.keys.forEach shape@{ shapeKey: HTShapeKey ->
                val forgeBuilder: Tag.Builder = getOrCreateBuilder(map, shapeKey.getForgeTag(materialKey))
                val commonBuilder: Tag.Builder = getOrCreateBuilder(map, shapeKey.getCommonTag(materialKey))
                syncBuilder(commonBuilder, forgeBuilder)
                syncBuilder(forgeBuilder, commonBuilder)
            }
        }
        HTMixinLogger.INSTANCE.info("Synced Forge Tags and Common Tags!")
        //Remove Empty Builder
        HashMap(map).forEach { (id: Identifier, builder: Tag.Builder) ->
            if ((builder as TagBuilderAccessor).entries.isEmpty()) {
                map.remove(id)
            }
        }
        HTMixinLogger.INSTANCE.info("Removed empty tag builders!")
    }

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, tagKey: Tag.Identified<*>): Tag.Builder =
        getOrCreateBuilder(map, tagKey.id)

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, id: Identifier) =
        map.computeIfAbsent(id) { Tag.Builder.create() }

    @JvmStatic
    private fun <T> registerTag(builder: Tag.Builder, registry: Registry<T>, value: T) {
        builder.add(registry.getId(value), HTMaterialsCommon.MOD_NAME)
    }

    @JvmStatic
    private fun syncBuilder(parentBuilder: Tag.Builder, childBuilder: Tag.Builder) {
        (childBuilder as TagBuilderAccessor).entries.forEach(parentBuilder::add)
    }

}