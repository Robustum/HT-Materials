package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.fluid.MaterialFluid
import io.github.hiiragi283.material.api.material.HTMaterial
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes
import net.minecraft.fluid.Fluid

@Suppress("UnstableApiUsage")
class HTFluidProperty : HTMaterialProperty<HTFluidProperty> {

    lateinit var fluid: Fluid

    fun getAttributes(): FluidVariantAttributeHandler? = FluidVariantAttributes.getHandler(fluid)

    override val key: HTPropertyKey<HTFluidProperty> = HTPropertyKey.FLUID

    override fun verify(material: HTMaterial) {

    }

    internal fun init(material: HTMaterial) {
        if (this::fluid.isInitialized) return
        MaterialFluid.Flowing(material)
        val still = MaterialFluid.Still(material)
        MaterialFluid.Bucket(still)
        MaterialFluid.Block(still)
        fluid = still

    }

}