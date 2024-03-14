package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extension.getEntrypoints
import io.github.hiiragi283.api.extension.isModLoaded
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.material.impl.HTFluidManagerImpl
import io.github.hiiragi283.material.impl.HTMaterialRegistryImpl
import io.github.hiiragi283.material.impl.HTPartManagerImpl
import io.github.hiiragi283.material.impl.HTShapeRegistryImpl
import net.minecraft.item.Item

internal class HTMaterialsAPIImpl : HTMaterialsAPI {
    companion object {
        internal lateinit var iconItem1: Item
        internal lateinit var dictionaryItem1: Item

        private val addons: Iterable<HTMaterialsAddon> = getEntrypoints<HTMaterialsAddon>(HTMaterialsAPI.MOD_ID)
            .filter { isModLoaded(it.modId) }
            .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })
    }

    override val iconItem: Item
        get() = iconItem1

    override val dictionaryItem: Item
        get() = dictionaryItem1

    override val shapeRegistry: HTShapeRegistry
        get() = HTShapeRegistryImpl
    override val materialRegistry: HTMaterialRegistry
        get() = HTMaterialRegistryImpl
    override val fluidManager: HTFluidManager
        get() = HTFluidManagerImpl
    override val partManager: HTPartManager
        get() = HTPartManagerImpl

    override fun forEachAddon(action: (HTMaterialsAddon) -> Unit) {
        addons.forEach(action)
    }
}
