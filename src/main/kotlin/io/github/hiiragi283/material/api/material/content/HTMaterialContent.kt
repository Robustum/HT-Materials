package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

sealed class HTMaterialContent<T> {

    abstract val shapeKey: HTShapeKey

    abstract val registry: Registry<T>

    abstract fun getIdentifier(materialKey: HTMaterialKey): Identifier

    abstract fun create(materialKey: HTMaterialKey): T?

    open fun onCreate(materialKey: HTMaterialKey, created: T) {}

    abstract class BLOCK : HTMaterialContent<Block>() {

        final override val registry: Registry<Block> = Registry.BLOCK

        final override fun getIdentifier(materialKey: HTMaterialKey): Identifier = shapeKey.getIdentifier(materialKey)

    }

    abstract class FLUID : HTMaterialContent<Fluid>() {

        final override val registry: Registry<Fluid> = Registry.FLUID

    }

    abstract class ITEM : HTMaterialContent<Item>() {

        final override val registry: Registry<Item> = Registry.ITEM

        final override fun getIdentifier(materialKey: HTMaterialKey): Identifier = shapeKey.getIdentifier(materialKey)

        override fun onCreate(materialKey: HTMaterialKey, created: Item) {
            HTPartManager.forceRegister(materialKey, shapeKey, created)
        }

    }

}