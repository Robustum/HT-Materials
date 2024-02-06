package io.github.hiiragi283.api.material.element

import java.awt.Color

data class HTElement(
    override val color: Color,
    override val formula: String,
    override val molar: Double,
) : HTMaterialInfoProvider<HTElement> {
    override fun bracket(): HTElement = copy(formula = "($formula)")
}
