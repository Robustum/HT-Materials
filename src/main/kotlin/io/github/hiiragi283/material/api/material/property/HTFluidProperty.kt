package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes
import net.minecraft.fluid.Fluid

@Suppress("UnstableApiUsage")
class HTFluidProperty(
    var fluid: Fluid? = null,
    var defaultAmount: Long = FluidConstants.INGOT
) : HTMaterialProperty<HTFluidProperty> {

    fun getAttributes(): FluidVariantAttributeHandler? = fluid?.let(FluidVariantAttributes::getHandler)

    override val key: HTPropertyKey<HTFluidProperty> = HTPropertyKey.FLUID

    override fun verify(material: HTMaterial) {

    }

}