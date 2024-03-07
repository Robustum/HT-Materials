package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidRegistry
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartRegistry
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.material.impl.HTFluidRegistryImpl
import io.github.hiiragi283.material.impl.HTPartRegistryImpl
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup

internal class HTMaterialsAPIImpl : HTMaterialsAPI {
    companion object {
        internal lateinit var shapeRegistry: HTShapeRegistry
        internal lateinit var materialRegistry: HTMaterialRegistry
        internal lateinit var itemGroup: ItemGroup
        internal lateinit var iconItem: Item
        internal lateinit var dictionaryItem: Item
    }

    override fun shapeRegistry(): HTShapeRegistry = shapeRegistry

    override fun materialRegistry(): HTMaterialRegistry = materialRegistry

    override fun itemGroup(): ItemGroup = itemGroup

    override fun iconItem(): Item = iconItem

    override fun dictionaryItem(): Item = dictionaryItem

    override fun fluidRegistry(): HTFluidRegistry = HTFluidRegistryImpl

    override fun partRegistry(): HTPartRegistry = HTPartRegistryImpl
}
