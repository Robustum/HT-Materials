package io.github.hiiragi283.material.impl

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.api.util.getAllModId
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.registry.Registry

internal class HTFluidManagerImpl(map: DefaultedMap<HTMaterialKey, MutableCollection<Fluid>>) : HTFluidManager {
    override val fluidToMaterialMap: MutableMap<Fluid, HTMaterialKey> = mutableMapOf()

    override val materialToFluidsMap: Multimap<HTMaterialKey, Fluid> = HashMultimap.create()

    init {
        // Register fluids from Vanilla's registry
        loadVanillaEntries()
        getAllModId().forEach { modid: String ->
            HTMaterialsAPI.getInstance().materialRegistry().getKeys().forEach { key ->
                Registry.FLUID.get(key.getIdentifier(modid)).run { register(key, this) }
            }
        }
        // Register fluids from addons
        map.forEach { key, fluids ->
            fluids.forEach { fluid -> register(key, fluid) }
        }
        // Register event
        ServerWorldEvents.LOAD.register(::onWorldLoad)
    }

    private fun register(materialKey: HTMaterialKey, fluid: Fluid) {
        if (fluid == Fluids.EMPTY) return
        if (fluid !is FlowableFluid) return
        fluidToMaterialMap[fluid.still] = materialKey
        materialToFluidsMap.put(materialKey, fluid.still)
    }

    private fun loadVanillaEntries() {
        register(HTMaterialKeys.WATER, Fluids.WATER)
        register(HTMaterialKeys.LAPIS, Fluids.LAVA)
    }

    private fun onWorldLoad(server: MinecraftServer, world: ServerWorld) {
        // Clear old values
        fluidToMaterialMap.clear()
        materialToFluidsMap.clear()
        // Register fluids from Vanilla's registry
        loadVanillaEntries()
        // Register fluids from common tag
        HTMaterialsAPI.getInstance().materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
            TagRegistry.fluid(materialKey.getCommonId()).values().forEach { fluid ->
                register(materialKey, fluid)
            }
        }
    }
}
