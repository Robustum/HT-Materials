package io.github.hiiragi283.material.api.material.flag

class HTMaterialFlagSet private constructor(set: Set<HTMaterialFlag>) : Set<HTMaterialFlag> by set {

    class Builder : MutableSet<HTMaterialFlag> by hashSetOf() {

        internal fun build() = HTMaterialFlagSet(this)

    }

}