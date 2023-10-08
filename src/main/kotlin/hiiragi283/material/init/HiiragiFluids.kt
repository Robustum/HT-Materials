package hiiragi283.material.init

import hiiragi283.material.api.fluid.MaterialFluid

object HiiragiFluids {

    fun registerMaterialFluids() {
        HiiragiRegistries.MATERIAL.getValues()
            .mapNotNull { it.fluid() }
            .forEach(MaterialFluid::register)
    }

}