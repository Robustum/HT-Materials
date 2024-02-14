package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.resource.language.I18n
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

data class HTShapeKey(val name: String) {
    private val translationKey: String = "ht_shape.$name"

    fun getShapeOrNull(): HTShape? = HTMaterialsAPI.INSTANCE.shapeRegistry().getShape(this)

    @Throws(IllegalStateException::class)
    fun getShape(): HTShape = checkNotNull(getShapeOrNull()) { "Shape with $name is not registered!" }

    fun getShapeId() = Identifier("shape", name)

    //    Translation    //

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(materialKey: HTMaterialKey): String = I18n.translate(translationKey, materialKey.getTranslatedName())

    fun getTranslatedText(materialKey: HTMaterialKey): TranslatableText = TranslatableText(translationKey, materialKey.getTranslatedName())

    //    Any    //

    override fun toString(): String = name

    companion object {
        @JvmStatic
        val EMPTY = HTShapeKey("")
    }
}
