package hiiragi283.material.api.block

import hiiragi283.material.api.item.HiiragiBlockItem
import hiiragi283.material.api.reigstry.HiiragiRegistries
import hiiragi283.material.api.reigstry.HiiragiRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

abstract class HiiragiBlock(settings: FabricBlockSettings) : Block(settings), HiiragiRegistry.Entry<Block> {

    abstract val blockItem: HiiragiBlockItem?

    //    Entry    //

    override fun asItem(): Item = blockItem ?: super.asItem()

    override fun onRegister(path: String, registry: Registry<Block>) {
        blockItem?.let { HiiragiRegistries.ITEM.register(path, it) }
    }

}