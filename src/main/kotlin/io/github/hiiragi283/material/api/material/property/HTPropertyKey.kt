package io.github.hiiragi283.material.api.material.property

data class HTPropertyKey<T : HTMaterialProperty<T>>(val name: String, val clazz: Class<T>) {

    companion object {

        @JvmStatic
        inline fun <reified T : HTMaterialProperty<T>> create(name: String) = HTPropertyKey(name, T::class.java)

        val REGISTRY: Map<String, HTPropertyKey<*>>
            get() = map
        private val map: MutableMap<String, HTPropertyKey<*>> = mutableMapOf()

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T : HTMaterialProperty<T>> getAs(name: String): T? = map[name] as? T

        //    Keys    //

        @JvmField
        val FLUID: HTPropertyKey<HTFluidProperty> = create("fluid")

        @JvmField
        val SOLID: HTPropertyKey<HTSolidProperty> = create("solid")

        @JvmField
        val GEM: HTPropertyKey<HTGemProperty> = create("gem")

        @JvmField
        val METAL: HTPropertyKey<HTMetalProperty> = create("metal")

        @JvmField
        val STONE: HTPropertyKey<HTStoneProperty> = create("stone")

        @JvmField
        val WOOD: HTPropertyKey<HTWoodProperty> = create("wood")


    }

    init {
        map.putIfAbsent(name, this)
    }

    //    Any    //

    override fun toString(): String = name

}