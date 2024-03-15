package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

class HTShape(val name: String) {
    //    Identifier    //

    private val idPath: String = "%s_$name"

    @JvmOverloads
    fun getId(materialKey: HTMaterialKey, namespace: String = HTMaterialsAPI.MOD_ID) = getId(materialKey.name, namespace)

    @JvmOverloads
    fun getId(name: String, namespace: String = HTMaterialsAPI.MOD_ID) = Identifier(namespace, idPath.replace("%s", name))

    //    Tag    //

    private val tagPath: String = "${idPath}s"

    @JvmOverloads
    fun getTagId(materialKey: HTMaterialKey, namespace: String = "c") = getTagId(materialKey.name, namespace)

    @JvmOverloads
    fun getTagId(name: String, namespace: String = "c") = Identifier(namespace, tagPath.replace("%s", name))

    @JvmOverloads
    fun getTag(materialKey: HTMaterialKey, namespace: String = "c"): Tag<Item> = getTag(materialKey.name, namespace)

    @JvmOverloads
    fun getTag(name: String, namespace: String = "c"): Tag<Item> = TagRegistry.item(getTagId(name, namespace))

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
