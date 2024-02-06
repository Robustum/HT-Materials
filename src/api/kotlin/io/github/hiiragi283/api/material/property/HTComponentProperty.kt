package io.github.hiiragi283.api.material.property

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.material.FormulaConvertible
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.MolarMassConvertible

@JvmDefaultWithCompatibility
interface HTComponentProperty<T : HTComponentProperty<T>> :
    HTMaterialProperty<T>,
    ColorConvertible,
    FormulaConvertible,
    MolarMassConvertible {
    override fun verify(material: HTMaterial) {
        material.forEachProperty {
            if (it.key != key && it is ColorConvertible) {
                throw IllegalStateException("Material: ${material.key} cannot have two or more properties implemented ColorConvertible!")
            }
            if (it.key != key && it is FormulaConvertible) {
                throw IllegalStateException("Material: ${material.key} cannot have two or more properties implemented FormulaConvertible!")
            }
            if (it.key != key && it is MolarMassConvertible) {
                throw IllegalStateException(
                    "Material: ${material.key} cannot have two or more properties implemented MolarMassConvertible!",
                )
            }
        }
    }
}
