package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial

class HTGemProperty : HTMaterialProperty<HTGemProperty> {

    override val key: HTPropertyKey<HTGemProperty> = HTPropertyKey.GEM

    override fun verify(material: HTMaterial) {
        if (material.hasProperty(HTPropertyKey.METAL)) {
            throw IllegalStateException("Material: has both Metal and Gem Property, which is not allowed!")
        }
    }

}