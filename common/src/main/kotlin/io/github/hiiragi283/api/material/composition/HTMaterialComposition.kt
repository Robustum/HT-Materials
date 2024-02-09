package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.FormulaConvertible
import io.github.hiiragi283.api.material.MolarMassConvertible
import io.github.hiiragi283.api.material.element.HTElement
import io.github.hiiragi283.api.util.HTColor
import java.awt.Color

abstract class HTMaterialComposition {
    abstract val componentMap: Map<HTElement<*>, Int>
    abstract val color: Color
    abstract val formula: String
    abstract val molar: Double

    companion object {
        @JvmField
        val EMPTY: HTMaterialComposition = Empty

        @JvmOverloads
        @JvmStatic
        inline fun molecular(vararg pairs: Pair<HTElement<*>, Int>, builderAction: Molecular.() -> Unit = {}): HTMaterialComposition =
            molecular(mapOf(*pairs), builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun molecular(map: Map<HTElement<*>, Int>, builderAction: Molecular.() -> Unit = {}): HTMaterialComposition =
            Molecular(map).apply(builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun hydrate(
            vararg pairs: Pair<HTElement<*>, Int>,
            waterCount: Int,
            builderAction: Hydrate.() -> Unit = {},
        ): HTMaterialComposition = hydrate(molecular(*pairs), waterCount, builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun hydrate(
            unhydrate: HTMaterialComposition,
            waterCount: Int,
            builderAction: Hydrate.() -> Unit = {},
        ): HTMaterialComposition = Hydrate(unhydrate, waterCount).apply(builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun mixture(vararg providers: HTElement<*>, builderAction: Mixture.() -> Unit = {}): HTMaterialComposition =
            mixture(providers.toList(), builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun mixture(elements: Iterable<HTElement<*>>, builderAction: Mixture.() -> Unit = {}): HTMaterialComposition =
            Mixture(elements).apply(builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun polymer(vararg pairs: Pair<HTElement<*>, Int>, builderAction: Polymer.() -> Unit = {}): HTMaterialComposition =
            polymer(molecular(*pairs), builderAction)

        @JvmOverloads
        @JvmStatic
        inline fun polymer(monomar: HTMaterialComposition, builderAction: Polymer.() -> Unit = {}): HTMaterialComposition =
            Polymer(monomar).apply(builderAction)
    }

    private data object Empty : HTMaterialComposition() {
        override val componentMap: Map<HTElement<*>, Int> = mapOf()
        override val color: Color = HTColor.WHITE
        override val formula: String = ""
        override val molar: Double = 0.0
    }

    class Molecular(
        override var componentMap: Map<HTElement<*>, Int>,
        override var color: Color = ColorConvertible.average(componentMap.mapKeys { it.key.color }),
        override var formula: String = FormulaConvertible.format(componentMap.mapKeys { it.key.formula }),
        override var molar: Double = MolarMassConvertible.calculate(componentMap.mapKeys { it.key.molar }),
    ) : HTMaterialComposition()

    class Hydrate(
        unhydrate: HTMaterialComposition,
        waterCount: Int,
        override var color: Color = HTColor.WHITE,
        override var formula: String = "${unhydrate.formula}-${waterCount}H₂O",
        override var molar: Double = unhydrate.molar + waterCount * 18.0,
    ) : HTMaterialComposition() {
        override val componentMap: Map<HTElement<*>, Int> = buildMap {
            putAll(unhydrate.componentMap)
        }
    }

    class Mixture(
        elements: Iterable<HTElement<*>>,
        override var color: Color = ColorConvertible.average(elements.map(HTElement<*>::color)),
        override var formula: String = "",
        override var molar: Double = 0.0,
    ) : HTMaterialComposition() {
        override val componentMap: Map<HTElement<*>, Int> = elements.associateWith { 1 }
    }

    class Polymer(
        monomar: HTMaterialComposition,
        override var color: Color = monomar.color,
        override var formula: String = "(${monomar.formula})n",
        override var molar: Double = 0.0,
    ) : HTMaterialComposition() {
        override val componentMap: Map<HTElement<*>, Int> = monomar.componentMap
    }
}
