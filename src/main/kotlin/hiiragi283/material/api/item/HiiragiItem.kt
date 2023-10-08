package hiiragi283.material.api.item

import hiiragi283.material.api.registry.HiiragiRegistry
import hiiragi283.material.util.HiiragiNbtConstants
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

abstract class HiiragiItem(settings: FabricItemSettings) : Item(settings), HiiragiRegistry.Entry<Item> {

    //    Entry    //

    override fun toNbt(): NbtCompound {
        val nbt = NbtCompound()
        nbt.putString(HiiragiNbtConstants.ITEM, Registry.ITEM.getId(this).toString())
        return nbt
    }

    override fun toPacket(buf: PacketByteBuf) {
        buf.writeVarInt(Registry.ITEM.getRawId(this))
    }

}