package io.github.hiiragi283.api.fluid

import io.github.hiiragi283.api.material.HTMaterialKey
import net.minecraft.fluid.Fluid

interface HTFluidRegistry : Fluid2ObjectMap<HTMaterialKey?> {
    val allEntries: Map<Fluid, HTMaterialKey>

    fun getFluid(materialKey: HTMaterialKey): Collection<Fluid>

    fun convertFluids(fluid: Fluid): Collection<Fluid> = get(fluid)?.let(::getFluid) ?: listOf()

    operator fun contains(fluid: Fluid): Boolean

    operator fun contains(materialKey: HTMaterialKey): Boolean
}
