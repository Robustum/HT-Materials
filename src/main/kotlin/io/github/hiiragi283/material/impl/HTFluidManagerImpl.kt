package io.github.hiiragi283.material.impl

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.runTryAndCatch
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.material.HTMaterialsCore
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.fluid.Fluid
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.tag.Tag

object HTFluidManagerImpl : HTFluidManager, ServerWorldEvents.Load {
    init {
        ServerWorldEvents.LOAD.register(::onWorldLoad)
    }

    //    HTFluidRegistry    //

    override var fluidToMaterialMap: Map<Fluid, HTMaterialKey> = mapOf()
        private set

    override var materialToFluidsMap: Map<HTMaterialKey, Set<Fluid>> = mapOf()
        private set

    override fun onWorldLoad(server: MinecraftServer, world: ServerWorld) {
        HTFluidManager.Builder().run {
            // Register from Addons
            HTMaterialsCore.addons.forEach { runTryAndCatch { it.modifyFluidManager(this) } }
            // Reload
            val fluidToMaterial: MutableMap<Fluid, HTMaterialKey> = mutableMapOf()
            val materialToFluid: MutableMap<HTMaterialKey, MutableSet<Fluid>> = mutableMapOf()
            // Reload from tags
            registeredTagMap.forEach { (tag: Tag<Fluid>, materialKey: HTMaterialKey) ->
                tag.values().forEach { fluid: Fluid ->
                    fluidToMaterial[fluid] = materialKey
                    materialToFluid.computeIfAbsent(materialKey) { mutableSetOf() }.add(fluid)
                }
            }
            // Reload from fluids
            registeredFluidMap.forEach { (fluid: Fluid, materialKey: HTMaterialKey) ->
                fluidToMaterial[fluid] = materialKey
                materialToFluid.computeIfAbsent(materialKey) { mutableSetOf() }.add(fluid)
            }
            // Initialize
            this@HTFluidManagerImpl.fluidToMaterialMap = fluidToMaterial
            this@HTFluidManagerImpl.materialToFluidsMap = materialToFluid
        }
        HTMaterialsAPI.log("HTFluidManager reloaded!")
    }
}