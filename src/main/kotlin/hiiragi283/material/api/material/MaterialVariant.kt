package hiiragi283.material.api.material

import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.init.HiiragiRegistries
import hiiragi283.material.util.HiiragiNbtConstants
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf

@Suppress("UnstableApiUsage")
data class MaterialVariant(
    @JvmField val material: HiiragiMaterial,
    @JvmField val nbt: NbtCompound? = null
) : TransferVariant<HiiragiMaterial> {

    //    Conversion    //

    fun toPart() = HiiragiPart(
        HiiragiRegistries.SHAPE.getValue(nbt?.getString(HiiragiNbtConstants.SHAPE) ?: ""),
        material
    )

    //    TransferVariant    //

    override fun isBlank(): Boolean = material.isEmpty()

    override fun getObject(): HiiragiMaterial = material

    override fun getNbt(): NbtCompound? = nbt

    override fun toNbt(): NbtCompound {
        val result = NbtCompound()
        result.putString(HiiragiNbtConstants.MATERIAL, material.name)
        nbt?.let { result.put(HiiragiNbtConstants.TAG, it) }
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
        fun fromNbt(nbt: NbtCompound): MaterialVariant {
            val material: HiiragiMaterial =
                HiiragiRegistries.MATERIAL.getValue(nbt.getString(HiiragiNbtConstants.MATERIAL))
            val tag: NbtCompound? =
                if (nbt.contains(HiiragiNbtConstants.TAG)) nbt.getCompound(HiiragiNbtConstants.TAG) else null
            return MaterialVariant(material, tag)
        }

        @JvmStatic
        fun fromPacket(buf: PacketByteBuf): MaterialVariant {
            return if (buf.readBoolean()) {
                val material: HiiragiMaterial = HiiragiRegistries.MATERIAL.getValue(buf.readString())
                val tag: NbtCompound? = buf.readNbt()
                MaterialVariant(material, tag)
            } else {
                MaterialVariant(HiiragiMaterial.EMPTY)
            }
        }

    }

}