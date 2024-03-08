package io.github.hiiragi283.material.impl

import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidRegistry
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.tag.TagReloadedEvent
import net.minecraft.fluid.Fluid
import net.minecraft.tag.Tag

object HTFluidRegistryImpl : HTFluidRegistry {
    private val registeredFluidMap: MutableMap<Fluid, HTMaterialKey?> = hashMapOf()
    private val registeredTagMap: MutableMap<Tag<Fluid>, HTMaterialKey?> = hashMapOf()
    private var fluidToMaterialMap: MutableMap<Fluid, HTMaterialKey>? = null
    private var materialToFluidsMap: Multimap<HTMaterialKey, Fluid>? = null

    init {
        TagReloadedEvent.EVENT.register {
            fluidToMaterialMap = null
            materialToFluidsMap = null
        }
    }

    //    HTFluidRegistry    //

    private fun getEntryMapPair(): Pair<Map<Fluid, HTMaterialKey>, Multimap<HTMaterialKey, Fluid>> {
        var fluidToMaterial: MutableMap<Fluid, HTMaterialKey>? = fluidToMaterialMap
        var materialToFluid: Multimap<HTMaterialKey, Fluid>? = materialToFluidsMap
        if (fluidToMaterial == null || materialToFluid == null) {
            fluidToMaterial = mutableMapOf()
            materialToFluid = LinkedHashMultimap.create()!!
            // Reload from tags
            registeredTagMap.forEach { (tag: Tag<Fluid>, key: HTMaterialKey?) ->
                if (key == null) return@forEach
                tag.values().forEach { fluid: Fluid ->
                    fluidToMaterial[fluid] = key
                    materialToFluid.put(key, fluid)
                }
            }
            // Reload from fluids
            registeredFluidMap.forEach { (fluid: Fluid, key: HTMaterialKey?) ->
                if (key == null) return@forEach
                fluidToMaterial[fluid] = key
                materialToFluid.put(key, fluid)
            }
            fluidToMaterialMap = fluidToMaterial
            materialToFluidsMap = materialToFluid
            HTMaterialsAPI.log("HTFluidRegistry reloaded!")
        }
        return fluidToMaterial to materialToFluid
    }

    override val allEntries: Map<Fluid, HTMaterialKey>
        get() = getEntryMapPair().first

    override fun getFluid(materialKey: HTMaterialKey): Collection<Fluid> = getEntryMapPair().second.get(materialKey)

    override fun contains(fluid: Fluid): Boolean = fluid in getEntryMapPair().first

    override fun contains(materialKey: HTMaterialKey): Boolean = getEntryMapPair().second.containsKey(materialKey)

    override fun get(fluid: Fluid): HTMaterialKey? = getEntryMapPair().first[fluid]

    override fun add(fluid: Fluid, value: HTMaterialKey?) {
        registeredFluidMap[fluid] = value
    }

    override fun add(tag: Tag<Fluid>, value: HTMaterialKey?) {
        registeredTagMap[tag] = value
    }

    override fun remove(fluid: Fluid) {
        add(fluid, null)
    }

    override fun remove(tag: Tag<Fluid>) {
        add(tag, null)
    }

    override fun clear(fluid: Fluid) {
        remove(fluid)
    }

    override fun clear(tag: Tag<Fluid>) {
        remove(tag)
    }
}
