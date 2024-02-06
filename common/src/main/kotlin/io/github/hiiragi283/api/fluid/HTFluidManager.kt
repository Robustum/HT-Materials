package io.github.hiiragi283.api.fluid

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import io.github.hiiragi283.api.material.HTMaterialKey
import net.minecraft.fluid.Fluid

@JvmDefaultWithCompatibility
interface HTFluidManager {
    // Fluid -> HTMaterialKey

    val fluidToMaterialMap: ImmutableMap<Fluid, HTMaterialKey>

    fun getMaterialKey(fluid: Fluid): HTMaterialKey? = fluidToMaterialMap[fluid]

    fun hasMaterialKey(fluid: Fluid): Boolean = fluid in fluidToMaterialMap

    // HTMaterialKey -> Collection<Fluid>

    val materialToFluidsMap: ImmutableMultimap<HTMaterialKey, Fluid>

    fun getFluids(materialKey: HTMaterialKey): Collection<Fluid> = materialToFluidsMap.get(materialKey)

    fun hasFluid(materialKey: HTMaterialKey): Boolean = materialToFluidsMap.containsKey(materialKey)

    interface Builder {
        fun add(materialKey: HTMaterialKey, fluid: Fluid)
    }
}
