package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.HTMaterialsAPI
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlags
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.commonId
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.client.resource.language.I18n
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.SimpleRegistry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

@Suppress("unused")
class HTMaterial private constructor(
    private val info: Info,
    private val properties: HTMaterialProperties,
    private val flags: HTMaterialFlags
) {

    companion object {

        private val logger: Logger = LogManager.getLogger("HTMaterial")

        @JvmField
        val REGISTRY: SimpleRegistry<HTMaterial> = FabricRegistryBuilder.createSimple(
            HTMaterial::class.java,
            HTMaterialsCommon.id("material")
        ).attribute(RegistryAttribute.SYNCED).buildAndRegister()

        @JvmStatic
        @JvmOverloads
        internal fun createMaterial(
            name: String,
            preInit: HTMaterial.() -> Unit = {},
            init: HTMaterial.() -> Unit = {},
        ): HTMaterial = HTMaterial(Info(name), HTMaterialProperties(), HTMaterialFlags())
            .apply(preInit)
            .apply(init)
            .let { mat ->
                logger.info("The Material: $mat registered!")
                Registry.register(REGISTRY, commonId(name), mat)
            }

        @JvmStatic
        fun getMaterial(name: String): HTMaterial? = REGISTRY.get(commonId(name))

    }

    //    Properties    //

    fun <T : HTMaterialProperty<T>> getProperty(key: HTPropertyKey<T>): T? = properties.get(key)

    fun <T : HTMaterialProperty<T>> getPropertyOpt(key: HTPropertyKey<T>): Optional<T> =
        Optional.ofNullable(getProperty(key))

    fun <T : HTMaterialProperty<T>> hasProperty(key: HTPropertyKey<T>): Boolean = key in properties

    fun modifyProperties(init: HTMaterialProperties.() -> Unit) {
        check(HTMaterialsAPI.canModifyMaterial())
        properties.init()
        properties.verify(this)
    }

    //    Flags    //

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    fun modifyFlags(init: HTMaterialFlags.() -> Unit) {
        check(HTMaterialsAPI.canModifyMaterial())
        flags.init()
        flags.verify(this)
    }

    //    Info    //

    fun getInfo(): Info = info.copy()

    fun getName(): String = info.name

    fun getIdentifier(namespace: String): Identifier = Identifier(namespace, getName())

    fun getCommonId(): Identifier = commonId(getName())

    fun getIngotCountPerBlock(): Int = info.ingotPerBlock

    fun getColor(): Int = info.color

    fun getFormula(): String = info.formula

    fun getTranslatedName(): String = I18n.translate(info.translationKey)

    fun modifyInfo(init: Info.() -> Unit) {
        check(HTMaterialsAPI.canModifyMaterial())
        info.init()
    }

    data class Info(
        val name: String,
        var ingotPerBlock: Int = 9,
        var color: Int = -1,
        var formula: String = "",
        var translationKey: String = "ht_material.$name"
    )

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTMaterial -> false
        else -> other.getName() == this.getName()
    }

    override fun hashCode(): Int = getName().hashCode()

    override fun toString(): String = getName()

}