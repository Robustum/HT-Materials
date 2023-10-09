package hiiragi283.material.api.fluid

import hiiragi283.material.api.registry.HiiragiRegistry
import hiiragi283.material.util.HiiragiNbtConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

@Suppress("UnstableApiUsage")
data class FluidStack(
    val fluid: Fluid,
    var amount: Long = 0,
    var nbt: NbtCompound? = null
) : HiiragiRegistry.Entry<FluidStack> {

    constructor(variant: FluidVariant, amount: Long) : this(variant.fluid, amount, variant.nbt)

    //    Conversion    //

    fun toVariant(): FluidVariant = FluidVariant.of(fluid, nbt)

    //    Boolean    //

    fun isEmpty() = this == EMPTY || fluid == Fluids.EMPTY || amount <= 0

    //    Entry    //

    override fun asItem(): Item = fluid.bucketItem

    override fun toNbt(): NbtCompound {
        val nbt = NbtCompound()
        nbt.putString(HiiragiNbtConstants.FLUID, Registry.FLUID.getId(fluid).toString())
        nbt.putLong(HiiragiNbtConstants.AMOUNT, amount)
        this.nbt?.let { nbt.put(HiiragiNbtConstants.TAG, it) }
        return nbt
    }

    override fun toPacket(buf: PacketByteBuf) {
        buf.writeVarInt(Registry.FLUID.getRawId(fluid))
        buf.writeVarLong(amount)
        buf.writeNbt(nbt)
    }

    companion object {

        @JvmField
        val EMPTY = FluidStack(Fluids.EMPTY, 0)

        @JvmStatic
        fun isFluidEqual(stack1: FluidStack, stack2: FluidStack): Boolean = stack1.fluid == stack2.fluid

        @JvmStatic
        fun isStackEqual(stack1: FluidStack, stack2: FluidStack): Boolean =
            isFluidEqual(stack1, stack2) && stack1.amount == stack2.amount

    }

}