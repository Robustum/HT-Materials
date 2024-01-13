package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial

class HTStorageBlockProperty : HTMaterialProperty<HTStorageBlockProperty> {
    //    HTStorageBlockProperty    //

    override val key: HTPropertyKey<HTStorageBlockProperty> = HTPropertyKey.STORAGE_BLOCK

    override fun verify(material: HTMaterial) {
    }
}
