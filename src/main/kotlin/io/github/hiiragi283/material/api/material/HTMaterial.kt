package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlags
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.common.HTMaterialsCommon
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.util.Identifier
import net.minecraft.util.registry.DefaultedRegistry
import net.minecraft.util.registry.Registry
import java.util.*
import java.util.function.Consumer

@Suppress("unused")
class HTMaterial private constructor(
    private val info: Info,
    private val properties: HTMaterialProperties,
    private val flags: HTMaterialFlags
) {

    companion object {

        @JvmStatic
        val REGISTRY: DefaultedRegistry<HTMaterial> = FabricRegistryBuilder.createDefaulted(
            HTMaterial::class.java,
            HTMaterialsCommon.id("material"),
            HTMaterialsCommon.id("empty")
        ).attribute(RegistryAttribute.SYNCED).buildAndRegister()

        @JvmStatic
        fun createMaterial(identifier: Identifier, consumer: Consumer<HTMaterial>): HTMaterial =
            HTMaterial(Info(identifier), HTMaterialProperties(), HTMaterialFlags())
                .also(consumer::accept)
                .also { mat -> Registry.register(REGISTRY, identifier, mat) }

    }

    //    Properties    //

    fun <T : HTMaterialProperty> getProperty(key: HTPropertyKey<T>): T? = properties.getAs(key)

    fun <T : HTMaterialProperty> getPropertyOpt(key: HTPropertyKey<T>): Optional<T> =
        Optional.ofNullable(getProperty(key))

    fun <T : HTMaterialProperty> hasProperty(key: HTPropertyKey<T>): Boolean = key in properties

    fun modifyProperties(consumer: Consumer<HTMaterialProperties>) {
        consumer.accept(properties)
    }

    //    Flags    //

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    fun modifyFlags(consumer: Consumer<HTMaterialFlags>) {
        consumer.accept(flags)
    }

    //    Info    //

    fun getIdentifier(): Identifier = info.identifier

    fun getColor(): Int = info.color

    fun getFormula(): String = info.formula

    fun modifyInfo(consumer: Consumer<Info>) {
        consumer.accept(info)
    }

    data class Info(
        val identifier: Identifier,
        var color: Int = -1,
        var formula: String = ""
    )

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTMaterial -> false
        else -> other.info.identifier == this.info.identifier
    }

    override fun hashCode(): Int = info.identifier.hashCode()

    override fun toString(): String = info.identifier.toString()

}