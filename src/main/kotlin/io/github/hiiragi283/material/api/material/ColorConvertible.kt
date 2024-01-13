package io.github.hiiragi283.material.api.material

import java.awt.Color

fun interface ColorConvertible {
    fun asColor(): Color

    companion object {
        @JvmField
        val EMPTY = ColorConvertible { Color.WHITE }

        @JvmStatic
        fun of(vararg colors: ColorConvertible) = of(colors.associateWith { 1 })

        @JvmStatic
        fun of(vararg pairs: Pair<ColorConvertible, Int>) = of(pairs.toMap())

        @JvmStatic
        fun of(map: Map<ColorConvertible, Int>) = ofColor(map.mapKeys { (color: ColorConvertible, _) -> color.asColor() })

        @JvmStatic
        fun ofColor(vararg colors: Color) = ofColor(colors.associateWith { 1 })

        @JvmStatic
        fun ofColor(vararg pairs: Pair<Color, Int>) = ofColor(pairs.toMap())

        @JvmStatic
        fun ofColor(map: Map<Color, Int>) = ColorConvertible { average(map) }

        @JvmStatic
        fun average(colors: Iterable<Color>): Color = average(colors.associateWith { 1 })

        @JvmStatic
        fun average(map: Map<Color, Int>): Color {
            var redSum = 0
            var greenSum = 0
            var blueSum = 0
            var weightSum = 0
            map.forEach { (color: Color, weight: Int) ->
                // RGB値にweightをかけた値を加算していく
                color.run {
                    redSum += this.red * weight
                    greenSum += this.green * weight
                    blueSum += this.blue * weight
                }
                weightSum += weight
            }
            return if (weightSum > 0) {
                Color(redSum / weightSum, greenSum / weightSum, blueSum / weightSum)
            } else {
                Color.WHITE
            }
        }
    }
}
