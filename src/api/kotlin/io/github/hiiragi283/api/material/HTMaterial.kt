package io.github.hiiragi283.api.material

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.element.HTElement
import io.github.hiiragi283.api.material.property.HTMaterialProperty
import io.github.hiiragi283.api.material.property.HTPropertyType
import io.github.hiiragi283.api.shape.HTShape
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import java.awt.Color
import java.util.function.Consumer

class HTMaterial(
    val key: HTMaterialKey,
    composition: HTMaterialComposition,
    private val flags: Set<Identifier>,
    private val properties: Map<HTPropertyType<*>, HTMaterialProperty<*>>,
    val type: HTMaterialType,
) {
    constructor(key: HTMaterialKey, builder: Builder) : this(
        key,
        builder.composition,
        builder.flags,
        builder.properties,
        builder.type,
    )

    //    Composition    //

    val componentMap: Map<HTElement, Int> = composition.componentMap
    val color: Color = composition.color
    val formula: String = composition.formula
    val molar: Double = "%.1f".format(composition.molar).toDouble()

    //    Flags    //

    fun forEachFlag(consumer: Consumer<Identifier>) {
        flags.forEach(consumer)
    }

    fun forEachFlag(action: (Identifier) -> Unit) {
        flags.forEach(action)
    }

    fun hasFlag(flag: Identifier): Boolean = flag in flags

    //    Properties    //

    fun forEachProperty(consumer: Consumer<HTMaterialProperty<*>>) {
        properties.values.forEach(consumer)
    }

    fun forEachProperty(action: (HTMaterialProperty<*>) -> Unit) {
        properties.values.forEach(action)
    }

    fun <T : HTMaterialProperty<T>> getProperty(type: HTPropertyType<T>): T? = type.cast(properties[type])

    fun hasProperty(type: HTPropertyType<*>): Boolean = type in properties

    //    Type    //

    val defaultShape: HTShape? = type.defaultShape

    //    Other    //

    fun appendTooltip(shape: HTShape?, stack: ItemStack, lines: MutableList<Text>) {
        // Title
        lines.add(TranslatableText("tooltip.ht_materials.material.title"))
        // Name
        val name: String = shape?.getTranslatedName(key) ?: key.translatedName
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
        properties.values.forEach { it.appendTooltip(this, shape, stack, lines) }
    }

    //    Any    //

    override fun toString(): String = key.toString()

    //    Builder    //

    class Builder {
        var composition: HTMaterialComposition = HTMaterialComposition.EMPTY
        val flags: MutableSet<Identifier> = hashSetOf()
        internal val properties: MutableMap<HTPropertyType<*>, HTMaterialProperty<*>> = hashMapOf()
        var type: HTMaterialType = HTMaterialType.Undefined

        fun addProperty(property: HTMaterialProperty<*>) {
            properties[property.type] = property
        }

        fun removeProperty(key: HTPropertyType<*>) {
            properties.remove(key)
        }

        fun merge(jsonObject: JsonObject) {
            val composition: HTMaterialComposition? = JsonHelper.getObject(jsonObject, "composition", null)
                ?.let(HTMaterialComposition.Companion::json)
            val flags: Collection<Identifier>? = JsonHelper.getArray(jsonObject, "flags", null)
                ?.map(JsonElement::getAsString)
                ?.map(::Identifier)
            val properties: Map<HTPropertyType<*>, HTMaterialProperty<*>>? =
                JsonHelper.getArray(jsonObject, "properties", null)
                    ?.mapNotNull { it as? JsonObject }
                    ?.map(HTPropertyType.Companion::deserializeJson)
                    ?.associate { it.type to it }
            merge(composition, flags, properties, null)
        }

        fun merge(
            composition: HTMaterialComposition?,
            flags: Collection<Identifier>?,
            properties: Map<HTPropertyType<*>, HTMaterialProperty<*>>?,
            type: HTMaterialType?,
        ) {
            composition?.let { this.composition = it }
            flags?.forEach(this.flags::add)
            properties?.forEach(this.properties::put)
            type?.let { this.type = it }
        }
    }
}
