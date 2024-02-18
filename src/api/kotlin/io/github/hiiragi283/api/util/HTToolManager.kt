package io.github.hiiragi283.api.util

import net.fabricmc.fabric.impl.tool.attribute.ToolManagerImpl
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.Tag

@Suppress("UnstableApiUsage")
object HTToolManager {
    @JvmStatic
    private fun getEntry(block: Block) = ToolManagerImpl.entry(block)

    @JvmStatic
    fun setBreakByHand(block: Block, bool: Boolean) {
        getEntry(block).setBreakByHand(bool)
    }

    @JvmStatic
    fun putBreakByTool(block: Block, tag: Tag<Item>, miningLevel: Int) {
        getEntry(block).putBreakByTool(tag, miningLevel)
    }

    @JvmStatic
    fun getMiningLevel(block: Block, tag: Tag<Item>): Int = getEntry(block).getMiningLevel(tag)
}
