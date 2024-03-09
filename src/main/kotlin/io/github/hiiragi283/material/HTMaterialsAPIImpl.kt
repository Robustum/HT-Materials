package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.material.impl.HTFluidManagerImpl
import io.github.hiiragi283.material.impl.HTPartManagerImpl
import net.minecraft.item.Item

internal class HTMaterialsAPIImpl : HTMaterialsAPI {
    companion object {
        internal lateinit var shapeRegistry1: HTShapeRegistry
        internal lateinit var materialRegistry1: HTMaterialRegistry
        internal lateinit var iconItem1: Item
        internal lateinit var dictionaryItem1: Item
    }

    override val iconItem: Item
        get() = iconItem1

    override val dictionaryItem: Item
        get() = dictionaryItem1

    override val shapeRegistry: HTShapeRegistry
        get() = shapeRegistry1

    override val materialRegistry: HTMaterialRegistry
        get() = materialRegistry1

    override val fluidManager: HTFluidManager = HTFluidManagerImpl

    override val partManager: HTPartManager = HTPartManagerImpl
}
