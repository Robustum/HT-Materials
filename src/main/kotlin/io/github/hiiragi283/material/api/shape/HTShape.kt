package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.common.commonId
import io.github.hiiragi283.material.common.modify
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.function.Function

@Suppress("LeakingThis")
sealed class HTShape(val name: String) {

    companion object {

        private val logger: Logger = LogManager.getLogger("HTShape")

        private val map: MutableMap<String, HTShape> = mutableMapOf()

        @JvmField
        val REGISTRY: Map<String, HTShape> = map

        //    Shapes    //

        @JvmField
        val BLOCK: HTShape = Simple("block")

        @JvmField
        val DUST: HTShape = Simple("dust")

        @JvmField
        val GEAR: HTShape = Simple("gear")

        @JvmField
        val GEM: HTShape = Simple("gem")

        @JvmField
        val INGOT: HTShape = Simple("ingot")

        @JvmField
        val NUGGET: HTShape = Simple("nugget")

        @JvmField
        val PLATE: HTShape = Simple("plate")

        @JvmField
        val RAW_ORE: HTShape = Custom("raw_ore") { id ->
            val path: String = id.path
            return@Custom if (path.startsWith("raw_") && path.endsWith("_ores")) {
                HTMaterial.REGISTRY.get(id.modify {
                    path.removePrefix("raw_").removeSuffix("_ores")
                })
            } else null
        }

        @JvmField
        val RAW_BLOCK: HTShape = Custom("raw_block") { id ->
            val path: String = id.path
            return@Custom if (path.startsWith("raw_") && path.endsWith("_blocks")) {
                HTMaterial.REGISTRY.get(id.modify {
                    path.removePrefix("raw_").removeSuffix("_blocks")
                })
            } else null
        }

        @JvmField
        val ROD: HTShape = Simple("rod")

    }

    init {
        logger.info("The Shape: $name registered!")
        map.putIfAbsent(name, this)
    }

    val translationKey: String = "shape.c.$name"

    fun getTranslatedName(material: HTMaterial): String = I18n.translate(translationKey, material.getTranslatedName())

    fun getTranslatedText(material: HTMaterial): Text = TranslatableText(translationKey, material.getTranslatedName())

    abstract fun getMaterial(tagKey: TagKey<Item>): HTMaterial?

    fun getTagKey(material: HTMaterial): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId("${material.getName()}_${name}s"))

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTShape -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

    //    Simple    //

    class Simple(name: String) : HTShape(name) {

        override fun getMaterial(tagKey: TagKey<Item>): HTMaterial? {
            val tagId: Identifier = tagKey.id
            val path: String = tagId.path
            val pattern = "_${name}s"
            return if (path.endsWith(pattern)) {
                HTMaterial.REGISTRY.get(tagId.modify { path.removeSuffix(pattern) })
            } else null
        }

    }

    //    Custom    //

    class Custom(name: String, private val function: Function<Identifier, HTMaterial?>) : HTShape(name) {

        override fun getMaterial(tagKey: TagKey<Item>): HTMaterial? = function.apply(tagKey.id)

    }

}