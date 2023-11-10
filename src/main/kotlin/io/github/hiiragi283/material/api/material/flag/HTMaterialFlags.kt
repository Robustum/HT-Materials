package io.github.hiiragi283.material.api.material.flag

import io.github.hiiragi283.material.api.material.HTMaterial

class HTMaterialFlags() {

    private val flags: MutableSet<HTMaterialFlag> = mutableSetOf()

    constructor(flags: HTMaterialFlags) : this() {
        this.flags.addAll(flags.flags)
    }

    fun addFlags(vararg flag: HTMaterialFlag) = also { flag.forEach(flags::add) }

    operator fun contains(flag: HTMaterialFlag): Boolean = flag in flags

    fun verify(material: HTMaterial) {
        flags.forEach { it.verify(material) }
    }

    //    Any    //

    override fun toString(): String = flags.joinToString(separator = ", ")

}