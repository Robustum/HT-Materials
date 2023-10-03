package hiiragi283.material.init

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.reigstry.HiiragiRegistries
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.api.shape.HiiragiShapes
import hiiragi283.material.item.MaterialDustItem
import hiiragi283.material.item.MaterialIngotItem
import hiiragi283.material.item.MaterialNuggetItem

object HiiragiItems {

    private val materialFunctions: Map<HiiragiShape, (HiiragiMaterial) -> MaterialItem> = mapOf(
        HiiragiShapes.DUST to ::MaterialDustItem,
        HiiragiShapes.INGOT to ::MaterialIngotItem,
        HiiragiShapes.NUGGET to ::MaterialNuggetItem
    )

    fun registerMaterialItems() {
        materialFunctions.forEach { (shape: HiiragiShape, constructor: (HiiragiMaterial) -> MaterialItem) ->
            HiiragiRegistries.MATERIAL.getMap().values
                .filter(shape::isValid)
                .map(constructor)
                .forEach(MaterialItem::register)
        }
    }

}