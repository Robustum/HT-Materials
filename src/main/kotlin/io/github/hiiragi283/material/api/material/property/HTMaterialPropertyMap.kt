package io.github.hiiragi283.material.api.material.property

class HTMaterialPropertyMap private constructor(
    map: Map<HTPropertyKey<*>, HTMaterialProperty<*>>,
) : Map<HTPropertyKey<*>, HTMaterialProperty<*>> by map {
    //    Any    //

    override fun toString(): String = this.keys.joinToString(separator = ", ")

    //    Builder    //

    class Builder {
        private val backingMap: MutableMap<HTPropertyKey<*>, HTMaterialProperty<*>> = hashMapOf()

        @JvmOverloads
        fun <T : HTMaterialProperty<T>> add(property: T, action: T.() -> Unit = {}) {
            backingMap[property.key] = property.apply(action)
        }

        fun remove(key: HTPropertyKey<*>) {
            backingMap.remove(key)
        }

        internal fun build() = HTMaterialPropertyMap(backingMap)
    }
}
