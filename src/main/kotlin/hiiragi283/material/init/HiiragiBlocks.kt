package hiiragi283.material.init

import hiiragi283.material.api.block.MaterialBlock
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.block.MaterialCasingBlock
import hiiragi283.material.block.MaterialStorageBlock
import hiiragi283.material.block.StorageExtenderBlock
import hiiragi283.material.block.entity.InventoryExtenderBlockEntity
import hiiragi283.material.block.entity.TankExtenderBlockEntity

typealias MaterialBlockConstructor = (HiiragiMaterial) -> MaterialBlock

object HiiragiBlocks {

    @JvmField
    val INVENTORY_EXTENDER = HiiragiRegistries.BLOCK.register(
        "inventory_extender",
        StorageExtenderBlock(::InventoryExtenderBlockEntity)
    )

    @JvmField
    val TANK_EXTENDER = HiiragiRegistries.BLOCK.register(
        "tank_extender",
        StorageExtenderBlock(::TankExtenderBlockEntity)
    )

    private val materialFunctions: Map<HiiragiShape, MaterialBlockConstructor> = mapOf(
        HiiragiShapes.BLOCK to ::MaterialStorageBlock,
        HiiragiShapes.CASING to ::MaterialCasingBlock
    )

    fun registerMaterialBlocks() {
        materialFunctions.forEach { (shape, constructor) ->
            HiiragiRegistries.MATERIAL.getValues()
                .filter(shape::isValid)
                .map(constructor)
                .forEach(MaterialBlock::register)
        }
    }


}