package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial
import net.minecraft.tag.BlockTags

class HTMetalProperty : HTMaterialProperty<HTMetalProperty> {

    override val key: HTPropertyKey<HTMetalProperty> = HTPropertyKey.METAL

    override fun verify(material: HTMaterial) {
        material.modifyProperties { this += HTSolidProperty(BlockTags.NEEDS_STONE_TOOL, BlockTags.PICKAXE_MINEABLE) }
        if (material.hasProperty(HTPropertyKey.GEM)) {
            throw IllegalStateException("Material: has both Metal and Gem Property, which is not allowed!")
        }
    }

}