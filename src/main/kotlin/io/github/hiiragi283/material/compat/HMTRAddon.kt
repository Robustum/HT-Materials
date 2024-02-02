package io.github.hiiragi283.material.compat

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.collection.DefaultedTable
import io.github.hiiragi283.material.HTCommonMaterials
import net.minecraft.item.ItemConvertible
import techreborn.init.TRContent

@Suppress("unused")
object HMTRAddon : HTMaterialsAddon {
    override val modId: String = "techreborn"
    override val priority: Int = 0

    @JvmField
    val SMALL_DUST = HTShapeKey("small_dust")

    override fun registerShape(registry: ImmutableSet.Builder<HTShapeKey>) {
        registry.add(SMALL_DUST)
    }

    override fun bindItemToPart(registry: DefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {
        registry.getOrCreate(HTCommonMaterials.RUBY, HTShapeKeys.GEM)
            .add(TRContent.Gems.RUBY)
        registry.getOrCreate(HTCommonMaterials.SAPPHIRE, HTShapeKeys.GEM)
            .add(TRContent.Gems.SAPPHIRE)
        registry.getOrCreate(HTMaterialKeys.PHOSPHORUS, HTShapeKeys.DUST)
            .add(TRContent.Dusts.PHOSPHOROUS)
        registry.getOrCreate(HTMaterialKeys.PHOSPHORUS, SMALL_DUST)
            .add(TRContent.SmallDusts.PHOSPHOROUS)
    }
}
