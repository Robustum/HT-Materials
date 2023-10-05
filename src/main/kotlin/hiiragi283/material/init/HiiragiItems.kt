package hiiragi283.material.init

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.item.MaterialDustItem
import hiiragi283.material.item.MaterialIngotItem
import hiiragi283.material.item.MaterialNuggetItem

typealias MaterialItemConstructor = (HiiragiMaterial) -> MaterialItem

object HiiragiItems {

    private val materialFunctions: Map<HiiragiShape, MaterialItemConstructor> = mapOf(
        HiiragiShapes.DUST to ::MaterialDustItem,
        HiiragiShapes.INGOT to ::MaterialIngotItem,
        HiiragiShapes.NUGGET to ::MaterialNuggetItem
    )

    fun registerMaterialItems() {
        materialFunctions.forEach { (shape, constructor) ->
            HiiragiRegistries.MATERIAL.getValues()
                .filter(shape::isValid)
                .map(constructor)
                .forEach(MaterialItem::register)
        }
    }

}