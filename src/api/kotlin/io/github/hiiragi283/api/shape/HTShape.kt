package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.util.commonId
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

@JvmDefaultWithCompatibility
interface HTShape {
    val key: HTShapeKey
    val idPath: String
    val tagPath: String

    //    Identifier    //

    fun getIdentifier(materialKey: HTMaterialKey, namespace: String = HTMaterialsAPI.MOD_ID) =
        Identifier(namespace, idPath.replace("%s", materialKey.name))

    fun getCommonId(material: HTMaterialKey): Identifier = commonId(tagPath.replace("%s", material.name))

    //    Tag    //

    fun getShapeTag(): Tag.Identified<Item> = TagRegistry.item(key.getShapeId()) as Tag.Identified<Item>

    //    Translation    //

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(materialKey: HTMaterialKey): String = I18n.translate(key.translationKey, materialKey.getTranslatedName())

    fun getTranslatedText(materialKey: HTMaterialKey): TranslatableText =
        TranslatableText(key.translationKey, materialKey.getTranslatedName())
}
