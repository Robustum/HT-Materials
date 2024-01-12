package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.Color

class HTMaterial private constructor(
    val key: HTMaterialKey,
    val color: Color,
    val formula: String,
    val molar: Double,
    val properties: HTMaterialPropertyMap,
    val flags: HTMaterialFlagSet
) {

    //    Properties    //

    fun <T : HTMaterialProperty<T>> getProperty(key: HTPropertyKey<T>): T? = key.objClass.cast(properties[key])

    fun hasProperty(key: HTPropertyKey<*>): Boolean = key in properties

    fun getDefaultShape(): HTShapeKey? = when {
        hasProperty(HTPropertyKey.METAL) -> HTShapes.INGOT
        hasProperty(HTPropertyKey.GEM) -> HTShapes.GEM
        else -> null
    }

    fun verify() {
        properties.values.forEach { it.verify(this) }
        flags.forEach { it.verify(this) }
    }

    //    Flags    //

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    //    Any    //

    override fun toString(): String = key.toString()

    companion object {

        private val LOGGER: Logger = LogManager.getLogger(HTMaterial::class.java)

        //    Registry    //

        private val registry: MutableMap<HTMaterialKey, HTMaterial> = linkedMapOf()

        @JvmField
        val REGISTRY: Map<HTMaterialKey, HTMaterial> = registry

        @JvmStatic
        fun getMaterialKeys(): Collection<HTMaterialKey> = registry.keys

        @JvmStatic
        fun getMaterials(): Collection<HTMaterial> = registry.values

        @JvmStatic
        fun getMaterial(key: HTMaterialKey): HTMaterial =
            registry[key] ?: throw IllegalStateException("Material: $key is not registered!")

        @JvmStatic
        fun getMaterialOrNull(key: HTMaterialKey): HTMaterial? = registry[key]

        @JvmStatic
        internal fun create(
            key: HTMaterialKey,
            color: Color,
            formula: String,
            molar: Double,
            properties: HTMaterialPropertyMap,
            flags: HTMaterialFlagSet
        ): HTMaterial = HTMaterial(key, color, formula, molar, properties, flags).also {
            registry.putIfAbsent(key, it)
            LOGGER.info("Material: $key registered!")
        }

        private val shapeKey = HTShapeKey("fluid")

        fun appendTooltip(material: HTMaterial, shapeKey: HTShapeKey?, stack: ItemStack, lines: MutableList<Text>) {
            //Title
            lines.add(TranslatableText("tooltip.ht_materials.material.title"))
            //Name
            val name: String = shapeKey?.getTranslatedName(material.key) ?: material.key.getTranslatedName()
            lines.add(TranslatableText("tooltip.ht_materials.material.name", name))
            //Formula
            material.formula.takeIf(String::isNotEmpty)?.let { formula: String ->
                lines.add(TranslatableText("tooltip.ht_materials.material.formula", formula))
            }
            //Molar Mass
            material.molar.takeIf { it > 0.0 }?.let { molar: Double ->
                lines.add(TranslatableText("tooltip.ht_materials.material.molar", molar))
            }
            //Tooltip from Properties
            material.properties.values.forEach { it.appendTooltip(material, shapeKey, stack, lines) }
        }

    }

}