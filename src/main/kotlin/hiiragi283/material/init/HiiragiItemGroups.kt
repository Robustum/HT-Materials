package hiiragi283.material.init

import hiiragi283.material.util.hiiragiId
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.Items

object HiiragiItemGroups {

    val COMMON: ItemGroup = FabricItemGroupBuilder.build(
        hiiragiId("common")
    ) { Items.WRITABLE_BOOK.defaultStack }

    val MATERIAL: ItemGroup = FabricItemGroupBuilder.build(
        hiiragiId("material")
    ) { Items.IRON_INGOT.defaultStack }

}