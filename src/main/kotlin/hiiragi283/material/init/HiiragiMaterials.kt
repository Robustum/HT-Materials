package hiiragi283.material.init

import hiiragi283.material.api.block.MaterialBlock
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.material.materialOf
import hiiragi283.material.util.HiiragiColor
import hiiragi283.material.util.enableAccess
import java.lang.reflect.Field

object HiiragiMaterials {

    //    Elements    //

    @JvmField
    val HYDROGEN = materialOf("hydrogen") {
        blockSettings = MaterialBlock.Settings.GAS
        color = HiiragiColor.BLUE.rgb
        formula = "H"
        molar = 1.0
        shapeType = HiiragiShapeTypes.GAS
        tempBoil = 20
        tempMelt = 14
    }

    @JvmField
    val HELIUM = materialOf("helium") {
        blockSettings = MaterialBlock.Settings.GAS
        color = HiiragiColor.YELLOW.rgb
        formula = "He"
        molar = 4.0
        shapeType = HiiragiShapeTypes.GAS
        tempBoil = 3
        tempMelt = 1
    }

    @JvmField
    val LITHIUM = materialOf("lithium") {
        color = HiiragiColor.GRAY.rgb
        formula = "Li"
        molar = 6.9
        shapeType = HiiragiShapeTypes.METAL_COMMON
        tempBoil = 1560
        tempMelt = 454
    }

    @JvmField
    val CARBON = materialOf("carbon") {
        blockSettings = MaterialBlock.Settings.DUST
        color = HiiragiColor.mixColor(HiiragiColor.BLACK, HiiragiColor.DARK_GRAY).rgb
        formula = "C"
        molar = 12.0
        shapeType = HiiragiShapeTypes.SOLID
        //tempBoil = 4300
        //tempMelt = 4000
    }

    @JvmField
    val NITROGEN = materialOf("nitrogen") {
        blockSettings = MaterialBlock.Settings.GAS
        color = HiiragiColor.AQUA.rgb
        formula = "N"
        molar = 14.0
        shapeType = HiiragiShapeTypes.GAS
        tempBoil = 77
        tempMelt = 63
    }

    @JvmField
    val OXYGEN = materialOf("oxygen") {
        blockSettings = MaterialBlock.Settings.GAS
        formula = "O"
        molar = 16.0
        shapeType = HiiragiShapeTypes.GAS
        tempBoil = 90
        tempMelt = 54
    }

    @JvmField
    val FLUORINE = materialOf("fluorine") {
        blockSettings = MaterialBlock.Settings.GAS
        color = HiiragiColor.GREEN.rgb
        formula = "F"
        molar = 19.0
        shapeType = HiiragiShapeTypes.GAS
        tempBoil = 85
        tempMelt = 54
    }

    @JvmField
    val NEON = materialOf("neon") {
        blockSettings = MaterialBlock.Settings.GAS
        color = HiiragiColor.LIGHT_PURPLE.rgb
        formula = "Ne"
        molar = 20.2
        shapeType = HiiragiShapeTypes.GAS
        tempBoil = 27
        tempMelt = 25
    }

    fun register() {
        this::class.java.declaredFields
            .map(Field::enableAccess)
            .map { it.get(this) }
            .filterIsInstance<HiiragiMaterial>()
            .forEach(HiiragiMaterial::register)
    }

}