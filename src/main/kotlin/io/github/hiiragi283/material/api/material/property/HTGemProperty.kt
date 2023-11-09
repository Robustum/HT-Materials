package io.github.hiiragi283.material.api.material.property

class HTGemProperty : HTMaterialProperty {

    override fun verify(properties: HTMaterialProperties) {
        if (HTPropertyKey.METAL in properties) {
            throw IllegalStateException("Material: has both Metal and Gem Property, which is not allowed!")
        }
    }

}