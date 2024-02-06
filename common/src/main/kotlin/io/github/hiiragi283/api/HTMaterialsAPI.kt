package io.github.hiiragi283.api

import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.util.getInstance
import net.minecraft.util.Identifier
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

interface HTMaterialsAPI {
    companion object {
        const val MOD_ID: String = "ht_materials"
        const val MOD_NAME: String = "HT Materials"

        @JvmStatic
        val INSTANCE: HTMaterialsAPI = getInstance()

        @JvmStatic
        fun id(path: String) = Identifier(MOD_ID, path)

        private val logger: Logger = LogManager.getLogger(MOD_NAME)

        @JvmOverloads
        @JvmStatic
        fun log(message: String, level: Level = Level.INFO) {
            logger.log(level, "[$MOD_NAME] $message")
        }
    }

    fun shapeRegistry(): HTShapeRegistry

    fun materialRegistry(): HTMaterialRegistry

    fun fluidManager(): HTFluidManager

    fun partManager(): HTPartManager
}
