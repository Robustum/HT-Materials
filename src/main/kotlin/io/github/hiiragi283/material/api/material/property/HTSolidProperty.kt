package io.github.hiiragi283.material.api.material.property

import net.minecraft.block.Block
import net.minecraft.tag.TagKey

class HTSolidProperty(
    var harvestLevel: TagKey<Block>? = null,
    var harvestTools: TagKey<Block>? = null
) : HTMaterialProperty {

    override fun verify(properties: HTMaterialProperties) {

    }

}