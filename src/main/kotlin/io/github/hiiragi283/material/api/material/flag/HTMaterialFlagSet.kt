package io.github.hiiragi283.material.api.material.flag

import io.github.hiiragi283.material.api.material.HTMaterial

class HTMaterialFlagSet private constructor(set: Set<HTMaterialFlag>) : Set<HTMaterialFlag> by set {

    fun verify(material: HTMaterial) {
        this.forEach { it.verify(material) }
    }

    class Builder : MutableSet<HTMaterialFlag> by hashSetOf() {

        internal fun build() = HTMaterialFlagSet(this)

    }

}