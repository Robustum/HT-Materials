package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.FormulaConvertible
import io.github.hiiragi283.api.material.MolarMassConvertible
import io.github.hiiragi283.api.material.element.HTMaterialInfoProvider
import io.github.hiiragi283.api.util.HTColor
import java.awt.Color

abstract class HTMaterialComposition {
    abstract fun componentMap(): Map<HTMaterialInfoProvider<*>, Int>

    abstract fun color(): Color

    abstract fun formula(): String

    abstract fun molar(): Double

    companion object {
        @JvmField
        val EMPTY: HTMaterialComposition = Empty

        @JvmStatic
        fun create(vararg pairs: Pair<HTMaterialInfoProvider<*>, Int>) = create(mapOf(*pairs))

        @JvmStatic
        fun create(map: Map<HTMaterialInfoProvider<*>, Int>): HTMaterialComposition = build {
            this.map.putAll(map)
        }

        @JvmStatic
        inline fun override(parent: HTMaterialComposition, builderAction: Builder.() -> Unit): HTMaterialComposition = build {
            map.putAll(parent.componentMap())
            color = parent::color
            formula = parent::formula
            molar = parent::molar
            builderAction()
        }

        @JvmStatic
        inline fun build(builderAction: Builder.() -> Unit): HTMaterialComposition = Builder().apply(builderAction).build()
    }

    private data object Empty : HTMaterialComposition() {
        override fun componentMap(): Map<HTMaterialInfoProvider<*>, Int> = mapOf()

        override fun color(): Color = HTColor.WHITE

        override fun formula(): String = ""

        override fun molar(): Double = 0.0
    }

    class Builder {
        var map: MutableMap<HTMaterialInfoProvider<*>, Int> = mutableMapOf()
        var color: () -> Color = ::averageColor
        var formula: () -> String = ::formatForula
        var molar: () -> Double = ::calculateMolar

        fun averageColor(): Color = ColorConvertible.average(map.mapKeys { it.key.color })

        fun formatForula(): String = FormulaConvertible.format(map.mapKeys { it.key.formula })

        fun calculateMolar(): Double = MolarMassConvertible.calculate(map.mapKeys { it.key.molar })

        fun build(): HTMaterialComposition = object : HTMaterialComposition() {
            override fun componentMap(): Map<HTMaterialInfoProvider<*>, Int> = map

            override fun color(): Color = this@Builder.color()

            override fun formula(): String = this@Builder.formula()

            override fun molar(): Double = this@Builder.molar()
        }
    }
}
