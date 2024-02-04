package io.github.hiiragi283.api.material.element

import io.github.hiiragi283.api.util.HTColor
import java.awt.Color

@Suppress("DataClassPrivateConstructor")
data class HTElement private constructor(
    val color: Color,
    val molar: Double,
    val symbol: String,
) {
    companion object {
        @JvmStatic
        fun build(builderAction: Builder.() -> Unit): HTElement = Builder().apply(builderAction).build()
    }

    class Builder internal constructor() {
        var color: Color = HTColor.WHITE
        var molar: Double = 0.0
        var symbol: String = ""

        internal fun build(): HTElement = HTElement(color, molar, symbol)
    }
}
