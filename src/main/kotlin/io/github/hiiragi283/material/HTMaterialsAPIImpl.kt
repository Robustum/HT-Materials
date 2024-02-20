package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.registry.HTDeferredRegister
import io.github.hiiragi283.api.shape.HTShapeRegistry
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

internal class HTMaterialsAPIImpl : HTMaterialsAPI {
    companion object {
        internal lateinit var shapeRegistry: HTShapeRegistry
        internal lateinit var materialRegistry: HTMaterialRegistry
        internal lateinit var itemGroup: ItemGroup
        internal lateinit var iconItem: Item
        internal lateinit var dictionaryItem: Item
        internal lateinit var fluidManager: HTFluidManager
        internal lateinit var partManager: HTPartManager

        private val blockRegister: HTDeferredRegister<Block> = HTDeferredRegister(Registry.BLOCK_KEY, HTMaterialsAPI.MOD_ID)
        private val fluidRegister: HTDeferredRegister<Fluid> = HTDeferredRegister(Registry.FLUID_KEY, HTMaterialsAPI.MOD_ID)
        private val itemRegister: HTDeferredRegister<Item> = HTDeferredRegister(Registry.ITEM_KEY, HTMaterialsAPI.MOD_ID)

        fun initRegister() {
            blockRegister.register(Registry.BLOCK)
            fluidRegister.register(Registry.FLUID)
            itemRegister.register(Registry.ITEM)
        }
    }

    override fun shapeRegistry(): HTShapeRegistry = shapeRegistry

    override fun materialRegistry(): HTMaterialRegistry = materialRegistry

    override fun itemGroup(): ItemGroup = itemGroup

    override fun iconItem(): Item = iconItem

    override fun dictionaryItem(): Item = dictionaryItem

    override fun fluidManager(): HTFluidManager = fluidManager

    override fun partManager(): HTPartManager = partManager

    override fun registerBlock(path: String, supplier: Supplier<out Block>): Supplier<Block> = blockRegister.register(path, supplier)

    override fun registerFluid(path: String, supplier: Supplier<out Fluid>): Supplier<Fluid> = fluidRegister.register(path, supplier)

    override fun registerItem(path: String, supplier: Supplier<out Item>): Supplier<Item> = itemRegister.register(path, supplier)
}
