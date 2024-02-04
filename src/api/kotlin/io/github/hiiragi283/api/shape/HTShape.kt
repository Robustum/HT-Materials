package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.util.commonId
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

data class HTShape(
    val key: HTShapeKey,
    val idPath: String,
    val tagPath: String,
) {
    //    Identifier    //

    fun getIdentifier(materialKey: HTMaterialKey, namespace: String = HTMaterialsAPI.MOD_ID) =
        Identifier(namespace, idPath.replace("%s", materialKey.name))

    fun getCommonId(material: HTMaterialKey): Identifier = commonId(tagPath.replace("%s", material.name))

    //    Tag    //

    fun getShapeTag(): Tag.Identified<Item> = HTPlatformHelper.INSTANCE.getItemTag(key.getShapeId())
}
