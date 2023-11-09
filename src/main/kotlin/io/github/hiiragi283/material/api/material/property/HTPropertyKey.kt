package io.github.hiiragi283.material.api.material.property

data class HTPropertyKey<T : HTMaterialProperty>(val name: String, val clazz: Class<T>) {

    companion object {

        @JvmStatic
        inline fun <reified T : HTMaterialProperty> create(name: String) = HTPropertyKey(name, T::class.java)

        private val map: MutableMap<String, HTPropertyKey<*>> = mutableMapOf()

        @JvmStatic
        operator fun get(name: String): HTPropertyKey<*>? = map[name]

        @Suppress("UNCHECKED_CAST")
        fun <T : HTMaterialProperty> getAs(name: String): T? = get(name) as? T

        //    Keys    //

        @JvmField
        val FLUID: HTPropertyKey<HTFluidProperty> = create("fluid")

        @JvmField
        val SOLID: HTPropertyKey<HTSolidProperty> = create("solid")

        @JvmField
        val GEM: HTPropertyKey<HTGemProperty> = create("gem")

        @JvmField
        val METAL: HTPropertyKey<HTMetalProperty> = create("metal")

    }

    init {
        map.putIfAbsent(name, this)
    }

    //    Any    //

    override fun toString(): String = name

}