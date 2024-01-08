package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial

enum class HTGemProperty : HTMaterialProperty<HTGemProperty> {
    AMETHYST,
    COAL,
    CUBIC,
    DIAMOND,
    EMERALD,
    LAPIS,
    QUARTZ,
    RUBY;

    override val key: HTPropertyKey<HTGemProperty> = HTPropertyKey.GEM

    override fun verify(material: HTMaterial) {
        if (material.hasProperty(HTPropertyKey.METAL)) {
            throw IllegalStateException("Material: has both Metal and Gem Property, which is not allowed!")
        }
    }

}