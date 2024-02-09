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
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.registry.Registry
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

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
        MinecraftForge.EVENT_BUS.register(EventHandler)
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

    object EventHandler {
        @SubscribeEvent
        fun onWorldLoaded(event: WorldEvent.Load) {
            HTMaterialsAPIImpl.fluidManager = Builder().apply {
                // Register fluids from Vanilla's registry
                loadVanillaEntries()
                // Register fluids from common tag
                HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                    HTPlatformHelper.INSTANCE.getFluidTag(materialKey.getCommonId()).values().forEach { fluid ->
                        add(materialKey, fluid)
                    }
                }
            }.let { HTFluidManagerImpl(it) }
        }
    }
}
