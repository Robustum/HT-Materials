package io.github.hiiragi283.api.material

import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.element.HTMaterialInfoProvider
import io.github.hiiragi283.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.api.material.property.HTMaterialProperty
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.material.property.HTPropertyKey
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import java.awt.Color
import java.util.function.Consumer

class HTMaterial(
    val key: HTMaterialKey,
    private val composition: HTMaterialComposition,
    private val flags: HTMaterialFlagSet,
    private val properties: HTMaterialPropertyMap,
    val type: HTMaterialType,
) {
    //    Composition    //

    fun componentMap(): Map<HTMaterialInfoProvider<*>, Int> = composition.componentMap()

    fun color(): Color = composition.color()

    fun formula(): String = composition.formula()

    fun molar(): Double = composition.molar()

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

    fun getDefaultShape(): HTShapeKey? = when (type) {
        HTMaterialType.Gem.AMETHYST -> HTShapeKeys.GEM
        HTMaterialType.Gem.COAL -> HTShapeKeys.GEM
        HTMaterialType.Gem.CUBIC -> HTShapeKeys.GEM
        HTMaterialType.Gem.DIAMOND -> HTShapeKeys.GEM
        HTMaterialType.Gem.EMERALD -> HTShapeKeys.GEM
        HTMaterialType.Gem.LAPIS -> HTShapeKeys.GEM
        HTMaterialType.Gem.QUARTZ -> HTShapeKeys.GEM
        HTMaterialType.Gem.RUBY -> HTShapeKeys.GEM
        HTMaterialType.Metal -> HTShapeKeys.INGOT
        HTMaterialType.Stone -> null
        HTMaterialType.Undefined -> null
        HTMaterialType.Wood -> null
    }

    //    Any    //

    override fun toString(): String = key.toString()

    companion object {
        fun appendTooltip(
            material: HTMaterial,
            shapeKey: HTShapeKey?,
            stack: ItemStack,
            lines: MutableList<Text>,
        ) {
            // Title
            lines.add(TranslatableText("tooltip.ht_materials.material.title"))
            // Name
            val name: String = shapeKey?.getTranslatedName(material.key) ?: material.key.getTranslatedName()
            lines.add(TranslatableText("tooltip.ht_materials.material.name", name))
            // Type
            lines.add(TranslatableText("tooltip.ht_materials.material.type", material.type))
            // Formula
            material.formula().takeIf(String::isNotEmpty)?.let { formula: String ->
                lines.add(TranslatableText("tooltip.ht_materials.material.formula", formula))
            }
            // Molar Mass
            material.molar().takeIf { it > 0.0 }?.let { molar: Double ->
                lines.add(TranslatableText("tooltip.ht_materials.material.molar", molar))
            }
            // Tooltip from Properties
            material.properties.values.forEach { it.appendTooltip(material, shapeKey, stack, lines) }
        }
    }
}
