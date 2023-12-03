package io.github.hiiragi283.material.api.material

import java.awt.Color

@JvmDefaultWithCompatibility
fun interface ColorConvertible {

    fun asColor(): Color

    companion object {

        @JvmField
        val EMPTY = ColorConvertible { Color.WHITE }

        @JvmStatic
        fun ofColor(vararg colors: Color) = of(colors.toList().associate { ColorConvertible { it } to 1 })

        @JvmStatic
        fun ofColor(vararg pair: Pair<Color, Int>) = of(pair.toMap().mapKeys { ColorConvertible(it::key) })

        @JvmStatic
        fun of(vararg colors: ColorConvertible) = of(colors.toList().associateWith { 1 })

        @JvmStatic
        fun of(vararg pair: Pair<ColorConvertible, Int>) = of(pair.toMap())

        @JvmStatic
        fun of(map: Map<ColorConvertible, Int>) = ColorConvertible {
            var redSum = 0
            var greenSum = 0
            var blueSum = 0
            var weightSum = 0
            map.forEach { (color: ColorConvertible, weight: Int) ->
                //RGB値にweightをかけた値を加算していく
                color.asColor().run {
                    redSum += this.red * weight
                    greenSum += this.green * weight
                    blueSum += this.blue * weight
                }
                weightSum += weight
            }
            if (weightSum > 0) {
                Color(redSum / weightSum, greenSum / weightSum, blueSum / weightSum)
            } else Color.WHITE
        }

    }


}