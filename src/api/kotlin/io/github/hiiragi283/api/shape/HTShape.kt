package io.github.hiiragi283.api.shape

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import net.fabricmc.fabric.api.tag.TagRegistry
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

    fun getCommonId(material: HTMaterialKey): Identifier = getCommonId(material.name)

    fun getCommonId(name: String) = Identifier("c", tagPath.replace("%s", name))

    //    Tag    //

    fun getShapeTag(): Tag<Item> = TagRegistry.item(key.getShapeId())
}
