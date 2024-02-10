package io.github.hiiragi283.forge

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.forge.mixin.TagBuilderAccessor
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraftforge.registries.ForgeRegistries

internal object HTTagLoaderMixin {
    private val Tag.Builder.entries: List<Tag.TrackedEntry>
        get() = (this as TagBuilderAccessor).entries

    @JvmStatic
    fun loadTags(map: MutableMap<Identifier, Tag.Builder>, entryType: String) {
        HTMaterialsAPI.log("Current entry type: $entryType")
        when (entryType) {
            "block" -> {}
            "block_entity_type" -> {}
            "enchantment" -> {}
            "entity_type" -> {}
            "fluid" -> fluidTags(map)
            "item" -> itemTags(map)
            "potion" -> {}
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
                ForgeRegistries.FLUIDS.getKey(fluid),
            )
        }
    }

    @JvmStatic
    fun itemTags(map: MutableMap<Identifier, Tag.Builder>) {
        // Register Tags from HTPartManager
        HTMaterialsAPI.INSTANCE.partManager().getAllEntries().forEach { entry ->
            val (materialKey: HTMaterialKey, shapeKey: HTShapeKey, item: Item) = entry
            // Shape tag
            registerTag(
                getOrCreateBuilder(map, shapeKey.getShapeId()),
                ForgeRegistries.ITEMS.getKey(item),
            )
            // Material tag
            registerTag(
                getOrCreateBuilder(map, materialKey.getMaterialId()),
                ForgeRegistries.ITEMS.getKey(item),
            )
            // Forge tag
            registerTag(
                getOrCreateBuilder(map, shapeKey.getShape().getCommonId(materialKey)),
                ForgeRegistries.ITEMS.getKey(item),
            )
        }
        HTMaterialsAPI.log("Registered Tags for HTPartManager's Entries!")
    }

    @JvmStatic
    private fun getOrCreateBuilder(map: MutableMap<Identifier, Tag.Builder>, id: Identifier) =
        map.computeIfAbsent(id) { Tag.Builder.create() }

    @JvmStatic
    private fun registerTag(builder: Tag.Builder, id: Identifier?) {
        builder.add(id, HTMaterialsAPI.MOD_NAME)
    }
}
