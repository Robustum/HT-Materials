package io.github.hiiragi283.material

import io.github.hiiragi283.material.client.HTCustomModelIdItem
import io.github.hiiragi283.material.util.modify
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object HTModelLoaderMixin {

    private const val BLOCK_PREFIX = "block/"

    private const val ITEM_PREFIX = "item/"

    @JvmStatic
    fun modifyBlockStateId(id: Identifier): Identifier = if (id.namespace == HTMaterialsCommon.MOD_ID) {
        id
    } else id

    @JvmStatic
    fun modifyModelId(id: Identifier): Identifier {
        if (id.namespace == HTMaterialsCommon.MOD_ID) {
            val path: String = id.path.removePrefix("models/").removeSuffix(".json")
            val fixedId: Identifier = id.modify { path }
            return when {
                path.startsWith(BLOCK_PREFIX) -> modifyBlockModelId(fixedId.modify { it.removePrefix(BLOCK_PREFIX) })
                path.startsWith(ITEM_PREFIX) -> modifyItemModelId(fixedId.modify { it.removePrefix(ITEM_PREFIX) })
                else -> null
            } ?: id
        } else return id
    }

    private fun modifyBlockModelId(id: Identifier): Identifier = id

    private fun modifyItemModelId(id: Identifier): Identifier? =
        (Registry.ITEM.get(id) as? HTCustomModelIdItem)?.getModelId()

}