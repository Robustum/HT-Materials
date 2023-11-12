package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.commonId
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.SimpleRegistry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.function.Predicate

@Suppress("unused")
class HTShape private constructor(
    val name: String,
    val prefix: String,
    val suffix: String,
    val generateItem: Predicate<HTMaterial>
) {

    private val translationKey: String = "ht_shape.$name"

    companion object {

        private val logger: Logger = LogManager.getLogger("HTShape")

        @JvmField
        val REGISTRY: SimpleRegistry<HTShape> = FabricRegistryBuilder.createSimple(
            HTShape::class.java,
            HTMaterialsCommon.id("shape")
        ).attribute(RegistryAttribute.SYNCED).buildAndRegister()

        @JvmStatic
        fun getShape(name: String): HTShape? = REGISTRY.get(commonId(name))

        //    Shapes    //

        @JvmStatic
        fun create(name: String, init: Builder.() -> Unit = {}): HTShape = Builder(name).apply(init).build()
            .also {
                Registry.register(REGISTRY, commonId(name), it)
                logger.info("The Shape: $name registered!")
            }

        @JvmStatic
        fun createSimple(name: String) = create(name) { suffix = "_${name}s" }

        @JvmField
        val BLOCK = createSimple("block")

        @JvmField
        val DUST = createSimple("dust")

        @JvmField
        val GEAR = createSimple("gear")

        @JvmField
        val GEM = createSimple("gem")

        @JvmField
        val INGOT = createSimple("ingot")

        @JvmField
        val NUGGET = createSimple("nugget")

        @JvmField
        val PLATE = createSimple("plate")

        @JvmField
        val RAW_ORE = create("raw_ore") {
            prefix = "raw_"
            suffix = "_ores"
        }

        @JvmField
        val RAW_BLOCK = create("raw_block") {
            prefix = "raw_"
            suffix = "_blocks"
        }

    }

    fun canGenerateItem(material: HTMaterial): Boolean = generateItem.test(material)

    fun getIdentifier(namespace: String, material: HTMaterial) =
        Identifier(namespace, prefix + material.getName() + suffix)

    fun getCommonId(material: HTMaterial) = getIdentifier("c", material)

    fun getMaterial(tagKey: TagKey<Item>): HTMaterial? = getMaterial(tagKey.id.path)

    private fun getMaterial(path: String) = if (path.startsWith(prefix) && path.endsWith(suffix)) {
        HTMaterial.getMaterial(path.removePrefix(prefix).removeSuffix(suffix))
    } else null

    fun getTranslatedName(material: HTMaterial): String = I18n.translate(translationKey, material.getTranslatedName())

    fun getTranslatedText(material: HTMaterial): Text = TranslatableText(translationKey, material.getTranslatedName())

    fun getTagKey(material: HTMaterial): TagKey<Item> = TagKey.of(Registry.ITEM_KEY, getCommonId(material))

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTShape -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

    //    Builder    //

    class Builder(val name: String) {

        var prefix: String = ""
        var suffix: String = ""
        var generateItem: Predicate<HTMaterial> = Predicate { it.hasProperty(HTPropertyKey.SOLID) }

        internal fun build() = HTShape(name, prefix, suffix, generateItem).also {
            check(prefix.isNotEmpty() || suffix.isNotEmpty()) {
                "The shape: $name must have either prefix or suffix!"
            }
        }

    }

}