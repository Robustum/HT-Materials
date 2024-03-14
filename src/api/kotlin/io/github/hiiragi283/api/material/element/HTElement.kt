package io.github.hiiragi283.api.material.element

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.github.hiiragi283.api.extension.ColorCodec
import io.github.hiiragi283.api.extension.averageColor
import io.github.hiiragi283.api.extension.calculateMolar
import io.github.hiiragi283.api.extension.formatFormula
import java.awt.Color

@Suppress("DataClassPrivateConstructor")
data class HTElement private constructor(
    val color: Color,
    val formula: String,
    val molar: Double,
) {
    fun bracket() = copy(formula = "($formula)")

    companion object {
        @JvmField
        val CODEC: Codec<HTElement> = RecordCodecBuilder.create { instance ->
            instance.group(
                ColorCodec.fieldOf("color").forGetter(HTElement::color),
                Codec.STRING.orElse("").fieldOf("formula").forGetter(HTElement::formula),
                Codec.DOUBLE.orElse(0.0).fieldOf("molar").forGetter(HTElement::molar),
            ).apply(instance, ::of)
        }

        @JvmStatic
        fun of(color: Color, formula: String, molar: Double): HTElement = HTElement(color, formula, molar)

        @JvmStatic
        fun group(vararg pairs: Pair<HTElement, Int>): HTElement = group(mapOf(*pairs))

        @JvmStatic
        fun group(
            map: Map<HTElement, Int>,
            color: Color = averageColor(map.mapKeys { it.key.color }),
            formula: String = formatFormula(map.mapKeys { it.key.formula }),
            molar: Double = calculateMolar(map.mapKeys { it.key.molar }),
        ): HTElement = HTElement(color, formula, molar)
    }
}
