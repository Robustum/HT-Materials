package io.github.hiiragi283.api.material.composition

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.FormulaConvertible
import io.github.hiiragi283.api.material.MolarMassConvertible
import io.github.hiiragi283.api.material.element.HTElement
import java.awt.Color
import java.util.function.Supplier

class HTMolecularComposition(
    private val map: Map<HTElement, Int>,
    private val color: Supplier<Color> = Supplier { ColorConvertible.average(map.mapKeys { (element: HTElement, _) -> element.color }) },
    private val formula: Supplier<String> = Supplier {
        FormulaConvertible.format(
            map.mapKeys {
                    (element: HTElement, _) ->
                element.symbol
            },
        )
    },
    private val molar: Supplier<Double> = Supplier {
        MolarMassConvertible.calculate(
            map.mapKeys {
                    (element: HTElement, _) ->
                element.molar
            },
        )
    },
) : HTMaterialComposition() {
    constructor(element: HTElement, count: Int) : this(mapOf(element to count))

    override fun componentMap(): Map<HTElement, Int> = map

    override fun color(): Color = color.get()

    override fun formula(): String = formula.get()

    override fun molar(): Double = molar.get()
}
