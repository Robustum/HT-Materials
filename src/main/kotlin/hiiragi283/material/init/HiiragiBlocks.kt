package hiiragi283.material.init

import hiiragi283.material.api.block.MaterialBlock
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.block.MachineExtenderBlock
import hiiragi283.material.block.MaterialStorageBlock

typealias MaterialBlockConstructor = (HiiragiMaterial) -> MaterialBlock

object HiiragiBlocks {

    @JvmField
    val MACHINE_EXTENDER = HiiragiRegistries.BLOCK.register("machine_extender", MachineExtenderBlock)

    private val materialFunctions: Map<HiiragiShape, MaterialBlockConstructor> = mapOf(
        HiiragiShapes.BLOCK to ::MaterialStorageBlock
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