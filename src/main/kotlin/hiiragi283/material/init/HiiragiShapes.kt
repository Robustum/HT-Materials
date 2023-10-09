package hiiragi283.material.init

import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.util.enableAccess
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import java.lang.reflect.Field

@Suppress("UnstableApiUsage")
object HiiragiShapes {

    @JvmField
    val BLOCK = HiiragiShape("block", FluidConstants.BLOCK, "@_blocks")

    @JvmField
    val CASING = HiiragiShape("casing", FluidConstants.INGOT * 8, "@_casings")

    @JvmField
    val DUST = HiiragiShape("dust", FluidConstants.INGOT, "@_dusts")

    @JvmField
    val GEAR = HiiragiShape("gear", FluidConstants.INGOT * 4, "@_gears")

    @JvmField
    val GEM = HiiragiShape("gem", FluidConstants.INGOT, "@_gems")

    @JvmField
    val INGOT = HiiragiShape("ingot", FluidConstants.INGOT, "@_ingots")

    @JvmField
    val NUGGET = HiiragiShape("nugget", FluidConstants.NUGGET, "@_nuggets")

    @JvmField
    val ORE = HiiragiShape("ore", FluidConstants.INGOT * 2, "@_ores")

    @JvmField
    val PLATE = HiiragiShape("plate", FluidConstants.INGOT, "@_plates")

    @JvmField
    val ROD = HiiragiShape("rod", FluidConstants.INGOT / 2, "@_rods")

    //    State    //

    @JvmField
    val SOLID = HiiragiShape("solid")

    @JvmField
    val LIQUID = HiiragiShape("liquid")

    @JvmField
    val GAS = HiiragiShape("gas")

    //    Type    //

    @JvmField
    val METAL = HiiragiShape("metal")

    fun register() {
        this::class.java.declaredFields
            .map(Field::enableAccess)
            .map { it.get(this) }
            .filterIsInstance<HiiragiShape>()
            .forEach(HiiragiShape::register)
    }

}