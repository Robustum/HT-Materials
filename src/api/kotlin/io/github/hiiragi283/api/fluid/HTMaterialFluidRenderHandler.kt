package io.github.hiiragi283.api.fluid

import io.github.hiiragi283.api.material.HTMaterialKey
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView

@Suppress("UnstableApiUsage")
class HTMaterialFluidRenderHandler(val materialKey: HTMaterialKey) : FluidVariantRenderHandler {
    override fun getName(fluidVariant: FluidVariant): Text = materialKey.getTranslatedText()

    override fun appendTooltip(fluidVariant: FluidVariant, tooltip: MutableList<Text>, tooltipContext: TooltipContext) {
        materialKey.getMaterial().appendTooltip(null, ItemStack.EMPTY, tooltip)
    }

    override fun getColor(fluidVariant: FluidVariant, view: BlockRenderView?, pos: BlockPos?): Int = materialKey.getMaterial().color().rgb
}
