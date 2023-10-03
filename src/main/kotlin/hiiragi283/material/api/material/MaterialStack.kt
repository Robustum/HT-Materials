package hiiragi283.material.api.material

import hiiragi283.material.api.reigstry.HiiragiRegistries
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf

@Suppress("UnstableApiUsage")
data class MaterialStack(
    val material: HiiragiMaterial,
    val nbt: NbtCompound? = null
) : TransferVariant<HiiragiMaterial> {

    override fun isBlank(): Boolean = material == HiiragiMaterial.EMPTY

    override fun getObject(): HiiragiMaterial = material

    override fun getNbt(): NbtCompound? = nbt

    override fun toNbt(): NbtCompound {
        val result = NbtCompound()
        result.putString("material", material.name)
        nbt?.let { result.put("tag", it) }
        return result
    }

    override fun toPacket(buf: PacketByteBuf) {
        if (isBlank) {
            buf.writeBoolean(false)
        } else {
            buf.writeBoolean(true)
            buf.writeString(material.name)
            buf.writeNbt(nbt)
        }
    }

    companion object {
        @JvmStatic
        fun fromNbt(nbt: NbtCompound): MaterialStack {
            val material: HiiragiMaterial = HiiragiRegistries.MATERIAL.getMap()
                .getOrDefault(nbt.getString("material"), HiiragiMaterial.EMPTY)
            val tag: NbtCompound? = if (nbt.contains("nbt")) nbt.getCompound("tag") else null
            return MaterialStack(material, tag)
        }

        @JvmStatic
        fun fromPacket(buf: PacketByteBuf): MaterialStack {
            return if (buf.readBoolean()) {
                val material: HiiragiMaterial = HiiragiRegistries.MATERIAL.getMap()
                    .getOrDefault(buf.readString(), HiiragiMaterial.EMPTY)
                val tag: NbtCompound? = buf.readNbt()
                MaterialStack(material, tag)
            } else {
                MaterialStack(HiiragiMaterial.EMPTY)
            }
        }

    }

}