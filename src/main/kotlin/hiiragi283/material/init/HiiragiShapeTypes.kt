package hiiragi283.material.init

import hiiragi283.material.api.shape.HiiragiShapeType

object HiiragiShapeTypes {

    @JvmField
    val EMPTY = HiiragiShapeType("empty")

    @JvmField
    val WILDCARD: HiiragiShapeType = HiiragiShapeType("wildcard")

    @JvmField
    val SOLID: HiiragiShapeType = EMPTY.child(
        "solid",
        HiiragiShapes.DUST,
        HiiragiShapes.ORE,
        HiiragiShapes.SOLID
    )

    @JvmField
    val GAS: HiiragiShapeType = EMPTY.child("gas", HiiragiShapes.GAS)

    @JvmField
    val GEM_4x: HiiragiShapeType = SOLID.child("gem_4x", HiiragiShapes.GEM)

    @JvmField
    val GEM_4x_ADVANCED: HiiragiShapeType = SOLID.child(
        "gem_4x",
        HiiragiShapes.PLATE,
        HiiragiShapes.ROD
    )

    @JvmField
    val GEM_9x: HiiragiShapeType = GEM_4x.child("gem_9x", HiiragiShapes.BLOCK)

    @JvmField
    val GEM_9x_ADVANCED: HiiragiShapeType = GEM_9x.child(
        "gem_4x_advanced",
        HiiragiShapes.PLATE,
        HiiragiShapes.ROD
    )

    @JvmField
    val GEM_AMORPHOUS: HiiragiShapeType = GEM_9x.child("amorphous")

    @JvmField
    val GEM_COAL: HiiragiShapeType = GEM_9x.child("coal")

    @JvmField
    val GEM_CUBIC: HiiragiShapeType = GEM_9x.child("cubic")

    @JvmField
    val GEM_DIAMOND: HiiragiShapeType = GEM_9x_ADVANCED.child("diamond")

    @JvmField
    val GEM_EMERALD: HiiragiShapeType = GEM_9x_ADVANCED.child("emerald")

    @JvmField
    val GEM_LAPIS: HiiragiShapeType = GEM_9x_ADVANCED.child("lapis")

    @JvmField
    val GEM_QUARTZ: HiiragiShapeType = GEM_4x_ADVANCED.child("quartz")

    @JvmField
    val GEM_RUBY: HiiragiShapeType = GEM_9x_ADVANCED.child("ruby")

    @JvmField
    val LIQUID: HiiragiShapeType = EMPTY.child("liquid", HiiragiShapes.LIQUID)

    @JvmField
    val METAL_COMMON: HiiragiShapeType = SOLID.child(
        "metal_common",
        HiiragiShapes.BLOCK,
        HiiragiShapes.INGOT,
        HiiragiShapes.METAL,
        HiiragiShapes.NUGGET
    )

    @JvmField
    val METAL_ADVANCED: HiiragiShapeType = METAL_COMMON.child(
        "metal_advanced",
        HiiragiShapes.CASING,
        HiiragiShapes.GEAR,
        HiiragiShapes.PLATE,
        HiiragiShapes.ROD
    )

    @JvmField
    val STONE: HiiragiShapeType = SOLID.child(
        "stone",
        HiiragiShapes.CASING,
        HiiragiShapes.GEAR,
        HiiragiShapes.PLATE,
        HiiragiShapes.ROD
    )

    @JvmField
    val WOOD: HiiragiShapeType = SOLID.child(
        "wood",
        HiiragiShapes.CASING,
        HiiragiShapes.GEAR,
        HiiragiShapes.PLATE,
        HiiragiShapes.ROD
    )

}