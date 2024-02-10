package io.github.hiiragi283.fabric

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup

class HTMaterialsAPIFabric : HTMaterialsAPI {
    companion object {
        internal lateinit var shapeRegistry: HTShapeRegistry
        internal lateinit var materialRegistry: HTMaterialRegistry
        internal lateinit var itemGroup: ItemGroup
        internal lateinit var iconItem: Item
        internal lateinit var fluidManager: HTFluidManager
        internal lateinit var partManager: HTPartManager
    }

    override fun shapeRegistry(): HTShapeRegistry = shapeRegistry

    override fun materialRegistry(): HTMaterialRegistry = materialRegistry

    override fun itemGroup(): ItemGroup = itemGroup

    override fun iconItem(): Item = iconItem

    override fun fluidManager(): HTFluidManager = fluidManager

    override fun partManager(): HTPartManager = partManager
}
