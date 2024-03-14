package io.github.hiiragi283.api.extension

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import java.awt.Color

val ColorCodec: Codec<Color> = RecordCodecBuilder.create { instance ->
    instance.group(
        Codec.INT.fieldOf("red").forGetter(Color::getRed),
        Codec.INT.fieldOf("green").forGetter(Color::getGreen),
        Codec.INT.fieldOf("blue").forGetter(Color::getBlue),
    ).apply(instance) { r, g, b -> Color(r, g, b) }
}

fun averageColor(vararg colors: Color): Color = averageColor(colors.associateWith { 1 })

fun averageColor(colors: Iterable<Color>): Color = averageColor(colors.associateWith { 1 })

fun averageColor(vararg pairs: Pair<Color, Int>): Color = averageColor(mapOf(*pairs))

fun averageColor(map: Map<Color, Int>): Color {
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

object HTColor {
    // Minecraftのカラーコードと同じ色
    @JvmField
    val BLACK = Color(0x00, 0x00, 0x00)

    @JvmField
    val DARK_BLUE = Color(0x00, 0x00, 0xAA)

    @JvmField
    val DARK_GREEN = Color(0x00, 0xAA, 0x00)

    @JvmField
    val DARK_AQUA = Color(0x00, 0xAA, 0xAA)

    @JvmField
    val DARK_RED = Color(0xAA, 0x00, 0x00)

    @JvmField
    val DARK_PURPLE = Color(0xAA, 0x00, 0xAA)

    @JvmField
    val GOLD = Color(0xFF, 0xAA, 0x00)

    @JvmField
    val GRAY = Color(0xAA, 0xAA, 0xAA)

    @JvmField
    val DARK_GRAY = Color(0x55, 0x55, 0x55)

    @JvmField
    val BLUE = Color(0x55, 0x55, 0xFF)

    @JvmField
    val GREEN = Color(0x55, 0xFF, 0x55)

    @JvmField
    val AQUA = Color(0x55, 0xFF, 0xFF)

    @JvmField
    val RED = Color(0xFF, 0x55, 0x55)

    @JvmField
    val LIGHT_PURPLE = Color(0xFF, 0x55, 0xFF)

    @JvmField
    val YELLOW = Color(0xFF, 0xFF, 0x55)

    @JvmField
    val WHITE = Color(0xFF, 0xFF, 0xFF)
}
