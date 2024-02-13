package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShapeKey
import net.minecraft.fluid.FlowableFluid
import net.minecraft.item.BlockItem
import java.util.function.Supplier
import net.minecraft.block.Block as MCBlock
import net.minecraft.item.Item as MCItem

sealed class HTMaterialContent(val shapeKey: HTShapeKey, val type: Type) {
    abstract fun init(materialKey: HTMaterialKey)

    open fun postInit(materialKey: HTMaterialKey) {}

    enum class Type {
        BLOCK,
        FLUID,
        ITEM,
    }

    //    Block    //

    abstract class Block(shapeKey: HTShapeKey) : HTMaterialContent(shapeKey, Type.BLOCK) {
        lateinit var block: MCBlock
            private set
        lateinit var blockItem: BlockItem
            private set

        final override fun init(materialKey: HTMaterialKey) {
            block = HTPlatformHelper.INSTANCE.registerBlock(blockId(materialKey), block(materialKey))
            blockItem = HTPlatformHelper.INSTANCE.registerItem(blockId(materialKey), blockItem(materialKey))
        }

        abstract fun blockId(materialKey: HTMaterialKey): String

        abstract fun block(materialKey: HTMaterialKey): MCBlock

        abstract fun blockItem(materialKey: HTMaterialKey): BlockItem
    }

    //    Fluid    //

    abstract class Fluid(shapeKey: HTShapeKey) : HTMaterialContent(shapeKey, Type.FLUID) {
        lateinit var still: Supplier<FlowableFluid>
            protected set
        lateinit var flowing: Supplier<FlowableFluid>
            protected set
    }

    //    Item    //

    abstract class Item(shapeKey: HTShapeKey) : HTMaterialContent(shapeKey, Type.ITEM) {
        lateinit var item: MCItem
            private set

        final override fun init(materialKey: HTMaterialKey) {
            item = HTPlatformHelper.INSTANCE.registerItem(itemId(materialKey), item(materialKey))
        }

        abstract fun itemId(materialKey: HTMaterialKey): String

        abstract fun item(materialKey: HTMaterialKey): MCItem
    }
}
