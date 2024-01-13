package io.github.hiiragi283.material.api.material.content

import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.HTMaterialType
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.api.util.HTCustomColoredItem
import io.github.hiiragi283.material.api.util.HTCustomModelItem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.EnvironmentInterface
import net.fabricmc.api.EnvironmentInterfaces
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class HTSimpleItemContent(override val shapeKey: HTShapeKey) : HTMaterialContent.ITEM() {
    override fun create(materialKey: HTMaterialKey): Item? =
        ItemImpl(materialKey, shapeKey).takeUnless { HTPartManager.hasDefaultItem(materialKey, shapeKey) }

    @EnvironmentInterfaces(
        value = [
            EnvironmentInterface(value = EnvType.CLIENT, itf = HTCustomColoredItem::class),
            EnvironmentInterface(value = EnvType.CLIENT, itf = HTCustomModelItem::class),
        ],
    )
    private class ItemImpl(
        private val materialKey: HTMaterialKey,
        private val shapeKey: HTShapeKey,
    ) : Item(FabricItemSettings().group(HTMaterials.ITEM_GROUP)),
        HTCustomColoredItem,
        HTCustomModelItem {
        override fun getName(): Text = shapeKey.getTranslatedText(materialKey)

        override fun getName(stack: ItemStack): Text = shapeKey.getTranslatedText(materialKey)

        @Environment(EnvType.CLIENT)
        override fun getColorProvider(): ItemColorProvider = ItemColorProvider { _, tintIndex: Int ->
            if (tintIndex == 0) materialKey.getMaterial().color.rgb else -1
        }

        @Environment(EnvType.CLIENT)
        override fun getModelId(): Identifier {
            val type: HTMaterialType = materialKey.getMaterial().type
            val modelName: String = if (type is HTMaterialType.Gem) {
                if (shapeKey == HTShapes.GEM) {
                    "${type.name.lowercase()}_gem"
                } else {
                    shapeKey.toString()
                }
            } else {
                shapeKey.toString()
            }
            return HTMaterials.id("models/item/$modelName.json")
        }
    }
}
