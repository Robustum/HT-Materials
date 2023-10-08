package hiiragi283.material

import hiiragi283.material.init.*
import hiiragi283.material.util.hiiragiId
import net.fabricmc.api.ModInitializer
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pers.solid.brrp.v1.RRPEventHelper
import pers.solid.brrp.v1.api.RuntimeResourcePack

object RagiMaterials : ModInitializer {

    const val MOD_ID: String = "ragi_materials"
    const val MOD_NAME: String = "RagiMaterials"

    val LOGGER: Logger = LogManager.getLogger(MOD_NAME)

    private val RESOURCE_PACK = RuntimeResourcePack.create(hiiragiId("runtime"))

    override fun onInitialize() {

        HiiragiRegistries.registerShape()
        HiiragiRegistries.registerMaterial()
        HiiragiRegistries.registerPart()

        HiiragiBlocks.registerMaterialBlocks()
        HiiragiFluids.registerMaterialFluids()
        HiiragiItems.registerMaterialItems()

        HiiragiRegistries.BLOCK.register(Registry.BLOCK)
        HiiragiRegistries.FLUID.register(Registry.FLUID)
        HiiragiRegistries.ITEM.register(Registry.ITEM)

        HiiragiRegistries.BLOCK.addResources(RESOURCE_PACK)
        HiiragiRegistries.FLUID.addResources(RESOURCE_PACK)
        HiiragiRegistries.ITEM.addResources(RESOURCE_PACK)

        HiiragiTagRegistry.register(RESOURCE_PACK)

        HiiragiEventHandlers.register()

        RRPEventHelper.BEFORE_VANILLA.registerPack(RESOURCE_PACK)

    }

}