package io.github.hiiragi283.material.compat

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.collection.DefaultedMap
import io.github.hiiragi283.api.util.collection.DefaultedTable
import me.steven.indrev.IndustrialRevolution
import me.steven.indrev.registry.IRFluidRegistry
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemConvertible
import net.minecraft.util.registry.Registry

@Suppress("unused")
object HMIRAddon : HTMaterialsAddon {
    override val modId: String = IndustrialRevolution.MOD_ID
    override val priority: Int = 0

    @JvmField
    val CHUNK = HTShapeKey("chunk")

    @JvmField
    val PURIFIED_ORE = HTShapeKey("purified_ore")

    override fun registerShape(registry: ImmutableSet.Builder<HTShapeKey>) {
        registry.add(CHUNK, PURIFIED_ORE)
    }

    override fun bindFluidToPart(registry: DefaultedMap<HTMaterialKey, MutableCollection<Fluid>>) {
        mapOf(
            HTMaterialKeys.COPPER to IRFluidRegistry.MOLTEN_COPPER_STILL,
            HTMaterialKeys.GOLD to IRFluidRegistry.MOLTEN_GOLD_STILL,
            HTMaterialKeys.IRON to IRFluidRegistry.MOLTEN_IRON_STILL,
            HTMaterialKeys.LEAD to IRFluidRegistry.MOLTEN_LEAD_STILL,
            HTMaterialKeys.NETHERITE to IRFluidRegistry.MOLTEN_NETHERITE_STILL,
            HTMaterialKeys.SILVER to IRFluidRegistry.MOLTEN_SILVER_STILL,
            HTMaterialKeys.TIN to IRFluidRegistry.MOLTEN_TIN_STILL,
        ).forEach { (key: HTMaterialKey, fluid: Fluid) -> registry.getOrCreate(key).add(fluid) }
    }

    override fun bindItemToPart(registry: DefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {
        listOf(
            HTMaterialKeys.COPPER,
            HTMaterialKeys.GOLD,
            HTMaterialKeys.IRON,
            HTMaterialKeys.LEAD,
            HTMaterialKeys.NETHERITE,
            HTMaterialKeys.SILVER,
            HTMaterialKeys.TIN,
        ).forEach {
            // Chunks
            registry.getOrCreate(it, CHUNK).add(Registry.ITEM.get(CHUNK.getShape().getIdentifier(it, modId)))
            // Purified Ores
            registry.getOrCreate(it, PURIFIED_ORE).add(Registry.ITEM.get(PURIFIED_ORE.getShape().getIdentifier(it, modId)))
        }
    }
}
