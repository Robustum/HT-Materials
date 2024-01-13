package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.registry.HTObjectKey
import io.github.hiiragi283.material.util.commonId
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.resource.language.I18n
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

data class HTMaterialKey(override val name: String) : HTObjectKey<HTMaterial> {
    override val objClass: Class<HTMaterial> = HTMaterial::class.java

    fun getMaterial(): HTMaterial = HTMaterial.getMaterial(this)

    fun getMaterialOrNull(): HTMaterial? = HTMaterial.getMaterialOrNull(this)

    //    Identifier    //

    fun getIdentifier(namespace: String = HTMaterials.MOD_ID): Identifier = Identifier(namespace, name)

    fun getCommonId() = commonId(name)

    //    Translation    //

    private val translationKey: String = "ht_material.$name"

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(): String = I18n.translate(translationKey)

    fun getTranslatedText(): TranslatableText = TranslatableText(translationKey)

    //    Any    //

    override fun toString(): String = name
}
