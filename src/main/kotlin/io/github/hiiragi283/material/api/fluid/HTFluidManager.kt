package io.github.hiiragi283.material.api.fluid

import com.google.common.collect.HashMultimap
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.common.HTMaterialsCommon
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.registry.Registry

object HTFluidManager {

    //    Fluid -> HTMaterial    //

    private val fluidToMaterial: MutableMap<Fluid, HTMaterial> = mutableMapOf()

    @JvmStatic
    fun getFluidToMaterialMap(): Map<Fluid, HTMaterial> = fluidToMaterial

    @JvmStatic
    fun getMaterial(fluid: Fluid): HTMaterial? = fluidToMaterial[fluid]

    @JvmStatic
    fun hasMaterial(fluid: Fluid): Boolean = fluid in fluidToMaterial

    //    HTMaterial -> Fluid    //

    private val materialToFluid: MutableMap<HTMaterial, Fluid> = mutableMapOf()

    @JvmStatic
    fun getDefaultFluidMap(): Map<HTMaterial, Fluid> = materialToFluid

    @JvmStatic
    fun getDefaultFluid(material: HTMaterial): Fluid? = materialToFluid[material]

    @JvmStatic
    fun hasDefaultFluid(material: HTMaterial): Boolean = material in materialToFluid

    //   HTMaterial -> Collection<Fluid>    //

    private val materialToFluids: Multimap<HTMaterial, Fluid> = HashMultimap.create()

    @JvmStatic
    fun getMaterialToFluidsMap(): ImmutableMultimap<HTMaterial, Fluid> =
        ImmutableMultimap.copyOf(materialToFluids)

    @JvmStatic
    fun getFluids(material: HTMaterial): Collection<Fluid> = materialToFluids[material] ?: setOf()

    //    Registration    //

    private fun checkFluidNotEmpty(fluid: Fluid) {
        check(fluid != Fluids.EMPTY) { "The Entry is empty!" }
    }

    @JvmStatic
    fun register(material: HTMaterial, fluid: Fluid) {
        checkFluidNotEmpty(fluid)
        if (fluid is FlowableFluid) {
            registerInternal(material, fluid.still)
            registerInternal(material, fluid.flowing)
        } else {
            registerInternal(material, fluid)
        }
    }

    @JvmSynthetic
    private fun registerInternal(material: HTMaterial, fluid: Fluid) {
        //Fluid -> HTMaterial
        fluidToMaterial.putIfAbsent(fluid, material)
        //HTMaterial -> Fluid
        if (!hasDefaultFluid(material)) {
            materialToFluid[material] = fluid
            HTMaterialsCommon.LOGGER.info("The Fluid: ${Registry.FLUID.getId(fluid)} registered as Default Fluid for Material: $material!!")
        }
        //HTMaterial -> Collection<Fluid>
        materialToFluids.put(material, fluid)
        //print info
        HTMaterialsCommon.LOGGER.info("The Fluid: ${Registry.FLUID.getId(fluid)} linked to Material: $material!")
    }

    @JvmStatic
    @JvmSynthetic
    internal fun forceRegister(material: HTMaterial, fluid: Fluid) {
        //Fluid -> HTMaterial
        fluidToMaterial.putIfAbsent(fluid, material)
        //HTMaterial -> Fluid
        materialToFluid[material] = fluid
        HTMaterialsCommon.LOGGER.info("The Fluid: ${Registry.FLUID.getId(fluid)} registered as Default Fluid for Material: $material!!")
        //HTMaterial -> Collection<Fluid>
        materialToFluids.put(material, fluid)
        //print info
        HTMaterialsCommon.LOGGER.info("The Fluid: ${Registry.FLUID.getId(fluid)} linked to Material: $material!")
    }

    //    Initialization    //

    init {
        //Water
        forceRegister(HTVanillaMaterials.WATER, Fluids.WATER)
        forceRegister(HTVanillaMaterials.WATER, Fluids.FLOWING_WATER)
        //Lava
        forceRegister(HTVanillaMaterials.LAVA, Fluids.LAVA)
        forceRegister(HTVanillaMaterials.WATER, Fluids.FLOWING_LAVA)
    }

}