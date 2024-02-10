package io.github.hiiragi283.api.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
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

    fun getCommonId() = HTPlatformHelper.INSTANCE.getLoaderType().id(name)

    fun getMaterialId() = getIdentifier("material")

    //    Tag    //

    fun getMaterialTag(): Tag.Identified<Item> = HTPlatformHelper.INSTANCE.getItemTag(getMaterialId())

    //    Translation    //

    private val translationKey: String = "ht_material.$name"

    @Environment(EnvType.CLIENT)
    fun getTranslatedName(): String = I18n.translate(translationKey)

    fun getTranslatedText(): TranslatableText = TranslatableText(translationKey)

    //    Any    //

    override fun toString(): String = name
}
