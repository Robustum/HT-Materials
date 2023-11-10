package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial
import net.minecraft.block.Block
import net.minecraft.tag.TagKey

class HTSolidProperty(
    var harvestLevel: TagKey<Block>? = null,
    var harvestTools: TagKey<Block>? = null
) : HTMaterialProperty<HTSolidProperty> {

    override val key: HTPropertyKey<HTSolidProperty> = HTPropertyKey.SOLID

    override fun verify(material: HTMaterial) {

    }

}