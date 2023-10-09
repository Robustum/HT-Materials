package hiiragi283.material.init

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.item.*

typealias MaterialItemConstructor = (HiiragiMaterial) -> MaterialItem

object HiiragiItems {

    @JvmField
    val SMITHING_HAMMER = HiiragiRegistries.ITEM.register("smithing_hammer", SmithingHammerItem)

    private val materialFunctions: Map<HiiragiShape, MaterialItemConstructor> = mapOf(
        HiiragiShapes.DUST to ::MaterialDustItem,
        HiiragiShapes.GEAR to ::MaterialGearItem,
        HiiragiShapes.INGOT to ::MaterialIngotItem,
        HiiragiShapes.NUGGET to ::MaterialNuggetItem,
        HiiragiShapes.PLATE to ::MaterialPlateItem,
        HiiragiShapes.ROD to ::MaterialRodItem
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