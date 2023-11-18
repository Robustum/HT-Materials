package io.github.hiiragi283.material.api.part

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.TagKey
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

data class HTPart(val material: HTMaterial, val shape: HTShape) {

    fun getTranslatedName(): String = shape.getTranslatedName(material)

    fun getTranslatedText(): TranslatableText = shape.getTranslatedText(material)

    fun getIdentifier(namespace: String): Identifier = shape.getIdentifier(namespace, material)

    fun getCommonId(): Identifier = shape.getCommonId(material)

    fun getCommonTag(): TagKey<Item> = shape.getCommonTag(material)

    fun appendTooltip(stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {
        //Title
        lines.add(TranslatableText("tooltip.ht_materials.material.title"))
        //Name
        lines.add(TranslatableText("tooltip.ht_materials.material.name", getTranslatedName()))
        //Formula
        lines.add(TranslatableText("tooltip.ht_materials.material.formula", material.getFormula()))
        //Fluid amount per ingot
        lines.add(TranslatableText("tooltip.ht_materials.material.fluid_amount", material.getFluidAmountPerIngot()))
        //Tooltip from Properties
        material.getProperties().forEach { it.appendTooltip(this, stack, context, lines) }
    }

}