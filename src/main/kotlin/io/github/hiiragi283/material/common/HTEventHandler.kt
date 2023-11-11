package io.github.hiiragi283.material.common

import com.dm.earth.tags_binder.api.LoadTagsCallback
import io.github.hiiragi283.material.api.item.MaterialItemManager
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.minecraft.item.Item
import net.minecraft.tag.TagKey

object HTEventHandler {

    fun register() {

        LoadTagsCallback.ITEM.register { handler: LoadTagsCallback.TagHandler<Item> ->
            HTMaterial.REGISTRY.forEach { material: HTMaterial ->
                HTShape.REGISTRY.values.forEach { shape: HTShape ->
                    val tagKey: TagKey<Item> = shape.getTagKey(material)
                    handler.register(tagKey)
                    handler.get(shape.getTagKey(material)).forEach {
                        MaterialItemManager.register(material, shape, it)
                    }
                }
            }
        }

    }

}