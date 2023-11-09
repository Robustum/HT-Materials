package io.github.hiiragi283.material.api.material.property

import net.minecraft.tag.BlockTags

class HTMetalProperty : HTMaterialProperty {

    override fun verify(properties: HTMaterialProperties) {
        properties.setSafety(
            HTPropertyKey.SOLID,
            HTSolidProperty(BlockTags.NEEDS_STONE_TOOL, BlockTags.PICKAXE_MINEABLE)
        )
        if (HTPropertyKey.GEM in properties) {
            throw IllegalStateException("Material: has both Metal and Gem Property, which is not allowed!")
        }
    }

}