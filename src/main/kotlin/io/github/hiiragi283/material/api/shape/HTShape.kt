package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.commonId
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

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

    fun getForgeTag(material: HTMaterial): Tag.Identified<Item> =
        TagRegistry.item(commonId(getForgePath(material))) as Tag.Identified<Item>

    fun getForgePath(material: HTMaterial): String

    fun getCommonTag(material: HTMaterial): Tag.Identified<Item> =
        TagRegistry.item(commonId(getCommonPath(material))) as Tag.Identified<Item>

    fun getCommonPath(material: HTMaterial): String

}