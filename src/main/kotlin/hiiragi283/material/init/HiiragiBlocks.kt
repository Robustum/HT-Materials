package hiiragi283.material.init

import hiiragi283.material.api.reigstry.HiiragiRegistries
import hiiragi283.material.block.MachineExtenderBlock

object HiiragiBlocks {

    @JvmField
    val MACHINE_EXTENDER = HiiragiRegistries.BLOCK.register("machine_extender", MachineExtenderBlock)

}