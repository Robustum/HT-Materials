package io.github.hiiragi283.material.api.material

fun interface MolarMassConvertible {

    fun asMolarMass(): Double

    companion object {

        @JvmField
        val EMPTY = MolarMassConvertible { 0.0 }

        @JvmStatic
        fun of(vararg molars: MolarMassConvertible) = of(molars.associateWith { 1 })

        @JvmStatic
        fun of(vararg pairs: Pair<MolarMassConvertible, Int>) = of(pairs.toMap())

        @JvmStatic
        fun of(map: Map<MolarMassConvertible, Int>) =
            MolarMassConvertible { calculate(map.mapKeys { (molar: MolarMassConvertible, _) -> molar.asMolarMass() }) }

        @JvmStatic
        fun ofDouble(vararg molars: Double) = ofDouble(molars.associateWith { 1 })

        @JvmStatic
        fun ofDouble(vararg pairs: Pair<Double, Int>) = ofDouble(pairs.toMap())

        @JvmStatic
        fun ofDouble(map: Map<Double, Int>) = MolarMassConvertible { calculate(map) }

        @JvmStatic
        fun calculate(molars: Iterable<Double>) = calculate(molars.associateWith { 1 })

        @JvmStatic
        fun calculate(map: Map<Double, Int>): Double = map.map { (molar: Double, weight: Int) -> molar * weight }.sum()

    }

}