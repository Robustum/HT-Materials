package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.HTItemGroup
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class MaterialItem(
    override val material: HTMaterial,
    override val shape: HTShape
) : Item(FabricItemSettings().group(HTItemGroup.MATERIAL)), MaterialItemConvertible {

    override fun getName(stack: ItemStack?): Text = shape.getTranslatedText(material)

}