package io.github.hiiragi283.material.api

import io.github.hiiragi283.material.api.material.*
import io.github.hiiragi283.material.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.material.api.registry.HTDefaultedMap
import io.github.hiiragi283.material.api.registry.HTDefaultedTable
import io.github.hiiragi283.material.api.registry.HTObjectKeySet
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemConvertible

@JvmDefaultWithCompatibility
interface HTMaterialsAddon {

    val modId: String

    val priority: Int

    //    Initialize    //

    fun registerShape(registry: HTObjectKeySet<HTShapeKey>) {}

    fun registerMaterialKey(registry: HTObjectKeySet<HTMaterialKey>) {}

    fun modifyMaterialContent(registry: HTDefaultedMap<HTMaterialKey, HTMaterialContentMap>) {}

    fun modifyMaterialProperty(registry: HTDefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {}

    fun modifyMaterialFlag(registry: HTDefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder>) {}

    fun modifyMaterialColor(registry: MutableMap<HTMaterialKey, ColorConvertible>) {}

    fun modifyMaterialFormula(registry: MutableMap<HTMaterialKey, FormulaConvertible>) {}

    fun modifyMaterialMolar(registry: MutableMap<HTMaterialKey, MolarMassConvertible>) {}

    fun modifyMaterialType(registry: MutableMap<HTMaterialKey, HTMaterialType>) {}

    //    Post Initialization    //

    fun bindItemToPart(registry: HTDefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {}

    fun bindFluidToPart(registry: HTDefaultedMap<HTMaterialKey, MutableCollection<Fluid>>) {}

    fun commonSetup() {}

    @Environment(EnvType.CLIENT)
    fun clientSetup() {
    }

}