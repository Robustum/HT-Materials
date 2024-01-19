package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.HTMaterialType
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.resource.HTRuntimeResourcePack
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.api.util.HTCustomColoredItem
import io.github.hiiragi283.material.util.addObject
import io.github.hiiragi283.material.util.buildJson
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class HTSimpleItemContent(shapeKey: HTShapeKey) : HTMaterialContent.ITEM(shapeKey) {
    override fun createItem(materialKey: HTMaterialKey): Item? =
        ItemImpl(materialKey, shapeKey).takeUnless { HTPartManager.hasDefaultItem(materialKey, shapeKey) }

    private fun getTextureName(type: HTMaterialType): String = if (type is HTMaterialType.Gem) {
        if (shapeKey == HTShapes.GEM) "${type.name.lowercase()}_gem" else shapeKey.toString()
    } else {
        shapeKey.toString()
    }

    override fun onCreate(materialKey: HTMaterialKey, created: Item) {
        super.onCreate(materialKey, created)
        // Model
        HTRuntimeResourcePack.addModel(
            created,
            buildJson {
                addProperty("parent", "item/generated")
                addObject("textures") {
                    addProperty(
                        "layer0",
                        HTMaterials.id("item/${getTextureName(materialKey.getMaterial().type)}").toString(),
                    )
                }
            },
        )
    }

    private class ItemImpl(
        private val materialKey: HTMaterialKey,
        private val shapeKey: HTShapeKey,
    ) : Item(FabricItemSettings().group(HTMaterials.ITEM_GROUP)),
        HTCustomColoredItem {
        override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)

        @Environment(EnvType.CLIENT)
        override fun getColorProvider(): ItemColorProvider = ItemColorProvider { _, tintIndex: Int ->
            if (tintIndex == 0) materialKey.getMaterial().color.rgb else -1
        }
    }
}
