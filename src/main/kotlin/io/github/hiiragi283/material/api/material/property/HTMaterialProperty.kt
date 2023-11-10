package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial

interface HTMaterialProperty<T : HTMaterialProperty<T>> {

    val key: HTPropertyKey<T>

    fun verify(material: HTMaterial)

}