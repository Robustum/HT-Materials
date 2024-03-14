package io.github.hiiragi283.material.compat

import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extension.notEmptyOrNull
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.shape.HTShapes
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
    val SMALL_DUST = HTShape("small_dust")

    override fun modifyShapeRegistry(builder: HTShapeRegistry.Builder) {
        builder += SMALL_DUST
    }

    override fun modifyPartManager(builder: HTPartManager.Builder) {
        builder.add(HTMaterialKeys.PHOSPHORUS, HTShapes.DUST, TRContent.Dusts.PHOSPHOROUS)
        builder.add(HTMaterialKeys.PHOSPHORUS, SMALL_DUST, TRContent.SmallDusts.PHOSPHOROUS)
        builder.add(HTMaterialKeys.RUBY, HTShapes.GEM, TRContent.Gems.RUBY)
        builder.add(HTMaterialKeys.SAPPHIRE, HTShapes.GEM, TRContent.Gems.SAPPHIRE)
        builder.add(HTMaterialKeys.WOOD, HTShapes.DUST, TRContent.Dusts.SAW)
    }

    @Suppress("UnstableApiUsage")
    override fun postInitialize(envType: EnvType) {
        FluidStorage.combinedItemApiProvider(TRContent.CELL).register { context: ContainerItemContext ->
            val stack: ItemStack = context.itemVariant.toStack()
            val item: DynamicCellItem = stack.item as? DynamicCellItem ?: return@register null
            val fluid: Fluid = item.getFluid(stack).notEmptyOrNull ?: return@register null
            return@register FullItemFluidStorage(context, TRContent.CELL, FluidVariant.of(fluid), FluidConstants.BUCKET)
        }
    }
}
