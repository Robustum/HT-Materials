package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.commonId
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

@JvmDefaultWithCompatibility
interface HTShape {

    val name: String

    fun register(): HTShape = also(HTShapes::register)

    //    Predicate    //

    fun canGenerateBlock(material: HTMaterial): Boolean

    fun canGenerateItem(material: HTMaterial): Boolean

    //    Identifier    //

    fun getIdentifier(material: HTMaterial, namespace: String = HTMaterialsCommon.MOD_ID): Identifier =
        Identifier(namespace, getIdPath(material))

    fun getIdPath(material: HTMaterial): String

    //    TagKey    //

    fun getForgeTag(material: HTMaterial): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(getForgePath(material)))

    fun getForgePath(material: HTMaterial): String

    fun getCommonTag(material: HTMaterial): TagKey<Item> =
        TagKey.of(Registry.ITEM_KEY, commonId(getCommonPath(material)))

    fun getCommonPath(material: HTMaterial): String

}