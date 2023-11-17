package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlags
import io.github.hiiragi283.material.api.material.property.HTMaterialProperties
import io.github.hiiragi283.material.api.material.property.HTMaterialProperty
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.HTLoadState
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.commonId
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.client.resource.language.I18n
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.SimpleRegistry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

@Suppress("unused", "UnstableApiUsage")
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
        internal fun createMaterial(
            name: String,
            preInit: HTMaterial.() -> Unit = {},
            init: HTMaterial.() -> Unit = {},
        ): HTMaterial = HTMaterial(Info(name), HTMaterialProperties(), HTMaterialFlags())
            .apply(preInit)
            .apply(init)
            .also { mat ->
                Registry.register(REGISTRY, commonId(name), mat)
                logger.info("The Material: $mat registered!")
            }

        @JvmStatic
        fun getMaterial(name: String): HTMaterial? = REGISTRY.get(commonId(name))

    }

    fun verify() {
        properties.verify(this)
        flags.verify(this)
    }

    //    Properties    //

    fun <T : HTMaterialProperty<T>> getProperty(key: HTPropertyKey<T>): T? = properties.get(key)

    fun <T : HTMaterialProperty<T>> getPropertyOpt(key: HTPropertyKey<T>): Optional<T> =
        Optional.ofNullable(getProperty(key))

    fun <T : HTMaterialProperty<T>> hasProperty(key: HTPropertyKey<T>): Boolean = key in properties

    fun modifyProperties(init: HTMaterialProperties.() -> Unit) {
        check(HTMaterialsCommon.getLoadState() <= HTLoadState.PRE_INIT)
        properties.init()
    }

    fun getDefaultShape(): HTShape? = when {
        hasProperty(HTPropertyKey.METAL) -> HTShape.INGOT
        hasProperty(HTPropertyKey.GEM) -> HTShape.GEM
        hasProperty(HTPropertyKey.SOLID) -> HTShape.DUST
        else -> null
    }

    //    Flags    //

    fun hasFlag(flag: HTMaterialFlag): Boolean = flag in flags

    fun modifyFlags(init: HTMaterialFlags.() -> Unit) {
        check(HTMaterialsCommon.getLoadState() <= HTLoadState.PRE_INIT)
        flags.init()
    }

    //    Info    //

    fun getInfo(): Info = info.copy()

    fun getName(): String = info.name

    fun getIdentifier(namespace: String): Identifier = Identifier(namespace, getName())

    fun getCommonId(): Identifier = commonId(getName())

    fun getColor(): Int = info.color

    fun getFormula(): String = info.formula

    fun getIngotCountPerBlock(): Int = info.ingotPerBlock

    fun getFluidAmountPerIngot(): Long = FluidConstants.BLOCK / getIngotCountPerBlock()

    fun getTranslatedName(): String = I18n.translate(info.translationKey)

    fun getTranslatedText(): Text = TranslatableText(info.translationKey)

    fun modifyInfo(init: Info.() -> Unit) {
        check(HTMaterialsCommon.getLoadState() <= HTLoadState.PRE_INIT)
        info.init()
    }

    data class Info(
        val name: String,
        var color: Int = -1,
        var formula: String = "",
        var ingotPerBlock: Int = 9,
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