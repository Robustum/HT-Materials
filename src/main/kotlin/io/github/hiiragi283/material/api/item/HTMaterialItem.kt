package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class HTMaterialItem(
    override val materialKey: HTMaterialKey,
    override val shapeKey: HTShapeKey
) : Item(FabricItemSettings().group(HTMaterialsCommon.ITEM_GROUP)), HTMaterialItemConvertible {

    override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

    override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)

}