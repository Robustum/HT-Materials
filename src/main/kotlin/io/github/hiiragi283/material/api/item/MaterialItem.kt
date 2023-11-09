package io.github.hiiragi283.material.api.item

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item

class MaterialItem(
    override val material: HTMaterial,
    override val shape: HTShape,
    settings: FabricItemSettings
) : Item(settings), MaterialItemConvertible