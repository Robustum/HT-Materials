package io.github.hiiragi283.material.compat

import com.simibubi.create.AllItems
import io.github.hiiragi283.material.api.entorypoint.HTMaterialPlugin
import io.github.hiiragi283.material.api.event.HTModsLoadedCallback
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.HTTagManager
import io.github.hiiragi283.material.common.util.isModLoaded
import org.apache.logging.log4j.LogManager

object HTCreatePlugin : HTMaterialPlugin.Pre {

    const val MOD_ID: String = "create"

    private val logger = LogManager.getLogger("HT Materials - Create")

    override fun preInitialize() {
        if (!isModLoaded(MOD_ID)) return

        HTModsLoadedCallback.EVENT.register {

            //Tag Registration
            HTTagManager.registerItemTags(
                HTShape.DUST.getForgeTag(HTVanillaMaterials.NETHERRACK),
                AllItems.CINDER_FLOUR.get()
            )
            HTTagManager.registerItemTags(
                HTShape.DUST.getForgeTag(HTVanillaMaterials.OBSIDIAN),
                AllItems.POWDERED_OBSIDIAN.get()
            )

            logger.info("Integration succeeded!")

        }

    }

}