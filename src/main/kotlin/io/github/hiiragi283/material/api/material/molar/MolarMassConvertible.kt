package io.github.hiiragi283.material.api.material.molar

/**
 * 1 Unit = 0.001 g/mol = 1 mg/mol
 * 1000 Unit = 1 g/mol
 */
fun interface MolarMassConvertible {

    fun asMolarMass(): Int

    companion object {

        @JvmField
        val EMPTY = MolarMassConvertible { 0 }

        @JvmStatic
        fun of(vararg pair: Pair<MolarMassConvertible, Int>) = of(pair.toMap())

        @JvmStatic
        fun of(map: Map<MolarMassConvertible, Int>) = MolarMassConvertible {
            var result: Int = 0
            for ((molar: MolarMassConvertible, weight: Int) in map) {
                result += molar.asMolarMass() * weight
            }
            result
        }

    }

}