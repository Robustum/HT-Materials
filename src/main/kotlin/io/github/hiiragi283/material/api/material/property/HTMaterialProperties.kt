package io.github.hiiragi283.material.api.material.property

class HTMaterialProperties {

    private val map: MutableMap<HTPropertyKey<*>, HTMaterialProperty> = mutableMapOf()

    operator fun <T : HTMaterialProperty> get(key: HTPropertyKey<T>): HTMaterialProperty? = map[key]

    fun <T : HTMaterialProperty> getAs(key: HTPropertyKey<T>): T? = key.clazz.cast(get(key))

    operator fun <T : HTMaterialProperty> set(key: HTPropertyKey<T>, property: T) {
        if (contains(key)) {
            throw IllegalArgumentException("Material Property: ${key.name} already registered!")
        }
        map[key] = property
    }

    fun <T : HTMaterialProperty> setSafety(key: HTPropertyKey<T>, property: T) {
        if (!contains(key)) {
            set(key, property)
        }
    }

    operator fun <T : HTMaterialProperty> contains(key: HTPropertyKey<T>): Boolean = key in map

    //    Any    //

    override fun toString(): String = map.keys.joinToString(separator = ", ")

}