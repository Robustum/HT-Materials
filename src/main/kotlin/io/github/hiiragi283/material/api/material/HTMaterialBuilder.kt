package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.property.*
import net.minecraft.tag.BlockTags

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
                this += HTSolidProperty(harvestTools = BlockTags.PICKAXE_MINEABLE)
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
                this += HTSolidProperty(harvestTools = BlockTags.PICKAXE_MINEABLE)
                this += HTMetalProperty()
            }
        },
        init = init
    )

    @JvmStatic
    fun createStone(name: String, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        name,
        preInit = {
            modifyProperties {
                this += HTSolidProperty(harvestTools = BlockTags.PICKAXE_MINEABLE)
                this += HTStoneProperty()
            }
        },
        init = init
    )

    @JvmStatic
    fun createWood(name: String, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        name,
        preInit = {
            modifyProperties {
                this += HTSolidProperty(harvestTools = BlockTags.PICKAXE_MINEABLE)
                this += HTWoodProperty()
            }
        },
        init = init
    )

}