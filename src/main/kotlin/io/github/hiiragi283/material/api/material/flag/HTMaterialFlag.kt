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

        private val REGISTRY: MutableMap<String, HTMaterialFlag> = mutableMapOf()

        @JvmStatic
        operator fun get(name: String): HTMaterialFlag? = REGISTRY[name]

        @JvmStatic
        fun getOpt(name: String): Optional<HTMaterialFlag> = Optional.ofNullable(get(name))

        //    Builder    //

        @JvmStatic
        fun create(name: String, consumer: Consumer<Builder> = Consumer {}): HTMaterialFlag =
            Builder(name).also(consumer::accept).build()

        //    Flags    //

        val GENERATE_INGOT: HTMaterialFlag = create("generate_ingot") { builder ->
            builder.addProperty(HTPropertyKey.METAL)
        }

    }

    init {
        REGISTRY[name] = this
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