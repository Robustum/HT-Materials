package io.github.hiiragi283.material.api.material.property

import dev.architectury.registry.block.BlockPropertiesExtension
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import io.github.hiiragi283.material.common.util.asBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.fabricmc.fabric.impl.item.FabricItemInternals
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.Tag
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

class HTSolidProperty private constructor(
    val blockSettings: FabricBlockSettings,
    var harvestLevel: Int,
    var harvestTool: Tag<Item>?
) : HTMaterialProperty<HTSolidProperty> {

    override val key: HTPropertyKey<HTSolidProperty> = HTPropertyKey.SOLID

    override fun verify(material: HTMaterial) {

    }

    override fun appendTooltip(part: HTPart, stack: ItemStack, lines: MutableList<Text>) {
        /*val block = stack.item.asBlock()


        //Mining Level
        blockSettings.let { lines.add(TranslatableText("tooltip.ht_materials.material.mining_level", it)) }
        //Mining Tool
        (harvestTool as? Tag.Identified<Item>)?.id?.let {
            lines.add(TranslatableText("tooltip.ht_materials.material.mining_tool", it))
        }*/
    }

    companion object {

        @JvmStatic
        fun createSolid(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.SAND),
            0,
            FabricToolTags.SHOVELS
        )

        @JvmStatic
        fun createGem(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK).requiresTool(),
            1,
            FabricToolTags.PICKAXES
        )

        @JvmStatic
        fun createMetal(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).requiresTool(),
            1,
            FabricToolTags.PICKAXES
        )

        @JvmStatic
        fun createStone(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.STONE).requiresTool(),
            0,
            FabricToolTags.SHOVELS
        )

        @JvmStatic
        fun createWood(): HTSolidProperty = HTSolidProperty(
            FabricBlockSettings.copyOf(Blocks.OAK_PLANKS),
            0,
            FabricToolTags.AXES
        )

    }

}