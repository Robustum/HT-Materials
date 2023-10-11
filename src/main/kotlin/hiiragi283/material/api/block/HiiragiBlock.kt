package hiiragi283.material.api.block

import hiiragi283.material.api.item.HiiragiBlockItem
import hiiragi283.material.api.registry.HiiragiRegistry
import hiiragi283.material.init.HiiragiRegistries
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.item.Item

abstract class HiiragiBlock(settings: FabricBlockSettings) : Block(settings), HiiragiRegistry.Entry<Block> {

    abstract val blockItem: HiiragiBlockItem?

    //    Entry    //

    override fun asItem(): Item = blockItem ?: super.asItem()

    override fun onRegister(name: String) {
        blockItem?.let { HiiragiRegistries.ITEM.register(name, it) }
    }

}