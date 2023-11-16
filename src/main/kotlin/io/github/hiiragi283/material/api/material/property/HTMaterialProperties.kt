package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial

class HTMaterialProperties {

    private val map: MutableMap<HTPropertyKey<*>, HTMaterialProperty<*>> = mutableMapOf()

    operator fun <T : HTMaterialProperty<T>> get(key: HTPropertyKey<T>): T? = key.clazz.cast(map[key])

    operator fun <T : HTMaterialProperty<T>> plusAssign(property: T) {
        map.putIfAbsent(property.key, property)
    }

    operator fun <T : HTMaterialProperty<T>> contains(key: HTPropertyKey<T>): Boolean = key in map

    fun verify(material: HTMaterial) {
        map.values.forEach { it.verify(material) }
    }

    //    Any    //

    override fun toString(): String = map.keys.joinToString(separator = ", ")

}