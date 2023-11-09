package io.github.hiiragi283.material.api.material.property

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes
import net.minecraft.fluid.Fluid

@Suppress("UnstableApiUsage")
class HTFluidProperty(var fluid: Fluid? = null) : HTMaterialProperty {

    fun getAttributes(): FluidVariantAttributeHandler? = fluid?.let(FluidVariantAttributes::getHandler)

    override fun verify(properties: HTMaterialProperties) {

    }

}