package hiiragi283.material.api.material

import hiiragi283.material.api.block.MaterialBlock
import hiiragi283.material.api.fluid.MaterialFluid
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.registry.HiiragiRegistry
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.api.shape.HiiragiShapeType
import hiiragi283.material.init.HiiragiItemGroups
import hiiragi283.material.init.HiiragiRegistries
import hiiragi283.material.init.HiiragiShapeTypes
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.HiiragiNbtConstants
import hiiragi283.material.util.MiningProperty
import hiiragi283.material.util.hiiragiId
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.tag.TagKey
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

data class HiiragiMaterial(
    val name: String,
    var color: Int = 0xFFFFFF,
    var formula: String = "",
    var molar: Double = 0.0,
    var rarity: Rarity = Rarity.COMMON,
    var shapeType: HiiragiShapeType = HiiragiShapeTypes.EMPTY,
    var tempBoil: Int = 0,
    var tempMelt: Int = 0,
    var translationKey: String = "hiiragi_material.$name"
) : HiiragiRegistry.Entry<HiiragiMaterial> {

    var blockSettings: FabricBlockSettings = MaterialBlock.Settings.METAL
    var itemSettings: FabricItemSettings = FabricItemSettings()
        .group(HiiragiItemGroups.MATERIAL)
        .rarity(rarity)

    var miningProperty: MiningProperty = MiningProperty()

    var fluid: () -> MaterialFluid? = { MaterialFluid(this) }

    override fun asItem(): Item = Items.AIR

    //    Conversion    //

    val identifier: Identifier = hiiragiId(name)

    val tagKey: TagKey<Item> = TagKey.of(Registry.ITEM_KEY, identifier)

    fun addBracket() = copy(formula = "($formula)")

    fun getItems(): List<Item> = HiiragiRegistries.SHAPE.getValues()
        .map(this::getPart)
        .map(HiiragiPart::asItem)
        .filterNot { it == Items.AIR }

    fun getNotEmpty(): HiiragiMaterial? = takeUnless(HiiragiMaterial::isEmpty)

    fun getPart(shape: HiiragiShape) = HiiragiPart(shape, this)

    fun getText() = TranslatableText(translationKey)

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