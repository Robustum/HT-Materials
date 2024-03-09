package io.github.hiiragi283.api.transfer

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf

@Suppress("UnstableApiUsage")
sealed class HTMaterialVariant : TransferVariant<HTMaterial> {
    abstract fun getMaterialName(): String

    override fun toNbt(): NbtCompound = NbtCompound().apply {
        putString("material", getMaterialName())
        nbt?.let { put("tag", it) }
    }

    override fun toPacket(buf: PacketByteBuf) {
        if (isBlank) {
            buf.writeBoolean(false)
        } else {
            buf.writeBoolean(true)
            buf.writeString(getMaterialName())
            buf.writeNbt(nbt)
        }
    }

    companion object {
        @JvmField
        val BLANK: HTMaterialVariant = Empty

        @JvmOverloads
        @JvmStatic
        fun of(materialKey: HTMaterialKey?, nbt: NbtCompound? = null): HTMaterialVariant =
            if (materialKey == null || materialKey !in HTMaterialsAPI.INSTANCE.materialRegistry) {
                Empty
            } else {
                Impl(materialKey, nbt)
            }
    }

    private data object Empty : HTMaterialVariant() {
        override fun getMaterialName(): String = "empty"

        override fun isBlank(): Boolean = true

        override fun getObject(): HTMaterial = throw IllegalStateException()

        override fun getNbt(): NbtCompound? = null
    }

    private data class Impl(
        @JvmField val materialKey: HTMaterialKey,
        @JvmField val nbt: NbtCompound? = null,
    ) : HTMaterialVariant() {
        override fun getMaterialName(): String = materialKey.name

        override fun isBlank(): Boolean = false

        override fun getObject(): HTMaterial = materialKey.material

        override fun getNbt(): NbtCompound? = nbt
    }
}
