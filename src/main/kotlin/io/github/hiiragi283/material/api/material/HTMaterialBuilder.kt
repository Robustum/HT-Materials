package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.property.HTSolidProperty
import io.github.hiiragi283.material.api.material.property.HTStoneProperty
import io.github.hiiragi283.material.api.material.property.HTWoodProperty
import net.minecraft.tag.BlockTags
import net.minecraft.util.Identifier

object HTMaterialBuilder {

    @JvmStatic
    fun createStone(identifier: Identifier, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        identifier,
        preInit = {
            modifyProperties {
                this += HTSolidProperty(harvestTools = BlockTags.PICKAXE_MINEABLE)
                this += HTStoneProperty()
            }
        },
        init = init
    )

    @JvmStatic
    fun createWood(identifier: Identifier, init: HTMaterial.() -> Unit = {}): HTMaterial = HTMaterial.createMaterial(
        identifier,
        preInit = {
            modifyProperties {
                this += HTSolidProperty(harvestTools = BlockTags.PICKAXE_MINEABLE)
                this += HTWoodProperty()
            }
        },
        init = init
    )

}