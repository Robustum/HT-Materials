package io.github.hiiragi283.material.compat

import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extension.notEmptyOrNull
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.part.HTPartRegistry
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import techreborn.init.TRContent
import techreborn.items.DynamicCellItem

@Suppress("unused")
object HMTRAddon : HTMaterialsAddon {
    override val modId: String = "techreborn"
    override val priority: Int = 0

    @JvmField
    val SMALL_DUST = HTShapeKey("small_dust")

    override fun registerShape(shapeHelper: HTMaterialsAddon.ShapeHelper) {
        shapeHelper.addShapeKey(SMALL_DUST)
    }

    override fun registerPartRegistry(registry: HTPartRegistry) {
        registry.add(TRContent.Dusts.PHOSPHOROUS, HTMaterialKeys.PHOSPHORUS, HTShapeKeys.DUST)
        registry.add(TRContent.SmallDusts.PHOSPHOROUS, HTMaterialKeys.PHOSPHORUS, SMALL_DUST)
        registry.add(TRContent.Gems.RUBY, HTMaterialKeys.RUBY, HTShapeKeys.GEM)
        registry.add(TRContent.Gems.SAPPHIRE, HTMaterialKeys.SAPPHIRE, HTShapeKeys.GEM)
        registry.add(TRContent.Dusts.SAW, HTMaterialKeys.WOOD, HTShapeKeys.DUST)
    }

    @Suppress("UnstableApiUsage")
    override fun postInitialize(envType: EnvType) {
        FluidStorage.combinedItemApiProvider(TRContent.CELL).register { context: ContainerItemContext ->
            val stack: ItemStack = context.itemVariant.toStack()
            val item: DynamicCellItem = stack.item as? DynamicCellItem ?: return@register null
            val fluid: Fluid = item.getFluid(stack).notEmptyOrNull() ?: return@register null
            return@register FullItemFluidStorage(context, TRContent.CELL, FluidVariant.of(fluid), FluidConstants.BUCKET)
        }
    }
}
