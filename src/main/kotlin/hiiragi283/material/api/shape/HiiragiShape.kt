package hiiragi283.material.api.shape

import hiiragi283.material.RagiMaterials
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.reigstry.HiiragiRegistries
import hiiragi283.material.api.reigstry.HiiragiRegistry
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry

data class HiiragiShape(
    val name: String,
    val scale: Int,
    val prefix: String,
    var translationKey: String = "hiiragi_shape.$name"
) : HiiragiRegistry.Entry<HiiragiShape> {

    constructor(name: String, scale: Number, prefix: String) : this(name, scale.toInt(), prefix)

    override fun asItem(): Item = Items.AIR

    //    Conversion    //

    val tagKey: TagKey<Item> = TagKey.of(Registry.ITEM_KEY, RagiMaterials.id(name))

    fun getPart(material: HiiragiMaterial) = HiiragiPart(this, material)

    //    Boolean    //

    fun hasScale(): Boolean = scale >= 0

    fun isValid(material: HiiragiMaterial): Boolean = this in material.shapeType.shapes

    //    Registration    //

    fun register() {
        HiiragiRegistries.SHAPE.register(name, this)
    }

}