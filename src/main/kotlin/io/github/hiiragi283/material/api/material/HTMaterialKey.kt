package io.github.hiiragi283.material.api.material

import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.util.commonId
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

data class HTMaterialKey(val name: String) {
    fun getMaterial(): HTMaterial = HTMaterial.getMaterial(this)

    fun getMaterialOrNull(): HTMaterial? = HTMaterial.getMaterialOrNull(this)

    //    Identifier    //

    fun getIdentifier(namespace: String = HTMaterials.MOD_ID): Identifier = Identifier(namespace, name)

    fun getCommonId() = commonId(name)

    fun getMaterialId() = getIdentifier("material")

    //    Tag    //

    fun getMaterialTag(): Tag.Identified<Item> = TagRegistry.item(getMaterialId()) as Tag.Identified<Item>

    //    Translation    //

    private val translationKey: String = "ht_material.$name"

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(): String = I18n.translate(translationKey)

    fun getTranslatedText(): TranslatableText = TranslatableText(translationKey)

    //    Any    //

    override fun toString(): String = name
}
