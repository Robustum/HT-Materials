package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial

class HTWoodProperty : HTMaterialProperty<HTWoodProperty> {

    override val key: HTPropertyKey<HTWoodProperty> = HTPropertyKey.WOOD

    override fun verify(material: HTMaterial) {
        material.modifyProperties(HTMaterialProperties::setWood)
    }

}