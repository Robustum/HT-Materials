package io.github.hiiragi283.material.impl

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.tag.TagsUpdatedEvent
import net.minecraft.fluid.Fluid
import net.minecraft.tag.Tag
import net.minecraft.tag.TagManager

object HTFluidManagerImpl : HTFluidManager, TagsUpdatedEvent {
    init {
        TagsUpdatedEvent.EVENT.register(::onUpdated)
    }

    //    HTFluidRegistry    //

    override var fluidToMaterialMap: Map<Fluid, HTMaterialKey> = mapOf()
        private set

    override var materialToFluidsMap: Map<HTMaterialKey, Set<Fluid>> = mapOf()
        private set

    override fun onUpdated(tagManager: TagManager, isClient: Boolean) {
        if (isClient) return
        HTFluidManager.Builder().run {
            // Register from Addons
            HTMaterialsAPI.INSTANCE.forEachAddon { it.modifyFluidManager(this) }
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
        HTMaterialsAPI.LOGGER.info("HTFluidManager reloaded!")
    }
}
