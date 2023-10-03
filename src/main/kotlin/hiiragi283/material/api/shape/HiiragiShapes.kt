package hiiragi283.material.api.shape

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants

@Suppress("UnstableApiUsage")
object HiiragiShapes {

    @JvmField
    val BLOCK = HiiragiShape("block", FluidConstants.BLOCK, "@_block")

    @JvmField
    val CASING = HiiragiShape("casing", FluidConstants.INGOT * 8, "@_casing")

    @JvmField
    val DUST = HiiragiShape("dust", FluidConstants.INGOT, "@_dust")

    @JvmField
    val GEAR = HiiragiShape("gear", FluidConstants.INGOT * 4, "@_gear")

    @JvmField
    val GEM = HiiragiShape("gem", FluidConstants.INGOT, "@_gem")

    @JvmField
    val INGOT = HiiragiShape("ingot", FluidConstants.INGOT, "@_ingot")

    @JvmField
    val NUGGET = HiiragiShape("nugget", FluidConstants.NUGGET, "@_nugget")

    @JvmField
    val ORE = HiiragiShape("ore", FluidConstants.INGOT * 2, "@_ore")

    @JvmField
    val PLATE = HiiragiShape("plate", FluidConstants.INGOT, "@_plate")

    @JvmField
    val ROD = HiiragiShape("rod", FluidConstants.INGOT / 2, "@_rod")

    //    State    //

    @JvmField
    val SOLID = HiiragiShape("solid", 0, "solid")

    @JvmField
    val LIQUID = HiiragiShape("liquid", 0, "liquid")

    @JvmField
    val GAS = HiiragiShape("gas", 0, "gas")

    //    Type    //

    @JvmField
    val METAL = HiiragiShape("metal", 0, "metal")

    fun register() {
        this::class.java.declaredFields
            .map { it.also { it.isAccessible = true } }
            .map { it.get(this) }
            .filterIsInstance<HiiragiShape>()
            .forEach(HiiragiShape::register)
    }

}