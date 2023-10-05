package hiiragi283.material.api.material

//    Material    //

fun materialOf(
    name: String,
    init: HiiragiMaterial.() -> Unit = {}
): HiiragiMaterial = HiiragiMaterial(name).also(init)

//    Isotope    //

fun isotopeOf(
    name: String,
    parent: HiiragiMaterial,
    init: HiiragiMaterial.() -> Unit = {}
): HiiragiMaterial = materialOf(name) {
    color = parent.color
    tempBoil = parent.tempBoil
    tempMelt = parent.tempMelt
    shapeType = parent.shapeType
}.also(init)