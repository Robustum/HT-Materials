package io.github.hiiragi283.api.material.flag

import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.property.HTPropertyKey

class HTMaterialFlag private constructor(
    val name: String,
    private val requiredFlags: Set<HTMaterialFlag>,
    private val requiredProperties: Set<HTPropertyKey<*>>,
) {
    init {
        REGISTRY.putIfAbsent(name, this)
    }

    fun verify(material: HTMaterial) {
        requiredProperties.forEach { key: HTPropertyKey<*> ->
            check(material.hasProperty(key)) { "The material:$material has no property:${key.name} but required for ${this.name}!" }
        }
        requiredFlags.forEach { flag: HTMaterialFlag ->
            check(material.hasFlag(flag)) { "The material:$material has no flag:${flag.name} but required for ${this.name}!" }
        }
    }

    //    Any    //

    override fun equals(other: Any?): Boolean = (other as? HTMaterialFlag)?.name == this.name

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

    //    Builder    //

    class Builder(private val name: String) {
        @JvmField
        val requiredFlags: MutableSet<HTMaterialFlag> = hashSetOf()

        @JvmField
        val requiredProperties: MutableSet<HTPropertyKey<*>> = hashSetOf()

        internal fun build(): HTMaterialFlag = HTMaterialFlag(name, requiredFlags, requiredProperties)
    }

    companion object {
        //    Registry    //

        private val REGISTRY: MutableMap<String, HTMaterialFlag> = hashMapOf()

        @JvmStatic
        fun getFlag(key: String): HTMaterialFlag? = REGISTRY[key]

        //    Builder    //

        @JvmStatic
        fun create(name: String, init: Builder.() -> Unit = {}): HTMaterialFlag = Builder(name).apply(init).build()
    }
}
