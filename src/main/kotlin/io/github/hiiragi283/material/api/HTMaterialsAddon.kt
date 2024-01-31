package io.github.hiiragi283.material.api

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.util.collection.DefaultedMap
import io.github.hiiragi283.material.api.util.collection.DefaultedTable
import net.fabricmc.api.EnvType
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemConvertible

@JvmDefaultWithCompatibility
interface HTMaterialsAddon {
    val modId: String

    val priority: Int

    //    Initialize    //

    fun registerShape(registry: ImmutableSet.Builder<HTShapeKey>) {}

    fun registerMaterialKey(registry: ImmutableSet.Builder<HTMaterialKey>) {}

    fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap>) {}

    fun modifyMaterialProperty(registry: DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {}

    fun modifyMaterialFlag(registry: DefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder>) {}

    fun modifyMaterialColor(registry: MutableMap<HTMaterialKey, ColorConvertible>) {}

    fun modifyMaterialFormula(registry: MutableMap<HTMaterialKey, FormulaConvertible>) {}

    fun modifyMaterialMolar(registry: MutableMap<HTMaterialKey, MolarMassConvertible>) {}

    fun modifyMaterialType(registry: MutableMap<HTMaterialKey, HTMaterialType>) {}

    //    Post Initialization    //

    fun bindItemToPart(registry: DefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {}

    fun bindFluidToPart(registry: DefaultedMap<HTMaterialKey, MutableCollection<Fluid>>) {}

    fun postInitialize(envType: EnvType) {}
}
