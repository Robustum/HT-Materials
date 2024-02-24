package io.github.hiiragi283.api.material

import io.github.hiiragi283.api.HTMaterialsAPI
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.resource.language.I18n
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

class HTMaterialKey(val name: String) {

    private var cache: HTMaterial? = null

    fun getMaterialOrNull(): HTMaterial? {
        if (cache == null) {
            cache = HTMaterialsAPI.INSTANCE.materialRegistry().getMaterial(this)
        }
        return cache
    }

    @Throws(IllegalStateException::class)
    fun getMaterial(): HTMaterial = checkNotNull(getMaterialOrNull()) { "material:$name is not registered!" }

    //    Identifier    //

    fun getIdentifier(namespace: String = HTMaterialsAPI.MOD_ID): Identifier = Identifier(namespace, name)

    fun getCommonId() = Identifier("c", name)

    fun getMaterialId() = getIdentifier("material")

    //    Translation    //

    val translationKey: String = "ht_material.$name"

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(): String = I18n.translate(translationKey)

    fun getTranslatedText(): TranslatableText = TranslatableText(translationKey)

    //    Any    //

    override fun equals(other: Any?): Boolean = (other as? HTMaterialKey)?.name == this.name

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name
}