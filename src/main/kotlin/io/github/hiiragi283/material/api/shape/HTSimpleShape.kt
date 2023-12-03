package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag

@Suppress("LeakingThis")
sealed class HTSimpleShape(override val name: String) : HTShape {

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

    class Block(name: String) : HTSimpleShape(name) {

        override fun canGenerateBlock(material: HTMaterial): Boolean =
            HTMaterialFlag.REGISTRY["generate_$name"]?.let(material::hasFlag) ?: false

        override fun canGenerateItem(material: HTMaterial): Boolean = false

    }

    class Item(name: String) : HTSimpleShape(name) {

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean =
            HTMaterialFlag.REGISTRY["generate_$name"]?.let(material::hasFlag) ?: false

    }

}