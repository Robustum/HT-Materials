package hiiragi283.material.api.part

import hiiragi283.material.RagiMaterials
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.reigstry.HiiragiRegistries
import hiiragi283.material.api.reigstry.HiiragiRegistry
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.util.commonId
import hiiragi283.material.util.itemStack
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.TagKey
import net.minecraft.text.TranslatableText
import net.minecraft.util.registry.Registry

fun BlockState.getParts(): List<HiiragiPart> = this.itemStack().getParts()

fun ItemStack.getParts(): List<HiiragiPart> = this.streamTags().toList()
    .mapNotNull(TagKey<Item>::getPart)
    .map(HiiragiPart::getValue)

fun TagKey<Item>.getPart(): HiiragiPart? = HiiragiRegistries.PART.get(this.id)

data class HiiragiPart(
    val shape: HiiragiShape,
    val material: HiiragiMaterial
) : HiiragiRegistry.Entry<HiiragiPart> {

    override fun asItem(): Item = Registry.ITEM.get(RagiMaterials.id(tagPath))

    //    Conversion    //

    val tagPath: String = shape.prefix.replace("@", material.name)

    val tagKey: TagKey<Item> = TagKey.of(Registry.ITEM_KEY, commonId(tagPath))

    @Environment(EnvType.CLIENT)
    fun getName(): String = I18n.translate(shape.translationKey, material.translationKey)

    fun getText() = TranslatableText(shape.translationKey, TranslatableText(material.translationKey))

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HiiragiPart -> false
        else -> other.shape == this.shape && other.material == this.material
    }

    override fun hashCode(): Int {
        var result = shape.hashCode()
        result = 31 * result + material.hashCode()
        return result
    }

    override fun toString(): String = shape.name + ":" + material.name


    companion object {

        @JvmStatic
        fun getAllParts(): List<HiiragiPart> = HiiragiRegistries.SHAPE.getMap().values
            .flatMap { shape: HiiragiShape ->
                HiiragiRegistries.MATERIAL.getMap().values.map(shape::getPart)
            }

    }
}