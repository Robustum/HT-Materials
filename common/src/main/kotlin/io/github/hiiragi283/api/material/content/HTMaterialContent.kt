package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShapeKey
import net.minecraft.block.Block
import net.minecraft.block.FluidBlock
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.item.BlockItem
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

sealed class HTMaterialContent<T>(val shapeKey: HTShapeKey, val registryKey: RegistryKey<Registry<T>>) {
    abstract fun id(materialKey: HTMaterialKey): Identifier

    open fun onCreate(materialKey: HTMaterialKey, created: T) {}

    //    Block    //

    abstract class BLOCK(shapeKey: HTShapeKey) : HTMaterialContent<Block>(shapeKey, Registry.BLOCK_KEY) {
        final override fun id(materialKey: HTMaterialKey): Identifier = shapeKey.getShape().getIdentifier(materialKey)

        abstract fun block(materialKey: HTMaterialKey): Block?

        abstract fun blockItem(block: Block, materialKey: HTMaterialKey): BlockItem?
    }

    //    Fluid    //

    abstract class FLUID(shapeKey: HTShapeKey) : HTMaterialContent<Fluid>(shapeKey, Registry.FLUID_KEY) {
        abstract fun still(materialKey: HTMaterialKey): FlowableFluid

        abstract fun flowingId(materialKey: HTMaterialKey): Identifier

        abstract fun flowing(materialKey: HTMaterialKey): FlowableFluid?

        abstract fun blockId(materialKey: HTMaterialKey): Identifier

        abstract fun block(fluid: FlowableFluid, materialKey: HTMaterialKey): FluidBlock?

        abstract fun bucketId(materialKey: HTMaterialKey): Identifier

        abstract fun bucket(fluid: FlowableFluid, materialKey: HTMaterialKey): BucketItem?
    }

    //    Item    //

    abstract class ITEM(shapeKey: HTShapeKey) : HTMaterialContent<Item>(shapeKey, Registry.ITEM_KEY) {
        final override fun id(materialKey: HTMaterialKey): Identifier = shapeKey.getShape().getIdentifier(materialKey)

        abstract fun item(materialKey: HTMaterialKey): Item?
    }
}
