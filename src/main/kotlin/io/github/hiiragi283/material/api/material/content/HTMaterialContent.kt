package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapeKey
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
    abstract fun getIdentifier(materialKey: HTMaterialKey): Identifier

    open fun onCreate(materialKey: HTMaterialKey, created: T) {}

    //    Block    //

    abstract class BLOCK(shapeKey: HTShapeKey) : HTMaterialContent<Block>(shapeKey, Registry.BLOCK_KEY) {
        final override fun getIdentifier(materialKey: HTMaterialKey): Identifier = shapeKey.getIdentifier(materialKey)

        abstract fun createBlock(materialKey: HTMaterialKey): Block?

        abstract fun createBlockItem(block: Block, materialKey: HTMaterialKey): BlockItem?
    }

    //    Fluid    //

    abstract class FLUID(shapeKey: HTShapeKey) : HTMaterialContent<Fluid>(shapeKey, Registry.FLUID_KEY) {
        abstract fun createStill(materialKey: HTMaterialKey): FlowableFluid

        abstract fun getFlowingFluidIdentifier(materialKey: HTMaterialKey): Identifier

        abstract fun createFlowing(materialKey: HTMaterialKey): FlowableFluid?

        abstract fun getBlockIdentifier(materialKey: HTMaterialKey): Identifier

        abstract fun createFluidBlock(fluid: FlowableFluid, materialKey: HTMaterialKey): FluidBlock?

        abstract fun getBucketIdentifier(materialKey: HTMaterialKey): Identifier

        abstract fun createFluidBucket(fluid: FlowableFluid, materialKey: HTMaterialKey): BucketItem?
    }

    //    Item    //

    abstract class ITEM(shapeKey: HTShapeKey) : HTMaterialContent<Item>(shapeKey, Registry.ITEM_KEY) {
        final override fun getIdentifier(materialKey: HTMaterialKey): Identifier = shapeKey.getIdentifier(materialKey)

        abstract fun createItem(materialKey: HTMaterialKey): Item?

        override fun onCreate(materialKey: HTMaterialKey, created: Item) {
            HTPartManager.forceRegister(materialKey, shapeKey, created)
        }
    }
}
