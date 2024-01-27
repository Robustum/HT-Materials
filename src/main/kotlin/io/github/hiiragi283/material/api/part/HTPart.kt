package io.github.hiiragi283.material.api.part

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

data class HTPart(
    val materialKey: HTMaterialKey,
    val shapeKey: HTShapeKey,
) : ItemConvertible {
    fun getMaterial(): HTMaterial = materialKey.getMaterial()

    fun getMaterialOrNull(): HTMaterial? = materialKey.getMaterialOrNull()

    fun getShape(): HTShape = shapeKey.getShape()

    fun getShapeOrNull(): HTShape? = shapeKey.getShapeOrNull()

    fun getPartId(): Identifier = Identifier("part", "$materialKey/$shapeKey")

    fun getPartTag(): Tag.Identified<Item> = TagRegistry.item(getPartId()) as Tag.Identified<Item>

    override fun asItem(): Item = HTPartManager.getDefaultItem(materialKey, shapeKey) ?: Items.AIR

    companion object {
        @JvmStatic
        fun fromTag(tag: Tag<*>): HTPart? = (tag as? Tag.Identified<*>)?.id?.let(::fromId)

        @JvmStatic
        fun fromId(id: Identifier): HTPart? = if (id.namespace == "minecraft" || id.namespace == "part") null else from(id.path)

        private fun from(path: String): HTPart? {
            for (shape: HTShape in HTShape.getShapes()) {
                for (materialKey: HTMaterialKey in HTMaterial.getMaterialKeys()) {
                    if (path.contains(materialKey.name)) {
                        if (shape.isMatchForgeFormat(path) || shape.isMatchFabricFormat(path)) {
                            return HTPart(materialKey, shape.key)
                        }
                    }
                }
            }
            return null
        }
    }
}
