package io.github.hiiragi283.api.material

import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.element.HTElement
import io.github.hiiragi283.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.api.material.property.HTMaterialProperty
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.material.property.HTPropertyKey
import io.github.hiiragi283.api.shape.HTShape
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import java.awt.Color
import java.util.function.Consumer

class HTMaterial(
    val key: HTMaterialKey,
    composition: HTMaterialComposition,
    private val flags: HTMaterialFlagSet,
    private val properties: HTMaterialPropertyMap,
    val type: HTMaterialType,
) {
    //    Composition    //

    val componentMap: Map<HTElement, Int> = composition.componentMap
    val color: Color = composition.color
    val formula: String = composition.formula
    val molar: Double = "%.1f".format(composition.molar).toDouble()

    //    Flags    //

    fun forEachFlag(consumer: Consumer<HTMaterialFlag>) {
        flags.forEach(consumer)
    }

    fun forEachFlag(action: (HTMaterialFlag) -> Unit) {
        flags.forEach(action)
    }

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    //    Properties    //

    fun forEachProperty(consumer: Consumer<HTMaterialProperty<*>>) {
        properties.values.forEach(consumer)
    }

    fun forEachProperty(action: (HTMaterialProperty<*>) -> Unit) {
        properties.values.forEach(action)
    }

    fun <T : HTMaterialProperty<T>> getProperty(key: HTPropertyKey<T>): T? = key.objClass.cast(properties[key])

    fun hasProperty(key: HTPropertyKey<*>): Boolean = key in properties

    //    Type    //

    val defaultShape: HTShape? = type.defaultShape

    //    Other    //

    fun appendTooltip(shapeKey: HTShape?, stack: ItemStack, lines: MutableList<Text>) {
        // Title
        lines.add(TranslatableText("tooltip.ht_materials.material.title"))
        // Name
        val name: String = shapeKey?.getTranslatedName(key) ?: key.translatedName
        lines.add(TranslatableText("tooltip.ht_materials.material.name", name))
        // Type
        lines.add(TranslatableText("tooltip.ht_materials.material.type", type))
        // Formula
        formula.takeIf(String::isNotEmpty)?.let { formula: String ->
            lines.add(TranslatableText("tooltip.ht_materials.material.formula", formula))
        }
        // Molar Mass
        molar.takeIf { it > 0.0 }?.let { molar: Double ->
            lines.add(TranslatableText("tooltip.ht_materials.material.molar", molar))
        }
        // Tooltip from Properties
        properties.values.forEach { it.appendTooltip(this, shapeKey, stack, lines) }
    }

    //    Any    //

    override fun toString(): String = key.toString()
}
