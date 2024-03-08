package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.formatter.HTTagFormatRule
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.resource.language.I18n
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

class HTShape(val name: String) {
    //    Formatter    //

    fun getFormatterOrNull(): HTTagFormatRule? = HTTagFormatRule.registry[name]

    val formatter: HTTagFormatRule
        get() = checkNotNull(getFormatterOrNull()) { "Formatter with $name is not registered!" }

    //    Identifier    //

    private val defaultIdPath: String = "%s_$name"

    fun getId(materialKey: HTMaterialKey, namespace: String = HTMaterialsAPI.MOD_ID) = getId(materialKey.name, namespace)

    fun getId(name: String, namespace: String = HTMaterialsAPI.MOD_ID) =
        Identifier(namespace, getFormatterOrNull()?.combine(name) ?: defaultIdPath.replace("%s", name))

    fun getShapeId() = Identifier("shape", name)

    //    Translation    //

    private val translationKey: String = "ht_shape.$name"

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(materialKey: HTMaterialKey): String = I18n.translate(translationKey, materialKey.translatedName)

    fun getTranslatedText(materialKey: HTMaterialKey): TranslatableText = TranslatableText(translationKey, materialKey.translatedName)

    //    Any    //

    override fun equals(other: Any?): Boolean = (other as? HTShape)?.name == this.name

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name
}
