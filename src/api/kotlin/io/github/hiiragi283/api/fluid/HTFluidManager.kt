package io.github.hiiragi283.api.fluid

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.asFlowableOrNull
import io.github.hiiragi283.api.extension.id
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.tag.Tag

interface HTFluidManager {
    // Fluid -> Fluid

    val fluidToMaterialMap: Map<Fluid, HTMaterialKey>

    operator fun get(fluid: Fluid): HTMaterialKey? = fluidToMaterialMap[fluid]

    operator fun contains(fluid: Fluid): Boolean = fluid in fluidToMaterialMap

    // HTMaterialKey -> Fluid

    fun getDefaultFluid(materialKey: HTMaterialKey): Fluid? {
        val fluids: Collection<Fluid> = get(materialKey)
        for (fluid in fluids) {
            val namespace = fluid.id.namespace
            return when (namespace) {
                "minecraft" -> fluid
                HTMaterialsAPI.MOD_ID -> fluid
                else -> continue
            }
        }
        return fluids.firstOrNull()
    }

    // HTMaterialKey -> Collection<Fluid>

    val materialToFluidsMap: Map<HTMaterialKey, Set<Fluid>>

    operator fun get(materialKey: HTMaterialKey): Set<Fluid> = materialToFluidsMap.getOrDefault(materialKey, setOf())

    operator fun contains(materialKey: HTMaterialKey): Boolean = materialKey in materialToFluidsMap

    //    Builder    //

    class Builder {
        val registeredFluidMap: Map<Fluid, HTMaterialKey>
            get() = registeredFluidMap1
        private val registeredFluidMap1: MutableMap<Fluid, HTMaterialKey> = hashMapOf()

        val registeredTagMap: Map<Tag<Fluid>, HTMaterialKey>
            get() = registeredTagMap1
        private val registeredTagMap1: MutableMap<Tag<Fluid>, HTMaterialKey> = hashMapOf()

        fun add(materialKey: HTMaterialKey, fluid: Fluid) {
            fluid.asFlowableOrNull?.still?.run { registeredFluidMap1[this] = materialKey }
        }

        fun add(materialKey: HTMaterialKey, tag: Tag<Fluid>) {
            registeredTagMap1[tag] = materialKey
        }

        operator fun set(materialKey: HTMaterialKey, fluid: Fluid) {
            add(materialKey, fluid)
        }

        operator fun set(materialKey: HTMaterialKey, tag: Tag<Fluid>) {
            add(materialKey, tag)
        }

        fun remove(fluid: Fluid) {
            fluid.asFlowableOrNull?.still?.run {
                registeredFluidMap1.remove(fluid)
            }
        }

        fun remove(key: Tag<Fluid>) {
            registeredTagMap1.remove(key)
        }

        operator fun minusAssign(fluid: Fluid) {
            remove(fluid)
        }

        operator fun minusAssign(tag: Tag<Fluid>) {
            remove(tag)
        }

        init {
            add(HTMaterialKeys.WATER, Fluids.WATER)
            add(HTMaterialKeys.LAVA, Fluids.LAVA)
        }
    }
}