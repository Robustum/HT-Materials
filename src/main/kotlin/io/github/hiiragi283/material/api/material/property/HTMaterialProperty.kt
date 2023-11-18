package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPart
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

interface HTMaterialProperty<T : HTMaterialProperty<T>> {

    val key: HTPropertyKey<T>

    fun verify(material: HTMaterial)

    fun appendTooltip(part: HTPart, stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {}

}