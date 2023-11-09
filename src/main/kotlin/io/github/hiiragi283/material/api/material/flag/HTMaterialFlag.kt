package io.github.hiiragi283.material.api.material.flag

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import java.util.*
import java.util.function.Consumer

class HTMaterialFlag private constructor(
    val name: String,
    private val requiredFlags: Set<HTMaterialFlag>,
    private val requiredProperties: Set<HTPropertyKey<*>>
) {

    companion object {

        //    Registry    //

        val REGISTRY: Map<String, HTMaterialFlag>
            get() = map
        private val map: MutableMap<String, HTMaterialFlag> = mutableMapOf()

        @JvmStatic
        fun getOptional(name: String): Optional<HTMaterialFlag> = Optional.ofNullable(map[name])

        //    Builder    //

        @JvmStatic
        fun create(name: String, consumer: Consumer<Builder> = Consumer {}): HTMaterialFlag =
            Builder(name).also(consumer::accept).build()

        //    Flags    //

        @JvmField
        val GENERATE_INGOT: HTMaterialFlag = create("generate_ingot") { builder ->
            builder.addProperty(HTPropertyKey.METAL)
        }

        @JvmField
        val FIREPROOF: HTMaterialFlag = create("fireproof")

    }

    init {
        map.putIfAbsent(name, this)
    }

    fun verify(material: HTMaterial) {
        requiredProperties.forEach { key: HTPropertyKey<*> ->
            if (!material.hasProperty(key)) {
                throw IllegalStateException("The material: $material has no property: ${key.name} but required!")
            }
        }
        requiredFlags.forEach { flag: HTMaterialFlag ->
            if (!material.hasFlag(flag)) {
                throw IllegalStateException("The material: $material has no flag: ${flag.name} but required!")
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

        val requiredFlags: MutableSet<HTMaterialFlag> = mutableSetOf()
        val requiredProperties: MutableSet<HTPropertyKey<*>> = mutableSetOf()

        fun addFlag(vararg flag: HTMaterialFlag) {
            flag.forEach(requiredFlags::add)
        }

        fun addProperty(vararg key: HTPropertyKey<*>) {
            key.forEach(requiredProperties::add)
        }

        fun build(): HTMaterialFlag = HTMaterialFlag(name, requiredFlags, requiredProperties)

    }

}