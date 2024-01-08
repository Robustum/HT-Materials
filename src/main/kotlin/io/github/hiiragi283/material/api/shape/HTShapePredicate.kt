package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import java.util.function.Predicate

class HTShapePredicate private constructor(
    private val disabled: Boolean,
    private val blackList: MutableList<HTMaterialKey>,
    private val whiteList: MutableList<HTMaterialKey>,
    private val requiredFlags: MutableList<HTMaterialFlag>,
    private val requiredProperties: MutableList<HTPropertyKey<*>>
) : Predicate<HTMaterial> {

    override fun test(material: HTMaterial): Boolean {
        val key: HTMaterialKey = material.key
        return when {
            disabled -> false
            blackList.contains(key) -> false
            whiteList.contains(key) -> true
            requiredFlags.all(material::hasFlag) && requiredProperties.all(material::hasProperty) -> true
            else -> false
        }
    }

    class Builder {

        @JvmField
        val blackList: MutableList<HTMaterialKey> = mutableListOf()

        @JvmField
        val whiteList: MutableList<HTMaterialKey> = mutableListOf()

        @JvmField
        val requiredFlags: MutableList<HTMaterialFlag> = mutableListOf()

        @JvmField
        val requiredProperties: MutableList<HTPropertyKey<*>> = mutableListOf()

        var disabled: Boolean = true

        internal fun build() = HTShapePredicate(disabled, blackList, whiteList, requiredFlags, requiredProperties)

    }

}