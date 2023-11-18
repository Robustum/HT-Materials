package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.tag.BlockTags
import net.minecraft.tag.TagKey
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

class HTSolidProperty private constructor(
    val blockSettings: FabricBlockSettings,
    var harvestLevel: Int,
    var harvestTool: TagKey<Block>?
) : HTMaterialProperty<HTSolidProperty> {

    companion object {

        @JvmStatic
        fun createDust(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.SAND),
            0,
            BlockTags.SHOVEL_MINEABLE
        )

        @JvmStatic
        fun createGem(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).requiresTool(),
            1,
            BlockTags.PICKAXE_MINEABLE
        )

        @JvmStatic
        fun createMetal(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).requiresTool(),
            1,
            BlockTags.PICKAXE_MINEABLE
        )

        @JvmStatic
        fun createStone(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.STONE).requiresTool(),
            0,
            BlockTags.PICKAXE_MINEABLE
        )

        @JvmStatic
        fun createWood(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.OAK_PLANKS),
            0,
            BlockTags.AXE_MINEABLE
        )

    }

    override val key: HTPropertyKey<HTSolidProperty> = HTPropertyKey.SOLID

    override fun verify(material: HTMaterial) {

    }

    fun getHarvestLevelTag(): TagKey<Block>? = harvestLevel.takeIf { it > 0 }?.let(MiningLevelManager::getBlockTag)

    override fun appendTooltip(part: HTPart, stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {
        //Mining Level
        harvestLevel.let { lines.add(TranslatableText("tooltip.ht_materials.material.mining_level", it)) }
        //Mining Tool
        harvestTool?.id?.let { lines.add(TranslatableText("tooltip.ht_materials.material.mining_tool", it)) }
    }

}