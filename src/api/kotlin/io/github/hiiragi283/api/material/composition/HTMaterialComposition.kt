package io.github.hiiragi283.api.material.composition

import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import io.github.hiiragi283.api.extension.*
import io.github.hiiragi283.api.material.element.HTElement
import io.github.hiiragi283.api.material.element.HTElements
import net.minecraft.util.JsonHelper
import java.awt.Color
import kotlin.jvm.optionals.getOrNull

abstract class HTMaterialComposition {
    abstract val componentMap: Map<HTElement, Int>
    abstract val color: Color
    abstract val formula: String
    abstract val molar: Double

    companion object {
        @JvmField
        val EMPTY: HTMaterialComposition = Empty

        @JvmStatic
        fun json(jsonObject: JsonObject): HTMaterialComposition {
            val components: Map<HTElement, Int> = JsonHelper.getArray(jsonObject, "components")
                .mapNotNull { it as? JsonObject }
                .mapNotNull { jsonObject1: JsonObject ->
                    val weight: Int = JsonHelper.getInt(jsonObject1, "weight", 1)
                    JsonHelper.getObject(jsonObject1, "element", null)
                        ?.let { HTElement.CODEC.parse(JsonOps.INSTANCE, it) }
                        ?.result()
                        ?.getOrNull()
                        ?.let { it to weight }
                }.toMap()
            val color: Color? = JsonHelper.getObject(jsonObject, "color", null)
                ?.let { ColorCodec.parse(JsonOps.INSTANCE, it) }
                ?.result()
                ?.getOrNull()
            val formula: String? = JsonHelper.getString(jsonObject, "formula", null)
            val molar: Double? = if (jsonObject.has("molar")) jsonObject.getAsJsonPrimitive("molar").asDouble else null
            return Mutable(components).apply {
                color?.let { this.color = it }
                formula?.let { this.formula = it }
                molar?.let { this.molar = it }
            }
        }

        //    Molecular    //

        @JvmOverloads
        @JvmStatic
        inline fun molecular(vararg pairs: Pair<HTElement, Int>, builderAction: Mutable.() -> Unit = {}): HTMaterialComposition =
            molecular(mapOf(*pairs), builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun molecular(map: Map<HTElement, Int>, builderAction: Mutable.() -> Unit = {}): HTMaterialComposition =
            Mutable(map).apply(builderAction)

        //    Hydrate    //

        @JvmOverloads
        @JvmStatic
        inline fun hydrate(
            vararg pairs: Pair<HTElement, Int>,
            waterCount: Int,
            builderAction: Mutable.() -> Unit = {},
        ): HTMaterialComposition = hydrate(molecular(*pairs), waterCount, builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun hydrate(
            unhydrate: HTMaterialComposition,
            waterCount: Int,
            builderAction: Mutable.() -> Unit = {},
        ): HTMaterialComposition = Mutable(
            buildMap {
                putAll(unhydrate.componentMap)
                put(HTElements.WATER, waterCount)
            },
            HTColor.WHITE,
            "${unhydrate.formula}-${waterCount}H₂O",
            unhydrate.molar + waterCount * 18.0,
        ).apply(builderAction)

        //    Mixture    //

        @JvmOverloads
        @JvmStatic
        inline fun mixture(vararg providers: HTElement, builderAction: Mutable.() -> Unit = {}): HTMaterialComposition =
            mixture(providers.toList(), builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun mixture(elements: Iterable<HTElement>, builderAction: Mutable.() -> Unit = {}): HTMaterialComposition = Mutable(
            elements.associateWith { 1 },
            averageColor(elements.map(HTElement::color)),
            "",
            0.0,
        ).apply(builderAction)

        //    Polymer    //

        @JvmOverloads
        @JvmStatic
        inline fun polymer(vararg pairs: Pair<HTElement, Int>, builderAction: Mutable.() -> Unit = {}): HTMaterialComposition =
            polymer(molecular(*pairs), builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun polymer(monomar: HTMaterialComposition, builderAction: Mutable.() -> Unit = {}): HTMaterialComposition = Mutable(
            monomar.componentMap,
            monomar.color,
            "(${monomar.formula})n",
            0.0,
        ).apply(builderAction)
    }

    private data object Empty : HTMaterialComposition() {
        override val componentMap: Map<HTElement, Int> = mapOf()
        override val color: Color = HTColor.WHITE
        override val formula: String = ""
        override val molar: Double = 0.0
    }

    data class Mutable(
        override var componentMap: Map<HTElement, Int>,
        override var color: Color = averageColor(componentMap.mapKeys { it.key.color }),
        override var formula: String = formatFormula(componentMap.mapKeys { it.key.formula }),
        override var molar: Double = calculateMolar(componentMap.mapKeys { it.key.molar }),
    ) : HTMaterialComposition()
}
