package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFlowableFluid
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.HTToolManager
import net.minecraft.item.BlockItem
import net.minecraft.tag.Tag
import java.util.function.Supplier
import net.minecraft.block.Block as MCBlock
import net.minecraft.fluid.Fluid as MCFluid
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
        lateinit var block: Supplier<MCBlock>
            private set
        lateinit var blockItem: Supplier<MCItem>
            private set

        abstract var harvestTool: Supplier<Tag<MCItem>>?
        abstract var harvestLevel: Int

        final override fun init(materialKey: HTMaterialKey) {
            block = HTMaterialsAPI.INSTANCE.registerBlock(blockId(materialKey)) { block(materialKey) }
            blockItem = HTMaterialsAPI.INSTANCE.registerItem(blockId(materialKey)) { blockItem(materialKey) }
        }

        override fun postInit(materialKey: HTMaterialKey) {
            harvestTool?.get()?.let { tag: Tag<net.minecraft.item.Item> ->
                HTToolManager.putBreakByTool(block.get(), tag, harvestLevel)
            } ?: HTToolManager.setBreakByHand(block.get(), true)
        }

        open fun blockId(materialKey: HTMaterialKey): String = shapeKey.getShape().getIdentifier(materialKey).path

        abstract fun block(materialKey: HTMaterialKey): MCBlock

        abstract fun blockItem(materialKey: HTMaterialKey): BlockItem
    }

    //    Fluid    //

    abstract class Fluid(shapeKey: HTShapeKey) : HTMaterialContent(shapeKey, Type.FLUID) {
        lateinit var still: Supplier<MCFluid>
            private set
        lateinit var flowing: Supplier<MCFluid>
            private set
        private lateinit var settings: HTFlowableFluid.Settings

        final override fun init(materialKey: HTMaterialKey) {
            still = HTMaterialsAPI.INSTANCE.registerFluid(fluidId(materialKey)) { HTFlowableFluid.Still(settings) }
            flowing = HTMaterialsAPI.INSTANCE.registerFluid("flowing_${fluidId(materialKey)}") { HTFlowableFluid.Flowing(settings) }
            settings = HTFlowableFluid.Settings(still, flowing).apply { settings(this, materialKey) }
        }

        abstract fun fluidId(materialKey: HTMaterialKey): String

        open fun settings(settings: HTFlowableFluid.Settings, materialKey: HTMaterialKey) {}
    }

    //    Item    //

    abstract class Item(shapeKey: HTShapeKey) : HTMaterialContent(shapeKey, Type.ITEM) {
        lateinit var item: Supplier<MCItem>
            private set

        final override fun init(materialKey: HTMaterialKey) {
            item = HTMaterialsAPI.INSTANCE.registerItem(itemId(materialKey)) { item(materialKey) }
        }

        open fun itemId(materialKey: HTMaterialKey): String = shapeKey.getShape().getIdentifier(materialKey).path

        abstract fun item(materialKey: HTMaterialKey): MCItem
    }
}
