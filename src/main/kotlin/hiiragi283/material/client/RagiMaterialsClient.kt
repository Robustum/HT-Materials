package hiiragi283.material.client

import hiiragi283.material.init.HiiragiEventHandlers
import hiiragi283.material.init.HiiragiRegistries
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
object RagiMaterialsClient : ClientModInitializer {

    override fun onInitializeClient() {

        HiiragiRegistries.BLOCK.registerClient()
        HiiragiRegistries.FLUID.registerClient()
        HiiragiRegistries.ITEM.registerClient()

        HiiragiEventHandlers.registerClient()

    }

}