package io.github.hiiragi283.api

import io.github.hiiragi283.api.extention.getSingleInstanceWith
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.function.Supplier

interface HTMaterialsAPI {
    companion object {
        const val MOD_ID: String = "ht_materials"
        const val MOD_NAME: String = "HT Materials"

        @JvmStatic
        val INSTANCE: HTMaterialsAPI = getSingleInstanceWith()

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

    fun itemGroup(): ItemGroup

    fun iconItem(): Item

    fun dictionaryItem(): Item

    fun fluidManager(): HTFluidManager

    fun partManager(): HTPartManager

    fun registerBlock(path: String, supplier: Supplier<out Block>): Supplier<Block>

    fun registerFluid(path: String, supplier: Supplier<out Fluid>): Supplier<Fluid>

    fun registerItem(path: String, supplier: Supplier<out Item>): Supplier<Item>
}
