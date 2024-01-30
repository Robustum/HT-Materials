package io.github.hiiragi283.material.compat

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.util.collection.DefaultedMap
import io.github.hiiragi283.material.api.util.collection.DefaultedTable
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
            HTElementMaterials.COPPER to IRFluidRegistry.MOLTEN_COPPER_STILL,
            HTElementMaterials.GOLD to IRFluidRegistry.MOLTEN_GOLD_STILL,
            HTElementMaterials.IRON to IRFluidRegistry.MOLTEN_IRON_STILL,
            HTElementMaterials.LEAD to IRFluidRegistry.MOLTEN_LEAD_STILL,
            HTVanillaMaterials.NETHERITE to IRFluidRegistry.MOLTEN_NETHERITE_STILL,
            HTElementMaterials.SILVER to IRFluidRegistry.MOLTEN_SILVER_STILL,
            HTElementMaterials.TIN to IRFluidRegistry.MOLTEN_TIN_STILL,
        ).forEach { (key: HTMaterialKey, fluid: Fluid) -> registry.getOrCreate(key).add(fluid) }
    }

    override fun bindItemToPart(registry: DefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {
        listOf(
            HTElementMaterials.COPPER,
            HTElementMaterials.GOLD,
            HTElementMaterials.IRON,
            HTElementMaterials.LEAD,
            HTVanillaMaterials.NETHERITE,
            HTElementMaterials.SILVER,
            HTElementMaterials.TIN,
        ).forEach {
            // Chunks
            registry.getOrCreate(it, CHUNK).add(Registry.ITEM.get(CHUNK.getIdentifier(it, modId)))
            // Purified Ores
            registry.getOrCreate(it, PURIFIED_ORE).add(Registry.ITEM.get(PURIFIED_ORE.getIdentifier(it, modId)))
        }
    }
}
