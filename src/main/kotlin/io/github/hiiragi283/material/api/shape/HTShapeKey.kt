package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.util.commonId
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

data class HTShapeKey
    @JvmOverloads
    constructor(val name: String, val idPath: String = "%s_$name", val tagPath: String = "${idPath}s") {
        fun getShape(): HTShape = HTShape.getShape(this)

        fun getShapeOrNull(): HTShape? = HTShape.getShapeOrNull(this)

        //    Identifier    //

        fun getIdentifier(material: HTMaterialKey, namespace: String = HTMaterials.MOD_ID): Identifier =
            Identifier(namespace, idPath.replace("%s", material.name))

        fun getShapeId(): Identifier = Identifier("shape", name)

        internal fun getCommonId(material: HTMaterialKey): Identifier = commonId(tagPath.replace("%s", material.name))

        //    Tag    //

        fun getShapeTag(): Tag.Identified<Item> = TagRegistry.item(getShapeId()) as Tag.Identified<Item>

        //    Translation    //

        private val translationKey: String = "ht_shape.$name"

        @Environment(EnvType.CLIENT)
        fun getTranslatedName(materialKey: HTMaterialKey): String = I18n.translate(translationKey, materialKey.getTranslatedName())

        fun getTranslatedText(materialKey: HTMaterialKey): TranslatableText = TranslatableText(
            translationKey,
            materialKey.getTranslatedName(),
        )

        //    Any    //

        override fun equals(other: Any?): Boolean = when (other) {
            null -> false
            !is HTShapeKey -> false
            else -> other.name == this.name
        }

        override fun hashCode(): Int = name.hashCode()

        override fun toString(): String = name

        companion object {
            private fun getForgePath(name: String): String {
                val split: List<String> = name.split("_")
                return when (split.size) {
                    1 -> "${name}s/%s"
                    2 -> "${split[0]}s/${split[1]}/%s"
                    else -> throw IllegalStateException("Cannot decompose name: $name into Forge Tag format!")
                }
            }
        }
    }
