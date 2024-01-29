package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.property.component.HTCompoundProperty
import io.github.hiiragi283.material.api.material.property.component.HTHydrateProperty
import io.github.hiiragi283.material.api.material.property.component.HTMixtureProperty
import io.github.hiiragi283.material.api.material.property.component.HTPolymerProperty

data class HTPropertyKey<T : HTMaterialProperty<T>>(val name: String, val objClass: Class<T>) {
    init {
        REGISTRY.putIfAbsent(name, this)
    }

    //    Any    //

    override fun toString(): String = name

    companion object {
        @JvmStatic
        inline fun <reified T : HTMaterialProperty<T>> create(name: String) = HTPropertyKey(name, T::class.java)

        private val REGISTRY: MutableMap<String, HTPropertyKey<*>> = hashMapOf()

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T : HTMaterialProperty<T>> getKey(name: String): T? = REGISTRY[name] as? T

        //    Keys    //

        @JvmField
        val COMPOUND: HTPropertyKey<HTCompoundProperty> = create("component")

        @JvmField
        val HYDRATE: HTPropertyKey<HTHydrateProperty> = create("hydrate")

        @JvmField
        val MIXTURE: HTPropertyKey<HTMixtureProperty> = create("mixture")

        @JvmField
        val POLYMER: HTPropertyKey<HTPolymerProperty> = create("polymer")
    }
}
