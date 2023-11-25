package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.api.block.HTMaterialBlock
import io.github.hiiragi283.material.common.HTMaterialsCommon
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class HTMaterialBlockItem(
    private val block: HTMaterialBlock
) : BlockItem(block, FabricItemSettings().group(HTMaterialsCommon.ITEM_GROUP)),
    HTMaterialItemConvertible by block {

    override fun getName(): Text = shapeHT.getTranslatedText(materialHT)

    override fun getName(stack: ItemStack): Text = shapeHT.getTranslatedText(materialHT)

    override fun asItem(): Item = block.asItem()

}