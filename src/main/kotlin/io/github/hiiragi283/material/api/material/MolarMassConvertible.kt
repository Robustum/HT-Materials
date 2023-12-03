package io.github.hiiragi283.material.api.material

fun interface MolarMassConvertible {

    fun asMolarMass(): Double

    companion object {

        @JvmField
        val EMPTY = MolarMassConvertible { 0.0 }

        @JvmStatic
        fun of(vararg pair: Pair<MolarMassConvertible, Int>) = of(pair.toMap())

        @JvmStatic
        fun of(map: Map<MolarMassConvertible, Int>) = MolarMassConvertible {
            var result = 0.0
            for ((molar: MolarMassConvertible, weight: Int) in map) {
                result += molar.asMolarMass() * weight
            }
            result
        }

    }

}