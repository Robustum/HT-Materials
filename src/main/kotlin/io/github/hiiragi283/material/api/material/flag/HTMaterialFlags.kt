package io.github.hiiragi283.material.api.material.flag

import io.github.hiiragi283.material.api.material.HTMaterial

class HTMaterialFlags {

    private val flags: MutableSet<HTMaterialFlag> = mutableSetOf()

    fun addFlags(vararg flag: HTMaterialFlag) = also { flag.forEach(flags::add) }

    fun removeFlags(vararg flag: HTMaterialFlag) = also { flag.forEach(flags::remove) }

    operator fun contains(flag: HTMaterialFlag): Boolean = flag in flags

    fun verify(material: HTMaterial) {
        flags.forEach { it.verify(material) }
    }

    //    Any    //

    override fun toString(): String = flags.joinToString(separator = ", ")

}