package io.github.hiiragi283.api.material.property

data class HTPropertyKey<T : HTMaterialProperty<T>>(val name: String, val objClass: Class<T>) {
    //    Any    //

    override fun toString(): String = name

    companion object {
        @JvmStatic
        inline fun <reified T : HTMaterialProperty<T>> create(name: String) = HTPropertyKey(name, T::class.java)
    }
}
