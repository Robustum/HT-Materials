package io.github.hiiragi283.material.impl.material.content

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTPlatformHelper
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.content.HTMaterialContent
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import io.github.hiiragi283.api.util.resource.HTRuntimeResourcePack
import io.github.hiiragi283.material.HTMaterials
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class HTSimpleItemContent(shapeKey: HTShapeKey) : HTMaterialContent.ITEM(shapeKey) {
    override fun item(materialKey: HTMaterialKey): Item =
        ItemImpl(materialKey, shapeKey) // .takeUnless { HTMaterialsAPI.getInstance().partManager().hasItem(materialKey, shapeKey) }

    private fun getTextureName(type: HTMaterialType): String = if (type is HTMaterialType.Gem) {
        if (shapeKey == HTShapeKeys.GEM) "${type.name.lowercase()}_gem" else shapeKey.toString()
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
                        HTMaterialsAPI.id("item/${getTextureName(materialKey.getMaterial().type)}").toString(),
                    )
                }
            },
        )
    }

    private class ItemImpl(
        private val materialKey: HTMaterialKey,
        private val shapeKey: HTShapeKey,
    ) : Item(FabricItemSettings().group(HTMaterials.itemGroup())) {
        init {
            HTPlatformHelper.INSTANCE.onSide(HTPlatformHelper.Side.CLIENT) {
                HTPlatformHelper.INSTANCE.registerItemColor(
                    { _, tintIndex: Int -> if (tintIndex == 0) materialKey.getMaterial().color().rgb else -1 },
                    this,
                )
            }
        }

        override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)
    }
}
