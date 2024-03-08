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
            cache = HTMaterialsAPI.INSTANCE.materialRegistry()[this]
        }
        return cache
    }

    val material: HTMaterial
        @Throws(IllegalStateException::class)
        get() = checkNotNull(getMaterialOrNull()) { "material:$name is not registered!" }

    //    Identifier    //

    fun getIdentifier(namespace: String = HTMaterialsAPI.MOD_ID): Identifier = Identifier(namespace, name)

    val commonId: Identifier
        get() = Identifier("c", name)

    fun getMaterialId() = getIdentifier("material")

    //    Translation    //

    val translationKey: String = "ht_material.$name"

    val translatedName: String
        @Environment(EnvType.CLIENT)
        get() = I18n.translate(translationKey)

    val translatedText: TranslatableText
        get() = TranslatableText(translationKey)

    //    Any    //

    override fun equals(other: Any?): Boolean = (other as? HTMaterialKey)?.name == this.name

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name
}
