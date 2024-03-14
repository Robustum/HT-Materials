package io.github.hiiragi283.api.part

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShape
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

data class HTPart(
    val materialKey: HTMaterialKey,
    val shape: HTShape,
) {
    constructor(material: HTMaterial, shape: HTShape) : this(material.key, shape)

    constructor(material: String, shape: String) : this(HTMaterialKey(material), HTShape(shape))

    companion object {
        @JvmField
        val CODEC: Codec<HTPart> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.STRING.fieldOf("material").forGetter { it.material.key.name },
                Codec.STRING.fieldOf("shape").forGetter { it.shape.name },
            ).apply(instance, ::HTPart)
        }
    }

    val material: HTMaterial
        get() = materialKey.material

    val partId: Identifier = Identifier("c", "$shape/$materialKey")

    val partTag: Tag<Item>
        get() = TagRegistry.item(partId)
}
