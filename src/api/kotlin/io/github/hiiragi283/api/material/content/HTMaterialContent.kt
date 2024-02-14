package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShapeKey
import net.minecraft.fluid.FlowableFluid
import net.minecraft.item.BlockItem
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
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

        abstract var harvestTool: Supplier<Tag<MCItem>>?
        abstract var harvestLevel: Int

        final override fun init(materialKey: HTMaterialKey) {
            block = Registry.register(Registry.BLOCK, blockId(materialKey), block(materialKey))
            blockItem = Registry.register(Registry.ITEM, blockId(materialKey), blockItem(materialKey))
        }

        abstract fun blockId(materialKey: HTMaterialKey): Identifier

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
            item = Registry.register(Registry.ITEM, itemId(materialKey), item(materialKey))
        }

        abstract fun itemId(materialKey: HTMaterialKey): Identifier

        abstract fun item(materialKey: HTMaterialKey): MCItem
    }
}
