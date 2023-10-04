package hiiragi283.material.api.material

import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.reigstry.HiiragiRegistries
import hiiragi283.material.api.reigstry.HiiragiRegistry
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.api.shape.HiiragiShapeType
import hiiragi283.material.api.shape.HiiragiShapeTypes
import hiiragi283.material.api.shape.HiiragiShapes
import hiiragi283.material.util.HiiragiNbtConstants
import hiiragi283.material.util.hiiragiId
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry

data class HiiragiMaterial(
    val name: String,
    var color: Int = 0xFFFFFF,
    var formula: String = "",
    var miningProperty: MiningProperty = MiningProperty(),
    var molar: Double = 0.0,
    var shapeType: HiiragiShapeType = HiiragiShapeTypes.EMPTY,
    var tempBoil: Int = 0,
    var tempMelt: Int = 0,
    var translationKey: String = "hiiragi_material.$name"
) : HiiragiRegistry.Entry<HiiragiMaterial> {

    override fun asItem(): Item = Items.AIR

    //    Conversion    //

    val tagKey: TagKey<Item> = TagKey.of(Registry.ITEM_KEY, hiiragiId(name))

    fun addBracket() = copy(formula = "($formula)")

    fun getNotEmpty(): HiiragiMaterial? = takeUnless(HiiragiMaterial::isEmpty)

    fun getPart(shape: HiiragiShape) = HiiragiPart(shape, this)

    //    Boolean    //

    fun hasFormula(): Boolean = formula.isNotEmpty()

    fun hasMolar(): Boolean = molar > 0.0

    fun hasTempBoil(): Boolean = tempBoil > 0

    fun hasTempMelt(): Boolean = tempMelt > 0

    fun isEmpty(): Boolean = this == EMPTY || name == "empty"

    fun isGem(): Boolean = HiiragiShapes.GEM.isValid(this)

    fun isMetal(): Boolean = HiiragiShapes.METAL.isValid(this)

    fun isFluid(): Boolean = isGas() || isLiquid()

    fun isGas(): Boolean = HiiragiShapes.GAS.isValid(this)

    fun isLiquid(): Boolean = HiiragiShapes.LIQUID.isValid(this)

    fun isSolid(): Boolean = HiiragiShapes.SOLID.isValid(this)

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HiiragiMaterial -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + color
        result = 31 * result + formula.hashCode()
        result = 31 * result + molar.hashCode()
        result = 31 * result + shapeType.hashCode()
        result = 31 * result + tempBoil
        result = 31 * result + tempMelt
        result = 31 * result + translationKey.hashCode()
        return result
    }

    override fun toString(): String = "material:$name"

    //    Entry    //

    fun register() {
        HiiragiRegistries.MATERIAL.register(name, this)
    }

    override fun toNbt(): NbtCompound {
        val nbt = NbtCompound()
        nbt.putString(HiiragiNbtConstants.MATERIAL, name)
        return nbt
    }

    override fun toPacket(buf: PacketByteBuf) {
        buf.writeString(name)
    }

    companion object {

        @JvmField
        val EMPTY = HiiragiMaterial("empty")

        @JvmStatic
        fun fromNbt(nbt: NbtCompound): HiiragiMaterial =
            HiiragiRegistries.MATERIAL.getValue(nbt.getString(HiiragiNbtConstants.MATERIAL))

        @JvmStatic
        fun fromPacket(buf: PacketByteBuf): HiiragiMaterial = HiiragiRegistries.MATERIAL.getValue(buf.readString())
    }

}