package io.github.hiiragi283.api

import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.util.component1
import io.github.hiiragi283.api.util.component2
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import net.fabricmc.loader.api.entrypoint.EntrypointContainer
import net.minecraft.util.Identifier
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

interface HTMaterialsAPI {
    companion object {
        const val MOD_ID: String = "ht_materials"
        const val MOD_NAME: String = "HT Materials"

        @JvmStatic
        fun id(path: String) = Identifier(MOD_ID, path)

        private val logger: Logger = LogManager.getLogger(MOD_NAME)

        @JvmOverloads
        @JvmStatic
        fun log(message: String, level: Level = Level.INFO) {
            logger.log(level, "[$MOD_NAME] $message")
        }

        private lateinit var instance: HTMaterialsAPI

        @JvmStatic
        fun getInstance(): HTMaterialsAPI {
            if (Companion::instance.isInitialized) return instance
            val containers: Iterable<EntrypointContainer<HTMaterialsAPI>> = FabricLoader.getInstance().getEntrypointContainers(
                "ht_materials_api",
                HTMaterialsAPI::class.java,
            )
            for ((container: HTMaterialsAPI, provider: ModContainer) in containers) {
                if (provider.metadata.id == MOD_ID) {
                    instance = container
                    return instance
                }
            }
            throw IllegalStateException()
        }
    }

    fun shapeRegistry(): HTShapeRegistry

    fun materialRegistry(): HTMaterialRegistry

    fun fluidManager(): HTFluidManager

    fun partManager(): HTPartManager
}
