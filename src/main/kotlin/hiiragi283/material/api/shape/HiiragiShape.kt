package hiiragi283.material.api.shape

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.registry.HiiragiRegistry
import hiiragi283.material.init.HiiragiRegistries
import hiiragi283.material.util.HiiragiConstants
import hiiragi283.material.util.hiiragiId
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry

data class HiiragiShape(
    val name: String,
    val scale: Int = 0,
    val prefix: String = "",
    var translationKey: String = "hiiragi_shape.$name"
) : HiiragiRegistry.Entry<HiiragiShape> {

    constructor(name: String, scale: Number, prefix: String) : this(name, scale.toInt(), prefix)

    override fun asItem(): Item = Items.AIR

    //    Conversion    //

    val tagKey: TagKey<Item> = TagKey.of(Registry.ITEM_KEY, hiiragiId(name))

    fun getNotEmpty(): HiiragiShape? = takeUnless(HiiragiShape::isEmpty)

    fun getPart(material: HiiragiMaterial) = HiiragiPart(this, material)

    //    Boolean    //

    fun hasPrefix(): Boolean = prefix.isNotEmpty()

    fun hasScale(): Boolean = scale > 0

    fun isEmpty(): Boolean = this == EMPTY || name == HiiragiConstants.EMPTY || scale <= 0

    fun isValid(material: HiiragiMaterial): Boolean = this in material.shapeType.shapes

    //    Entry    //

    fun register() {
        HiiragiRegistries.SHAPE.register(name, this)
    }

    companion object {

        @JvmField
        val EMPTY = HiiragiShape(HiiragiConstants.EMPTY)

    }

}