package io.github.hiiragi283.material.impl.fluid

import com.google.common.collect.HashMultimap
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.material.impl.HTMaterialsAPIImpl
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.registry.Registry

internal class HTFluidManagerImpl(builder: Builder) : HTFluidManager {
    init {
        // Register fluids from Vanilla's registry
        builder.loadVanillaEntries()
        HTPlatformHelper.INSTANCE.getAllModId().forEach { modid: String ->
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { key ->
                Registry.FLUID.get(key.getIdentifier(modid)).run { builder.add(key, this) }
            }
        }
        // Register event
        ServerWorldEvents.LOAD.register(::onWorldLoad)
    }

    override val fluidToMaterialMap: ImmutableMap<Fluid, HTMaterialKey> = ImmutableMap.copyOf(builder.fluidToMaterialMap)

    override val materialToFluidsMap: ImmutableMultimap<HTMaterialKey, Fluid> = ImmutableMultimap.copyOf(builder.materialToFluidsMap)

    class Builder : HTFluidManager.Builder {
        val fluidToMaterialMap: MutableMap<Fluid, HTMaterialKey> = mutableMapOf()

        val materialToFluidsMap: Multimap<HTMaterialKey, Fluid> = HashMultimap.create()

        override fun add(materialKey: HTMaterialKey, fluid: Fluid) {
            if (fluid == Fluids.EMPTY) return
            if (fluid !is FlowableFluid) return
            fluidToMaterialMap[fluid.still] = materialKey
            materialToFluidsMap.put(materialKey, fluid.still)
        }

        fun loadVanillaEntries() {
            add(HTMaterialKeys.WATER, Fluids.WATER)
            add(HTMaterialKeys.LAPIS, Fluids.LAVA)
        }
    }
}

@Suppress("UNUSED_PARAMETER")
private fun onWorldLoad(server: MinecraftServer, world: ServerWorld) {
    HTMaterialsAPIImpl.fluidManager = HTFluidManagerImpl.Builder().apply {
        // Register fluids from Vanilla's registry
        loadVanillaEntries()
        // Register fluids from common tag
        HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
            TagRegistry.fluid(materialKey.getCommonId()).values().forEach { fluid ->
                add(materialKey, fluid)
            }
        }
    }.let { HTFluidManagerImpl(it) }
}
