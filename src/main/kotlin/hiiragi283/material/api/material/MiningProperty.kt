package hiiragi283.material.api.material

import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager
import net.minecraft.block.Block
import net.minecraft.tag.TagKey

data class MiningProperty(
    val level: Int = 0,
    val tool: Set<TagKey<Block>> = setOf()
) {

    constructor(level: Int = 0, vararg tools: TagKey<Block>) : this(level, tools.toSet())

    val levelTag: TagKey<Block>? = if (level > 0) MiningLevelManager.getBlockTag(level) else null

}