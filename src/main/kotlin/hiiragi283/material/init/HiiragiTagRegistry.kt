package hiiragi283.material.init

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.tag.IdentifiedTagBuilder

object HiiragiTagRegistry {

    private val BLOCKS: MutableMap<TagKey<Block>, IdentifiedTagBuilder<Block>> = mutableMapOf()
    private val ITEMS: MutableMap<TagKey<Item>, IdentifiedTagBuilder<Item>> = mutableMapOf()

    fun getBlockBuilder(tagKey: TagKey<Block>): IdentifiedTagBuilder<Block> {
        BLOCKS.putIfAbsent(tagKey, IdentifiedTagBuilder.createBlock(tagKey))
        return BLOCKS[tagKey]!!
    }

    fun getItemBuilder(tagKey: TagKey<Item>): IdentifiedTagBuilder<Item> {
        ITEMS.putIfAbsent(tagKey, IdentifiedTagBuilder.createItem(tagKey))
        return ITEMS[tagKey]!!
    }

    fun register(resourcePack: RuntimeResourcePack) {
        BLOCKS.values.forEach(resourcePack::addTag)
        ITEMS.values.forEach(resourcePack::addTag)
    }

}