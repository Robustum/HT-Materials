package hiiragi283.material

import hiiragi283.material.api.reigstry.HiiragiRegistries
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pers.solid.brrp.v1.api.RuntimeResourcePack

object RagiMaterials : ModInitializer {

    const val MOD_ID: String = "ragi_materials"
    const val MOD_NAME: String = "RagiMaterials"

    val LOGGER: Logger = LogManager.getLogger(MOD_NAME)

    private val RESOURCE_PACK = RuntimeResourcePack.create(id("runtime"))

    override fun onInitialize() {

        HiiragiRegistries.BLOCK.register(Registry.BLOCK)
        HiiragiRegistries.BLOCK_ENTITY.register(Registry.BLOCK_ENTITY_TYPE)
        HiiragiRegistries.ITEM.register(Registry.ITEM)

        HiiragiRegistries.BLOCK.addResources(RESOURCE_PACK)
        HiiragiRegistries.ITEM.addResources(RESOURCE_PACK)

    }

    fun id(path: String) = Identifier(MOD_ID, path)

}