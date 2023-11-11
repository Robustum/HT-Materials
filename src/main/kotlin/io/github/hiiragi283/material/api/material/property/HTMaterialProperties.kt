package io.github.hiiragi283.material.api.material.property

class HTMaterialProperties() {

    private val map: MutableMap<HTPropertyKey<*>, HTMaterialProperty<*>> = mutableMapOf()

    operator fun get(key: HTPropertyKey<*>): HTMaterialProperty<*>? = map[key]

    fun <T : HTMaterialProperty<T>> getAs(key: HTPropertyKey<T>): T? = key.clazz.cast(get(key))

    operator fun <T : HTMaterialProperty<T>> plusAssign(property: T) {
        val key: HTPropertyKey<T> = property.key
        if (contains(key)) {
            throw IllegalArgumentException("Material Property: ${key.name} already registered!")
        }
        map[key] = property
    }

    fun <T : HTMaterialProperty<T>> addSafety(property: T) {
        if (!contains(property.key)) {
            plusAssign(property)
        }
    }

    operator fun <T : HTMaterialProperty<T>> contains(key: HTPropertyKey<T>): Boolean = key in map

    //    Any    //

    override fun toString(): String = map.keys.joinToString(separator = ", ")

}