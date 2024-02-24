package io.github.hiiragi283.api.fluid

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.allModsId
import io.github.hiiragi283.api.extension.asFlowableOrNull
import io.github.hiiragi283.api.extension.id
import io.github.hiiragi283.api.extension.notEmptyOrNull
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.registry.Registry

class HTFluidManager private constructor(builder: Builder) {
    init {
        // Register fluids from Vanilla's registry
        allModsId.forEach { modid: String ->
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { key ->
                Registry.FLUID.get(key.getIdentifier(modid)).notEmptyOrNull()?.run {
                    builder.add(key, this)
                }
            }
        }
    }

    // Fluid -> Entry

    val fluidToMaterialMap: ImmutableMap<Fluid, Entry> = ImmutableMap.copyOf(builder.fluidToMaterialMap)

    fun getEntry(fluid: Fluid): Entry? = fluidToMaterialMap[fluid]

    fun hasEntry(fluid: Fluid): Boolean = fluid in fluidToMaterialMap

    // HTMaterialKey -> Entry

    fun getDefaultEntry(materialKey: HTMaterialKey): Entry? {
        val entries: Collection<Entry> = getEntries(materialKey)
        for (entry in entries) {
            val namespace = entry.fluid.id.namespace
            return when (namespace) {
                "minecraft" -> entry
                HTMaterialsAPI.MOD_ID -> entry
                else -> continue
            }
        }
        return entries.firstOrNull()
    }

    // HTMaterialKey -> Collection<Entry>

    val materialToFluidsMap: ImmutableMultimap<HTMaterialKey, Entry> =
        ImmutableMultimap.copyOf(builder.materialToFluidsMap)

    fun getEntries(materialKey: HTMaterialKey): Collection<Entry> = materialToFluidsMap.get(materialKey)

    fun getFluids(materialKey: HTMaterialKey): Collection<Fluid> = getEntries(materialKey).map(Entry::fluid)

    fun hasEntry(materialKey: HTMaterialKey): Boolean = materialToFluidsMap.containsKey(materialKey)

    data class Entry(val materialKey: HTMaterialKey, val fluid: Fluid)

    class Builder {
        internal val fluidToMaterialMap: MutableMap<Fluid, Entry> = mutableMapOf()

        internal val materialToFluidsMap: Multimap<HTMaterialKey, Entry> = LinkedHashMultimap.create()

        fun add(materialKey: HTMaterialKey, fluid: Fluid) {
            fluid.asFlowableOrNull()?.still?.run {
                val entry = Entry(materialKey, this)
                fluidToMaterialMap[this] = entry
                materialToFluidsMap.put(materialKey, entry)
            }
        }

        fun remove(materialKey: HTMaterialKey, fluid: Fluid) {
            fluid.asFlowableOrNull()?.still?.run {
                val entry = Entry(materialKey, this)
                if (fluidToMaterialMap.contains(fluid) && materialToFluidsMap.containsKey(materialKey)) {
                    fluidToMaterialMap.remove(fluid, entry)
                    materialToFluidsMap.remove(materialKey, entry)
                }
            }
        }

        fun build() = HTFluidManager(this)

        init {
            add(HTMaterialKeys.WATER, Fluids.WATER)
            add(HTMaterialKeys.LAPIS, Fluids.LAVA)
        }
    }
}
