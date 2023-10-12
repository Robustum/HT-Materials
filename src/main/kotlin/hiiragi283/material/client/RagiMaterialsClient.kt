package hiiragi283.material.client

import hiiragi283.material.client.model.HiiragiModelResourceProvider
import hiiragi283.material.init.HiiragiEventHandlers
import hiiragi283.material.init.HiiragiRegistries
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry

@Environment(EnvType.CLIENT)
object RagiMaterialsClient : ClientModInitializer {

    override fun onInitializeClient() {

        HiiragiRegistries.BLOCK.registerClient()
        HiiragiRegistries.FLUID.registerClient()
        HiiragiRegistries.ITEM.registerClient()

        HiiragiEventHandlers.registerClient()

        ModelLoadingRegistry.INSTANCE.registerModelProvider { _, _ -> HiiragiModelResourceProvider }

    }

}