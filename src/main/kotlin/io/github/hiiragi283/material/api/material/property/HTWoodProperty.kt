package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial

object HTWoodProperty : HTMaterialProperty<HTWoodProperty> {

    override val key: HTPropertyKey<HTWoodProperty> = HTPropertyKey.WOOD

    override fun verify(material: HTMaterial) {

    }

}