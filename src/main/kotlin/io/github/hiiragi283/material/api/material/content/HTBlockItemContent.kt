package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class HTBlockItemContent(override val shapeKey: HTShapeKey, private val block: Block) : HTMaterialContent.Item {

    override fun create(materialKey: HTMaterialKey): Item = MaterialBlockItem(block, materialKey, shapeKey)

    class MaterialBlockItem(
        block: Block,
        val materialKey: HTMaterialKey,
        val shapeKey: HTShapeKey,
    ) : BlockItem(block, FabricItemSettings().group(HTMaterialsCommon.ITEM_GROUP)) {

        override fun asItem() = this

        override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)

    }

}