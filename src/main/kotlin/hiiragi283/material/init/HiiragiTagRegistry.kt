package hiiragi283.material.init

import hiiragi283.material.RagiMaterials
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import org.apache.logging.log4j.LogManager
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.tag.IdentifiedTagBuilder

object HiiragiTagRegistry {

    private val logger = LogManager.getLogger("${RagiMaterials.MOD_NAME}/Tag")

    private val BLOCKS: MutableMap<TagKey<Block>, IdentifiedTagBuilder<Block>> = mutableMapOf()
    private val ITEMS: MutableMap<TagKey<Item>, IdentifiedTagBuilder<Item>> = mutableMapOf()

    @Synchronized
    fun getBlockBuilder(tagKey: TagKey<Block>): IdentifiedTagBuilder<Block> {
        BLOCKS.putIfAbsent(tagKey, IdentifiedTagBuilder.createBlock(tagKey))
        return BLOCKS[tagKey]!!
    }

    @Synchronized
    fun getItemBuilder(tagKey: TagKey<Item>): IdentifiedTagBuilder<Item> {
        ITEMS.putIfAbsent(tagKey, IdentifiedTagBuilder.createItem(tagKey))
        return ITEMS[tagKey]!!
    }

    fun register(resourcePack: RuntimeResourcePack) {
        BLOCKS.values.forEach(resourcePack::addTag)
        ITEMS.values.forEach(resourcePack::addTag)
        logger.info("All TagBuilder have been registered!")
    }

}