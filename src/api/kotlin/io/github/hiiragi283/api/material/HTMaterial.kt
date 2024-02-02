package io.github.hiiragi283.api.material

import io.github.hiiragi283.api.HTMaterialsAPI
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

class HTMaterial private constructor(
    val key: HTMaterialKey,
    val properties: HTMaterialPropertyMap,
    val flags: HTMaterialFlagSet,
    val color: Color,
    val formula: String,
    val molar: Double,
    val type: HTMaterialType,
) {
    //    Properties    //

    fun <T : HTMaterialProperty<T>> getProperty(key: HTPropertyKey<T>): T? = key.objClass.cast(properties[key])

    fun hasProperty(key: HTPropertyKey<*>): Boolean = key in properties

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

    //    Flags    //

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    //    Any    //

    override fun toString(): String = key.toString()

    companion object {
        //    Registry    //

        private val REGISTRY: MutableMap<HTMaterialKey, HTMaterial> = linkedMapOf()

        @JvmStatic
        fun getMaterialKeys(): Collection<HTMaterialKey> = REGISTRY.keys

        @JvmStatic
        fun create(
            key: HTMaterialKey,
            properties: HTMaterialPropertyMap,
            flags: HTMaterialFlagSet,
            color: Color,
            formula: String,
            molar: Double,
            type: HTMaterialType,
        ): HTMaterial = HTMaterial(key, properties, flags, color, formula, molar, type).also {
            REGISTRY.putIfAbsent(key, it)
            HTMaterialsAPI.log("Material: $key registered!")
        }

        fun appendTooltip(
            material: HTMaterial,
            shapeKey: HTShapeKey?,
            stack: ItemStack,
            lines: MutableList<Text>,
        ) {
            // Title
            lines.add(TranslatableText("tooltip.ht_materials.material.title"))
            // Name
            val name: String = shapeKey?.getShape()?.getTranslatedName(material.key) ?: material.key.getTranslatedName()
            lines.add(TranslatableText("tooltip.ht_materials.material.name", name))
            // Type
            lines.add(TranslatableText("tooltip.ht_materials.material.type", material.type))
            // Formula
            material.formula.takeIf(String::isNotEmpty)?.let { formula: String ->
                lines.add(TranslatableText("tooltip.ht_materials.material.formula", formula))
            }
            // Molar Mass
            material.molar.takeIf { it > 0.0 }?.let { molar: Double ->
                lines.add(TranslatableText("tooltip.ht_materials.material.molar", molar))
            }
            // Tooltip from Properties
            material.properties.values.forEach { it.appendTooltip(material, shapeKey, stack, lines) }
        }
    }
}
