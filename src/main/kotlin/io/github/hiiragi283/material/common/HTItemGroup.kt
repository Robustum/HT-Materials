package io.github.hiiragi283.material.common

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.Items

object HTItemGroup {

    @JvmField
    val MATERIAL: ItemGroup = FabricItemGroupBuilder.create(HTMaterialsCommon.id("material"))
        .icon { Items.IRON_INGOT.defaultStack }
        .build()

}