package io.github.hiiragi283.material.api.material.materials

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTMetalProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.common.HTMaterialsCommon

object HTVanillaMaterials {

    @JvmField
    val IRON = HTMaterial.createMaterial(HTMaterialsCommon.id("iron")) { mat ->
        mat.modifyInfo { info ->
            info.formula = "Fe"
        }
        mat.modifyProperties { properties ->
            properties.setSafety(HTPropertyKey.METAL, HTMetalProperty())
        }
        mat.modifyFlags { flags ->
            flags.addFlags(HTMaterialFlag.GENERATE_INGOT)
        }
    }

}