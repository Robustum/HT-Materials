package io.github.hiiragi283.api.material.property

import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.shape.HTShape
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

@JvmDefaultWithCompatibility
interface HTMaterialProperty<T : HTMaterialProperty<T>> {
    val type: HTPropertyType<T>

    fun appendTooltip(
        material: HTMaterial,
        shape: HTShape?,
        stack: ItemStack,
        lines: MutableList<Text>,
    ) {}
}
