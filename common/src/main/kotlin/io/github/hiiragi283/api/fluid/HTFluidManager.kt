package io.github.hiiragi283.api.fluid

import com.google.common.collect.HashMultimap
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.registry.Registry

class HTFluidManager(builder: Builder) {
    init {
        HTPlatformHelper.INSTANCE.getAllModId().forEach { modid: String ->
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { key ->
                Registry.FLUID.get(key.getIdentifier(modid)).run { builder.add(key, this) }
            }
        }
    }

    // Fluid -> HTMaterialKey

    val fluidToMaterialMap: ImmutableMap<Fluid, HTMaterialKey> = ImmutableMap.copyOf(builder.fluidToMaterialMap)

    fun getMaterialKey(fluid: Fluid): HTMaterialKey? = fluidToMaterialMap[fluid]

    fun hasMaterialKey(fluid: Fluid): Boolean = fluid in fluidToMaterialMap

    // HTMaterialKey -> Collection<Fluid>

    val materialToFluidsMap: ImmutableMultimap<HTMaterialKey, Fluid> = ImmutableMultimap.copyOf(builder.materialToFluidsMap)

    fun getFluids(materialKey: HTMaterialKey): Collection<Fluid> = materialToFluidsMap.get(materialKey)

    fun hasFluid(materialKey: HTMaterialKey): Boolean = materialToFluidsMap.containsKey(materialKey)

    class Builder {
        internal val fluidToMaterialMap: MutableMap<Fluid, HTMaterialKey> = mutableMapOf()

        internal val materialToFluidsMap: Multimap<HTMaterialKey, Fluid> = HashMultimap.create()

        fun add(materialKey: HTMaterialKey, fluid: Fluid) {
            if (fluid == Fluids.EMPTY) return
            if (fluid !is FlowableFluid) return
            fluidToMaterialMap[fluid.still] = materialKey
            materialToFluidsMap.put(materialKey, fluid.still)
        }

        init {
            add(HTMaterialKeys.WATER, Fluids.WATER)
            add(HTMaterialKeys.LAPIS, Fluids.LAVA)
        }
    }
}
