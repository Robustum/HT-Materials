package io.github.hiiragi283.api.material.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import io.github.hiiragi283.api.util.resource.HTRuntimeResourcePack
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.item.Item as MCItem

class HTSimpleItemContent(shapeKey: HTShapeKey) : HTMaterialContent.Item(shapeKey) {
    override fun itemId(materialKey: HTMaterialKey): String = shapeKey.getShape().getIdentifier(materialKey).path

    override fun item(materialKey: HTMaterialKey): MCItem = ItemImpl(materialKey, shapeKey)

    private fun getTextureName(type: HTMaterialType): String = if (type is HTMaterialType.Gem) {
        if (shapeKey == HTShapeKeys.GEM) "${type.name.lowercase()}_gem" else shapeKey.toString()
    } else {
        shapeKey.toString()
    }

    override fun postInit(materialKey: HTMaterialKey) {
        // Client-only
        HTPlatformHelper.INSTANCE.onSide(HTPlatformHelper.Side.CLIENT) {
            // ItemColor
            HTPlatformHelper.INSTANCE.registerItemColor(
                { _, tintIndex: Int -> if (tintIndex == 0) materialKey.getMaterial().color().rgb else -1 },
                item.get(),
            )
            // Model
            HTRuntimeResourcePack.addModel(
                item.get(),
                buildJson {
                    addProperty("parent", "item/generated")
                    addObject("textures") {
                        addProperty(
                            "layer0",
                            HTMaterialsAPI.id("item/${getTextureName(materialKey.getMaterial().type)}").toString(),
                        )
                    }
                },
            )
        }
    }

    private class ItemImpl(
        private val materialKey: HTMaterialKey,
        private val shapeKey: HTShapeKey,
    ) : MCItem(Settings().group(HTMaterialsAPI.INSTANCE.itemGroup())) {
        override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)
    }
}
