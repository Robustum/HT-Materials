package io.github.hiiragi283.material.api.fluid

import com.google.common.collect.HashMultimap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.util.getAllModId
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object HTFluidManager {
    //    Fluid -> HTMaterialKey    //

    private val FLUID_TO_MAT: MutableMap<Fluid, HTMaterialKey> = hashMapOf()

    @JvmStatic
    fun getFluidToMaterialMap(): Map<Fluid, HTMaterialKey> = FLUID_TO_MAT

    @JvmStatic
    fun getMaterialKey(fluid: Fluid): HTMaterialKey? = FLUID_TO_MAT[fluid]

    @JvmStatic
    fun hasMaterialKey(fluid: Fluid): Boolean = fluid in FLUID_TO_MAT

    //    HTMaterialKey -> Fluid    //

    private val MAT_TO_FLUID: MutableMap<HTMaterialKey, Fluid> = hashMapOf()

    @JvmStatic
    fun getDefaultFluidMap(): Map<HTMaterialKey, Fluid> = MAT_TO_FLUID

    @JvmStatic
    fun getDefaultFluid(material: HTMaterialKey): Fluid? = MAT_TO_FLUID[material]

    @JvmStatic
    fun hasDefaultFluid(material: HTMaterialKey): Boolean = material in MAT_TO_FLUID

    //   HTMaterialKey -> Collection<Fluid>    //

    private val MAT_TO_FLUIDS: Multimap<HTMaterialKey, Fluid> = HashMultimap.create()

    @JvmStatic
    fun getMaterialToFluidsMap(): ImmutableMultimap<HTMaterialKey, Fluid> = ImmutableMultimap.copyOf(MAT_TO_FLUIDS)

    @JvmStatic
    fun getFluids(material: HTMaterialKey): Collection<Fluid> = MAT_TO_FLUIDS[material] ?: setOf()

    //    Registration    //

    private fun checkFluidNotEmpty(fluid: Fluid) {
        check(fluid != Fluids.EMPTY) { "The Entry is empty!" }
    }

    @JvmStatic
    @JvmSynthetic
    internal fun register(material: HTMaterialKey, fluid: Fluid) {
        checkFluidNotEmpty(fluid)
        if (fluid is FlowableFluid) {
            registerInternal(material, fluid.still)
            registerInternal(material, fluid.flowing)
        } else {
            registerInternal(material, fluid)
        }
    }

    @JvmSynthetic
    private fun registerInternal(material: HTMaterialKey, fluid: Fluid) {
        // Fluid -> HTMaterialKey
        FLUID_TO_MAT.putIfAbsent(fluid, material)
        // HTMaterial -> Fluid
        if (!hasDefaultFluid(material)) {
            MAT_TO_FLUID[material] = fluid
            HTMaterials.log("The Fluid: ${Registry.FLUID.getId(fluid)} registered as Default Fluid for Material: $material!!")
        }
        // HTMaterial -> Collection<Fluid>
        MAT_TO_FLUIDS.put(material, fluid)
        // print info
        HTMaterials.log("The Fluid: ${Registry.FLUID.getId(fluid)} linked to Material: $material!")
    }

    @JvmStatic
    @JvmSynthetic
    internal fun forceRegister(material: HTMaterialKey, fluid: Fluid) {
        // Fluid -> HTMaterialKey
        FLUID_TO_MAT.putIfAbsent(fluid, material)
        // HTMaterial -> Fluid
        MAT_TO_FLUID[material] = fluid
        HTMaterials.log("The Fluid: ${Registry.FLUID.getId(fluid)} registered as Default Fluid for Material: $material!!")
        // HTMaterial -> Collection<Fluid>
        MAT_TO_FLUIDS.put(material, fluid)
        // print info
        HTMaterials.log("The Fluid: ${Registry.FLUID.getId(fluid)} linked to Material: $material!")
    }

    //    Initialization    //

    init {
        // Water
        forceRegister(HTVanillaMaterials.WATER, Fluids.WATER)
        forceRegister(HTVanillaMaterials.WATER, Fluids.FLOWING_WATER)
        // Lava
        forceRegister(HTVanillaMaterials.LAVA, Fluids.LAVA)
        forceRegister(HTVanillaMaterials.LAVA, Fluids.FLOWING_LAVA)
    }

    @JvmStatic
    internal fun registerAllFluids() {
        getAllModId().forEach { modid: String ->
            HTMaterial.getMaterialKeys().forEach { key ->
                Registry.FLUID.get(Identifier(modid, key.name)).run {
                    if (this != Fluids.EMPTY) {
                        register(key, this)
                    }
                }
            }
        }
    }
}
