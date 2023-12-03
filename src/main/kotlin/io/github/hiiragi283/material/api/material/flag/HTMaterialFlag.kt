package io.github.hiiragi283.material.api.material.flag

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.property.HTPropertyKey

class HTMaterialFlag private constructor(
    val name: String,
    private val requiredFlags: Set<HTMaterialFlag>,
    private val requiredProperties: Set<HTPropertyKey<*>>
) {

    init {
        map.putIfAbsent(name, this)
    }

    fun verify(material: HTMaterial) {
        requiredProperties.forEach { key: HTPropertyKey<*> ->
            if (!material.hasProperty(key)) {
                throw IllegalStateException("The material: $material has no property: ${key.name} but required for ${this.name}!")
            }
        }
        requiredFlags.forEach { flag: HTMaterialFlag ->
            if (!material.hasFlag(flag)) {
                throw IllegalStateException("The material: $material has no flag: ${flag.name} but required for ${this.name}!")
            }
        }
    }

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTMaterialFlag -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

    //    Builder    //

    class Builder(private val name: String) {

        @JvmField
        val requiredFlags: MutableSet<HTMaterialFlag> = mutableSetOf()

        @JvmField
        val requiredProperties: MutableSet<HTPropertyKey<*>> = mutableSetOf()

        internal fun build(): HTMaterialFlag = HTMaterialFlag(name, requiredFlags, requiredProperties)

    }

    companion object {

        //    Registry    //

        private val map: MutableMap<String, HTMaterialFlag> = mutableMapOf()

        @JvmField
        val REGISTRY: Map<String, HTMaterialFlag> = map

        //    Builder    //

        @JvmStatic
        fun create(name: String, init: Builder.() -> Unit = {}): HTMaterialFlag =
            Builder(name).apply(init).build()

        //    Flags    //

        @JvmField
        val GENERATE_BLOCk = create("generate_block") {
            requiredProperties.add(HTPropertyKey.SOLID)
        }

        @JvmField
        val GENERATE_DUST = create("generate_dust") {
            requiredProperties.add(HTPropertyKey.SOLID)
        }

        @JvmField
        val GENERATE_GEAR = create("generate_gear") {
            requiredProperties.add(HTPropertyKey.SOLID)
        }

        @JvmField
        val GENERATE_GEM = create("generate_gem") {
            requiredProperties.add(HTPropertyKey.SOLID)
            requiredProperties.add(HTPropertyKey.GEM)
        }

        @JvmField
        val GENERATE_INGOT = create("generate_ingot") {
            requiredProperties.add(HTPropertyKey.SOLID)
        }

        @JvmField
        val GENERATE_NUGGET = create("generate_nugget") {
            requiredProperties.add(HTPropertyKey.SOLID)
            requiredProperties.add(HTPropertyKey.METAL)
        }

        @JvmField
        val GENERATE_PLATE = create("generate_plate") {
            requiredProperties.add(HTPropertyKey.SOLID)
        }

        @JvmField
        val GENERATE_ROD = create("generate_rod") {
            requiredProperties.add(HTPropertyKey.SOLID)
        }

    }

}