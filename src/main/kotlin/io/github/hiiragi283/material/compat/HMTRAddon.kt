package io.github.hiiragi283.material.compat

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.materials.HTCommonMaterials
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.util.collection.DefaultedTable
import net.minecraft.item.ItemConvertible
import techreborn.init.TRContent

@Suppress("unused")
object HMTRAddon : HTMaterialsAddon {
    override val modId: String = "techreborn"
    override val priority: Int = 0

    override fun registerShape(registry: ImmutableSet.Builder<HTShapeKey>) {
        registry.add(HTShapeKey("small_dust"))
    }

    override fun bindItemToPart(registry: DefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {
        registry.getOrCreate(HTCommonMaterials.RUBY, HTShapeKey("gem"))
            .add(TRContent.Gems.RUBY)
        registry.getOrCreate(HTCommonMaterials.SAPPHIRE, HTShapeKey("gem"))
            .add(TRContent.Gems.SAPPHIRE)
        registry.getOrCreate(HTElementMaterials.PHOSPHORUS, HTShapeKey("dust"))
            .add(TRContent.Dusts.PHOSPHOROUS)
        registry.getOrCreate(HTElementMaterials.PHOSPHORUS, HTShapeKey("small_dust"))
            .add(TRContent.SmallDusts.PHOSPHOROUS)
    }
}
