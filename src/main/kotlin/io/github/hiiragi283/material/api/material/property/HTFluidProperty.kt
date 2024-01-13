package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.fluid.HTFluidManager
import io.github.hiiragi283.material.api.fluid.HTMaterialFluid
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import net.minecraft.fluid.Fluid

class HTFluidProperty : HTMaterialProperty<HTFluidProperty> {

    var temperature: Int = 300
    var viscosity: Int = 1000
    var isGas: Boolean = false

    private lateinit var fluid: Fluid

    internal fun init(key: HTMaterialKey) {
        if (this::fluid.isInitialized) return
        HTMaterialFluid.Flowing(key)
        HTMaterialFluid.Still(key).run {
            HTMaterialFluid.Bucket(this)
            fluid = HTFluidManager.getDefaultFluid(key) ?: this
        }
    }

    //    HTMaterialProperty    //

    override val key: HTPropertyKey<HTFluidProperty> = HTPropertyKey.FLUID

    override fun verify(material: HTMaterial) {

    }

}