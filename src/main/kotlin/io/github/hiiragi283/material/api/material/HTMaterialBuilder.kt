package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.*

object HTMaterialBuilder {

    @JvmStatic
    fun createFluid(name: String, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        name,
        preInit = {
            modifyProperties {
                this += HTFluidProperty()
            }
        },
        init = init
    )

    @JvmStatic
    fun createGem(name: String, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        name,
        preInit = {
            modifyProperties {
                this += HTSolidProperty.createGem()
                this += HTGemProperty()
            }
        },
        init = init
    )

    @JvmStatic
    fun createMetal(name: String, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        name,
        preInit = {
            modifyProperties {
                this += HTSolidProperty.createMetal()
                this += HTMetalProperty()
            }
        },
        init = init
    )

    @JvmStatic
    fun createSolid(name: String, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        name,
        preInit = {
            modifyProperties { this += HTSolidProperty.createDust() }
            modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
        },
        init = init
    )

    @JvmStatic
    fun createStone(name: String, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        name,
        preInit = {
            modifyProperties {
                this += HTSolidProperty.createStone()
                this += HTStoneProperty()
            }
            modifyFlags {
                addFlags(
                    HTMaterialFlag.GENERATE_DUST,
                    HTMaterialFlag.GENERATE_PLATE
                )
            }
        },
        init = init
    )

    @JvmStatic
    fun createWood(name: String, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        name,
        preInit = {
            modifyProperties {
                this += HTSolidProperty.createWood()
                this += HTWoodProperty()
            }
            modifyFlags { addFlags(HTMaterialFlag.GENERATE_DUST) }
        },
        init = init
    )

}