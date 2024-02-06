package io.github.hiiragi283.api.material.property

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
    }
}
