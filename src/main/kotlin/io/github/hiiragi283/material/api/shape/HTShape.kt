package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
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
    val forgeTag: String,
    val fabricTag: String,
    private val generateItem: Predicate<HTMaterial>
) {

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

        @JvmField
        val BLOCK = create("block") {
            forgeTag = "storage_block/%s"
            fabricTag = "%s_blocks"
            generateItem = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_BLOCk) }
        }

        @JvmField
        val DUST = create("dust") {
            forgeTag = "dusts/%s"
            fabricTag = "%s_dusts"
            generateItem = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_DUST) }
        }

        @JvmField
        val GEAR = create("gear"){
            forgeTag = "gears/%s"
            fabricTag = "%s_gears"
            generateItem = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_GEAR) }
        }

        @JvmField
        val GEM = create("gem"){
            forgeTag = "gems/%s"
            fabricTag = "%s_gems"
            generateItem = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_GEM) }
        }

        @JvmField
        val INGOT = create("ingot"){
            forgeTag = "ingots/%s"
            fabricTag = "%s_ingots"
            generateItem = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_INGOT) }
        }

        @JvmField
        val NUGGET = create("nugget"){
            forgeTag = "nuggets/%s"
            fabricTag = "%s_nuggets"
            generateItem = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_NUGGET) }
        }

        @JvmField
        val PLATE = create("plate"){
            forgeTag = "plates/%s"
            fabricTag = "%s_plates"
            generateItem = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_PLATE) }
        }

        @JvmField
        val RAW_ORE = create("raw_ore") {
            forgeTag = "raw_materials/%s"
            fabricTag = "raw_%s_ores"
            generateItem = Predicate { false }
        }

        @JvmField
        val RAW_BLOCK = create("raw_block") {
            forgeTag = "storage_blocks/raw_%s"
            fabricTag = "raw_%s_blocks"
            generateItem = Predicate { false }
        }

    }

    fun canGenerateItem(material: HTMaterial): Boolean = generateItem.test(material)

    //    Translation    //

    private val translationKey: String = "ht_shape.$name"

    fun getTranslatedName(material: HTMaterial): String = I18n.translate(translationKey, material.getTranslatedName())

    fun getTranslatedText(material: HTMaterial): Text = TranslatableText(translationKey, material.getTranslatedName())

    //    Identifier    //

    fun getIdentifier(namespace: String, material: HTMaterial) = Identifier(namespace, fabricTag.format(material.getName()))

    fun getForgeId(material: HTMaterial) = Identifier("c", forgeTag.format(material.getName()))

    fun getCommonId(material: HTMaterial) = Identifier("c", fabricTag.format(material.getName()))

    //    TagKey    //

    fun getForgeTag(material: HTMaterial): TagKey<Item> = TagKey.of(Registry.ITEM_KEY, getForgeId(material))

    fun getCommonTag(material: HTMaterial): TagKey<Item> = TagKey.of(Registry.ITEM_KEY, getCommonId(material))

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

        var forgeTag: String = ""
        var fabricTag: String = ""
        var generateItem: Predicate<HTMaterial> = Predicate { it.hasProperty(HTPropertyKey.SOLID) }

        internal fun build() = HTShape(name, forgeTag, fabricTag, generateItem).also {
            check(forgeTag.isNotEmpty() && fabricTag.isNotEmpty()) {
                "The shape: $name must have both forgeTag and fabricTag!"
            }
        }

    }

}