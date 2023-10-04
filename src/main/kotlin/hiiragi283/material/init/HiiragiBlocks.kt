package hiiragi283.material.init

import hiiragi283.material.api.block.MaterialBlock
import hiiragi283.material.api.block.MaterialStorageBlock
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.reigstry.HiiragiRegistries
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.api.shape.HiiragiShapes
import hiiragi283.material.block.MachineExtenderBlock

object HiiragiBlocks {

    @JvmField
    val MACHINE_EXTENDER = HiiragiRegistries.BLOCK.register("machine_extender", MachineExtenderBlock)

    private val materialFunctions: Map<HiiragiShape, (HiiragiMaterial) -> MaterialBlock> = mapOf(
        HiiragiShapes.BLOCK to ::MaterialStorageBlock
    )

    fun registerMaterialBlocks() {
        materialFunctions.forEach { (shape: HiiragiShape, constructor: (HiiragiMaterial) -> MaterialBlock) ->
            HiiragiRegistries.MATERIAL.getValues()
                .filter(shape::isValid)
                .map(constructor)
                .forEach(MaterialBlock::register)
        }
    }


}