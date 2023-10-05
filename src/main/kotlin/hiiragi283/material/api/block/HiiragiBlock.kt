package hiiragi283.material.api.block

import hiiragi283.material.api.item.HiiragiBlockItem
import hiiragi283.material.api.reigstry.HiiragiRegistry
import hiiragi283.material.init.HiiragiRegistries
import hiiragi283.material.util.HiiragiNbtConstants
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

abstract class HiiragiBlock(settings: FabricBlockSettings) : Block(settings), HiiragiRegistry.Entry<Block> {

    abstract val blockItem: HiiragiBlockItem?

    //    Entry    //

    override fun asItem(): Item = blockItem ?: super.asItem()

    override fun onRegister(name: String) {
        blockItem?.let { HiiragiRegistries.ITEM.register(name, it) }
    }

    override fun toNbt(): NbtCompound {
        val nbt = NbtCompound()
        nbt.putString(HiiragiNbtConstants.BLOCK, Registry.BLOCK.getId(this).toString())
        return nbt
    }

    override fun toPacket(buf: PacketByteBuf) {
        buf.writeVarInt(Registry.BLOCK.getRawId(this))
    }

}