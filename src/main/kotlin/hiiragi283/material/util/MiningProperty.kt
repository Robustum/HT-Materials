package hiiragi283.material.util

import hiiragi283.material.init.HiiragiTagRegistry
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager
import net.minecraft.block.Block
import net.minecraft.tag.TagKey
import pers.solid.brrp.v1.tag.IdentifiedTagBuilder

data class MiningProperty(
    val level: Int = 0,
    val tool: Set<TagKey<Block>> = setOf()
) {

    constructor(level: Int = 0, vararg tools: TagKey<Block>) : this(level, tools.toSet())

    val levelTag: TagKey<Block>? = if (level > 0) MiningLevelManager.getBlockTag(level) else null

    fun register(block: Block) {
        tool.map(HiiragiTagRegistry::getBlockBuilder)
            .forEach { tagBuilder: IdentifiedTagBuilder<Block> -> tagBuilder.add(block) }
        levelTag?.let { HiiragiTagRegistry.getBlockBuilder(it).add(block) }
    }

}