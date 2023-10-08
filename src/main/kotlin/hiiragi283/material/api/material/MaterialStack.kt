package hiiragi283.material.api.material

import hiiragi283.material.api.registry.HiiragiRegistry
import hiiragi283.material.util.HiiragiNbtConstants
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf

data class MaterialStack(
    val material: HiiragiMaterial,
    var amount: Int
) : HiiragiRegistry.Entry<MaterialStack> {


    //    Boolean    //

    fun isEmpty() = material.isEmpty() || amount <= 0

    //    Entry    //

    override fun asItem(): Item = material.asItem()

    override fun toNbt(): NbtCompound {
        val nbt = NbtCompound()
        nbt.putString(HiiragiNbtConstants.MATERIAL, material.name)
        nbt.putInt(HiiragiNbtConstants.AMOUNT, amount)
        return nbt
    }

    override fun toPacket(buf: PacketByteBuf) {
        buf.writeString(material.name)
        buf.writeVarInt(amount)
    }

    companion object {

        @JvmStatic
        fun fromNbt(nbt: NbtCompound): MaterialStack = MaterialStack(
            HiiragiMaterial.fromNbt(nbt),
            nbt.getInt(HiiragiNbtConstants.AMOUNT)
        )

    }

}