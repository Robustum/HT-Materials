package io.github.hiiragi283.material.compat

import io.github.hiiragi283.material.api.entorypoint.HTMaterialPlugin
import io.github.hiiragi283.material.api.event.HTModsLoadedCallback
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.util.isAir
import io.github.hiiragi283.material.common.util.isModLoaded
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager

object HTMIPlugin : HTMaterialPlugin.Pre {

    const val MOD_ID: String = "modern_industrialization"

    private val logger = LogManager.getLogger("HT Materials - MI")

    override fun preInitialize() {
        if (!isModLoaded(MOD_ID)) return

        HTModsLoadedCallback.EVENT.register {

            //Register Tags for ALL MI Material Items
            HTMaterial.REGISTRY.forEach { material ->
                HTShape.REGISTRY.forEach { shape ->
                    Registry.ITEM.get(shape.getIdentifier(material, MOD_ID)).run {
                        if (!this.isAir()) {
                            HTPartManager.register(material, shape, this)
                        }
                    }
                }
            }

            logger.info("Integration succeeded!")

        }
    }

}