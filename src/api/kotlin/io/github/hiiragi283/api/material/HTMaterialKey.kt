package io.github.hiiragi283.api.material

import io.github.hiiragi283.api.HTMaterialsAPI
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

data class HTMaterialKey(val name: String) {
    fun getMaterial(): HTMaterial = checkNotNull(HTMaterialsAPI.INSTANCE.materialRegistry().getMaterial(this)) {
        "Material with $name is not registered!"
    }

    //    Identifier    //

    fun getIdentifier(namespace: String = HTMaterialsAPI.MOD_ID): Identifier = Identifier(namespace, name)

    fun getCommonId() = Identifier("c", name)

    fun getMaterialId() = getIdentifier("material")

    //    Tag    //

    fun getMaterialTag(): Tag.Identified<Item> = TagRegistry.item(getMaterialId()) as Tag.Identified<Item>

    //    Translation    //

    val translationKey: String = "ht_material.$name"

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(): String = I18n.translate(translationKey)

    fun getTranslatedText(): TranslatableText = TranslatableText(translationKey)

    //    Any    //

    override fun toString(): String = name
}
