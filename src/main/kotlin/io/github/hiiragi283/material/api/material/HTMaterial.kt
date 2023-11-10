package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlags
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.common.HTMaterialsCommon
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.client.resource.language.I18n
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.SimpleRegistry
import java.util.*

@Suppress("unused")
class HTMaterial private constructor(
    private val info: Info,
    private val properties: HTMaterialProperties,
    private val flags: HTMaterialFlags
) {

    companion object {

        @JvmField
        val REGISTRY: SimpleRegistry<HTMaterial> = FabricRegistryBuilder.createSimple(
            HTMaterial::class.java,
            HTMaterialsCommon.id("material")
        ).attribute(RegistryAttribute.SYNCED).buildAndRegister()

        @JvmOverloads
        internal fun createMaterial(
            identifier: Identifier,
            preInit: HTMaterial.() -> Unit = {},
            init: HTMaterial.() -> Unit = {},
        ): HTMaterial = HTMaterial(Info(identifier), HTMaterialProperties(), HTMaterialFlags())
            .apply(preInit)
            .apply(init)
            .let { mat -> Registry.register(REGISTRY, identifier, mat) }

    }

    //    Properties    //

    fun getProperties(): HTMaterialProperties = HTMaterialProperties(properties)

    fun <T : HTMaterialProperty<T>> getProperty(key: HTPropertyKey<T>): T? = properties.getAs(key)

    fun <T : HTMaterialProperty<T>> getPropertyOpt(key: HTPropertyKey<T>): Optional<T> =
        Optional.ofNullable(getProperty(key))

    fun <T : HTMaterialProperty<T>> hasProperty(key: HTPropertyKey<T>): Boolean = key in properties

    fun modifyProperties(init: HTMaterialProperties.() -> Unit) {
        properties.init()
    }

    //    Flags    //

    fun getFlags(): HTMaterialFlags = HTMaterialFlags(flags)

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    fun modifyFlags(init: HTMaterialFlags.() -> Unit) {
        flags.init()
    }

    //    Info    //

    fun getInfo(): Info = info.copy()

    fun getIdentifier(): Identifier = info.identifier

    fun getBlockMultiplier(): Int = info.blockMultiplier

    fun getBlockAmount(): Long = (getProperty(HTPropertyKey.FLUID)?.defaultAmount ?: 0) * getBlockMultiplier()

    fun getColor(): Int = info.color

    fun getFormula(): String = info.formula

    fun getTranslatedName(): String = I18n.translate(info.translationKey)

    fun modifyInfo(init: Info.() -> Unit) {
        info.init()
    }

    data class Info(
        val identifier: Identifier,
        var blockMultiplier: Int = 9,
        var color: Int = -1,
        var formula: String = "",
        var translationKey: String = "material.${identifier.namespace}.${identifier.path}"
    )

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTMaterial -> false
        else -> other.getIdentifier() == this.getIdentifier()
    }

    override fun hashCode(): Int = getIdentifier().hashCode()

    override fun toString(): String = getIdentifier().toString()

}