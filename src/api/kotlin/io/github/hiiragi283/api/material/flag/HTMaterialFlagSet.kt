package io.github.hiiragi283.api.material.flag

class HTMaterialFlagSet private constructor(set: Set<HTMaterialFlag>) : Set<HTMaterialFlag> by set {
    class Builder : MutableSet<HTMaterialFlag> by hashSetOf() {
        fun build() = HTMaterialFlagSet(this)
    }
}
