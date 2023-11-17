package io.github.hiiragi283.material.common

import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import pers.solid.brrp.v1.tag.IdentifiedTagBuilder

object HTTagManager {

    private val blocks: MutableMap<TagKey<Block>, IdentifiedTagBuilder<Block>> = mutableMapOf()

    private val fluids: MutableMap<TagKey<Fluid>, IdentifiedTagBuilder<Fluid>> = mutableMapOf()

    private val items: MutableMap<TagKey<Item>, IdentifiedTagBuilder<Item>> = mutableMapOf()

    @JvmStatic
    fun registerBlockTags(tagKey: TagKey<Block>, vararg block: Block) {
        val builder: IdentifiedTagBuilder<Block> =
            blocks.computeIfAbsent(tagKey) { IdentifiedTagBuilder.createBlock(tagKey) }
        builder.add(*block)
    }

    @JvmStatic
    fun registerFluidTags(tagKey: TagKey<Fluid>, vararg fluid: Fluid) {
        val builder: IdentifiedTagBuilder<Fluid> =
            fluids.computeIfAbsent(tagKey) { IdentifiedTagBuilder.createFluid(tagKey) }
        builder.add(*fluid)
    }

    @JvmStatic
    fun registerItemTags(tagKey: TagKey<Item>, vararg item: Item) {
        val builder: IdentifiedTagBuilder<Item> =
            items.computeIfAbsent(tagKey) { IdentifiedTagBuilder.createItem(tagKey) }
        builder.add(*item)
    }

    @JvmStatic
    fun register() {
        blocks.values.forEach(HTMaterialsCommon.RESOURCE_PACK::addTag)
        fluids.values.forEach(HTMaterialsCommon.RESOURCE_PACK::addTag)
        items.values.forEach(HTMaterialsCommon.RESOURCE_PACK::addTag)
    }

}