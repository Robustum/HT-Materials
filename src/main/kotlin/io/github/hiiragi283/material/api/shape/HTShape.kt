package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
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

    companion object {
        @JvmStatic
        fun create(name: String): HTShape = Impl(name)
    }

    private class Impl(override val name: String) : HTShape {

        init {
            register()
        }

        override fun getIdPath(material: HTMaterial): String = "${material.getName()}_$name"

        override fun getForgePath(material: HTMaterial): String {
            val split: List<String> = name.split("_")
            return when (split.size) {
                1 -> "${name}s/${material.getName()}"
                2 -> "${split[0]}s/${split[1]}/${material.getName()}"
                else -> throw IllegalStateException("Cannot decompose name: $name into Forge Tag format!")
            }
        }

        override fun getCommonPath(material: HTMaterial): String = "${material.getName()}_${name}s"

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean =
            HTMaterialFlag.REGISTRY["generate_$name"]?.let(material::hasFlag) ?: false

    }

}