package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial
import net.minecraft.tag.BlockTags

class HTMetalProperty : HTMaterialProperty<HTMetalProperty> {

    override val key: HTPropertyKey<HTMetalProperty> = HTPropertyKey.METAL

    override fun verify(material: HTMaterial) {
        val properties: HTMaterialProperties = material.getProperties()
        properties.addSafety(HTSolidProperty(BlockTags.NEEDS_STONE_TOOL, BlockTags.PICKAXE_MINEABLE))
        if (HTPropertyKey.GEM in properties) {
            throw IllegalStateException("Material: has both Metal and Gem Property, which is not allowed!")
        }
    }

}