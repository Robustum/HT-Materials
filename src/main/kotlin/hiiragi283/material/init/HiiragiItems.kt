package hiiragi283.material.init

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.item.*

typealias MaterialItemConstructor = (HiiragiMaterial) -> MaterialItem

object HiiragiItems {

    @JvmField
    val FORGE_HAMMER = HiiragiRegistries.ITEM.register("forge_hammer", ForgeHammerItem)

    private val materialFunctions: Map<HiiragiShape, MaterialItemConstructor> = mapOf(
        HiiragiShapes.DUST to ::MaterialDustItem,
        HiiragiShapes.INGOT to ::MaterialIngotItem,
        HiiragiShapes.NUGGET to ::MaterialNuggetItem,
        HiiragiShapes.PLATE to ::MaterialPlateItem
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