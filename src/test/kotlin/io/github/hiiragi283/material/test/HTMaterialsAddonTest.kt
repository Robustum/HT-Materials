package io.github.hiiragi283.material.test

import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapes
import net.minecraft.item.Items

object HTMaterialsAddonTest : HTMaterialsAddon {
    override val modId: String = "ht_materials_test"
    override val priority: Int = -90

    private val testMaterialKey = HTMaterialKey("test")
    
    override fun modifyPartManager(builder: HTPartManager.Builder) {
        builder.add(testMaterialKey, HTShapes.INGOT, Items.GLASS_BOTTLE)
    }
}