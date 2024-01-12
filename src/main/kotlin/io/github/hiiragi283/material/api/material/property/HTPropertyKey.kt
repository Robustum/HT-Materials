package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.registry.HTObjectKey

data class HTPropertyKey<T : HTMaterialProperty<T>>(
    override val name: String,
    override val objClass: Class<T>
) : HTObjectKey<T> {

    init {
        map.putIfAbsent(name, this)
    }

    //    Any    //

    override fun toString(): String = name

    companion object {

        @JvmStatic
        inline fun <reified T : HTMaterialProperty<T>> create(name: String) = HTPropertyKey(name, T::class.java)

        private val map: MutableMap<String, HTPropertyKey<*>> = mutableMapOf()

        @JvmField
        val REGISTRY: Map<String, HTPropertyKey<*>> = map

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T : HTMaterialProperty<T>> getKey(name: String): T? = REGISTRY[name] as? T

        //    Keys    //

        @JvmField
        val COMPOUND: HTPropertyKey<HTCompoundProperty> = create("component")

        @JvmField
        val FLUID: HTPropertyKey<HTFluidProperty> = create("fluid")

        @JvmField
        val HYDRATE: HTPropertyKey<HTHydrateProperty> = create("hydrate")

        @JvmField
        val MIXTURE: HTPropertyKey<HTMixtureProperty> = create("mixture")

        @JvmField
        val POLYMER: HTPropertyKey<HTPolymerProperty> = create("polymer")

        @JvmField
        val STORAGE_BLOCK: HTPropertyKey<HTStorageBlockProperty> = create("storage_block")

    }

}