package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.HTMaterialsCommon
import io.github.hiiragi283.material.api.client.HTColoredMaterialItem
import io.github.hiiragi283.material.api.client.HTCustomModelIdItem
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.property.HTPropertyKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class HTSimpleItemContent(override val shapeKey: HTShapeKey) : HTMaterialContent.ITEM() {

    override fun create(materialKey: HTMaterialKey): Item? =
        ItemImpl(materialKey, shapeKey).takeUnless { HTPartManager.hasDefaultItem(materialKey, shapeKey) }

    private class ItemImpl(
        private val materialKey: HTMaterialKey,
        private val shapeKey: HTShapeKey
    ) : Item(FabricItemSettings().group(HTMaterialsCommon.ITEM_GROUP)),
        HTColoredMaterialItem,
        HTCustomModelIdItem {

        override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)

        override fun getColorProvider(): ItemColorProvider = ItemColorProvider { _, tintIndex: Int ->
            if (tintIndex == 0) materialKey.getMaterial().color.rgb else -1
        }

        override fun getModelId(): Identifier {
            val modelName: String = materialKey.getMaterial()
                .getProperty(HTPropertyKey.GEM)
                ?.let { "${it.name.lowercase()}_gem" }
                ?.takeIf { shapeKey == HTShapes.GEM }
                ?: shapeKey.toString()
            return HTMaterialsCommon.id("models/item/$modelName.json")
        }

    }

}