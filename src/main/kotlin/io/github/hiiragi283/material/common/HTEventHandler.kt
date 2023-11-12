package io.github.hiiragi283.material.common

import com.dm.earth.tags_binder.api.LoadTagsCallback
import io.github.hiiragi283.material.api.item.MaterialItemConvertible
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.TagKey
import net.minecraft.text.Text

object HTEventHandler {

    fun registerCommon() {

        LoadTagsCallback.ITEM.register { handler: LoadTagsCallback.TagHandler<Item> ->
            HTMaterial.REGISTRY.forEach { material: HTMaterial ->
                HTShape.REGISTRY.forEach { shape: HTShape ->
                    val tagKey: TagKey<Item> = shape.getTagKey(material)
                    handler.register(tagKey)
                    handler.get(shape.getTagKey(material)).forEach {
                        MaterialItemConvertible.register(material, shape, it)
                    }
                }
            }
        }

    }

    fun registerClient() {

        ItemTooltipCallback.EVENT.register { stack: ItemStack, context: TooltipContext, lines: MutableList<Text> ->
            MaterialItemConvertible.getPart(stack.item)?.let { }
        }

    }

}