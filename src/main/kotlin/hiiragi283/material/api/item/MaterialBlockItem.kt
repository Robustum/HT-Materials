package hiiragi283.material.api.item

import hiiragi283.material.api.block.MaterialBlock
import hiiragi283.material.api.material.MaterialItemConvertible
import hiiragi283.material.api.part.HiiragiPart
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class MaterialBlockItem(
    block: MaterialBlock,
    settings: FabricItemSettings
) : HiiragiBlockItem(block, settings), MaterialItemConvertible {

    override val part: HiiragiPart = block.part

    //    General    //

    override fun getName(stack: ItemStack): Text = part.getText()

}