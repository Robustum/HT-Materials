package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.HTItemGroup
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class HTMaterialItem(
    override val materialHT: HTMaterial,
    override val shapeHT: HTShape
) : Item(FabricItemSettings().group(HTItemGroup.MATERIAL)), HTMaterialItemConvertible {

    override fun getName(): Text = shapeHT.getTranslatedText(materialHT)

    override fun getName(stack: ItemStack): Text = shapeHT.getTranslatedText(materialHT)

}