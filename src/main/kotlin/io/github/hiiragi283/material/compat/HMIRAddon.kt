package io.github.hiiragi283.material.compat

import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeKeys
import me.steven.indrev.IndustrialRevolution
import me.steven.indrev.registry.IRFluidRegistry
import me.steven.indrev.registry.IRItemRegistry
import net.minecraft.fluid.Fluid
import net.minecraft.util.registry.Registry

@Suppress("unused")
object HMIRAddon : HTMaterialsAddon {
    override val modId: String = IndustrialRevolution.MOD_ID
    override val priority: Int = 0

    @JvmField
    val CHUNK = HTShapeKey("chunk")

    @JvmField
    val PURIFIED_ORE = HTShapeKey("purified_ore")

    override fun registerShape(shapeHelper: HTMaterialsAddon.ShapeHelper) {
        shapeHelper.addShapeKey(CHUNK)
        shapeHelper.addShapeKey(PURIFIED_ORE)
    }

    override fun bindFluidToPart(builder: HTFluidManager.Builder) {
        mapOf(
            HTMaterialKeys.COPPER to IRFluidRegistry.MOLTEN_COPPER_STILL,
            HTMaterialKeys.GOLD to IRFluidRegistry.MOLTEN_GOLD_STILL,
            HTMaterialKeys.IRON to IRFluidRegistry.MOLTEN_IRON_STILL,
            HTMaterialKeys.LEAD to IRFluidRegistry.MOLTEN_LEAD_STILL,
            HTMaterialKeys.NETHERITE to IRFluidRegistry.MOLTEN_NETHERITE_STILL,
            HTMaterialKeys.SILVER to IRFluidRegistry.MOLTEN_SILVER_STILL,
            HTMaterialKeys.TIN to IRFluidRegistry.MOLTEN_TIN_STILL,
        ).forEach { (key: HTMaterialKey, fluid: Fluid) -> builder.add(key, fluid) }
    }

    override fun bindItemToPart(builder: HTPartManager.Builder) {
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
            builder.add(it, CHUNK, Registry.ITEM.get(CHUNK.getShape().getIdentifier(it, modId)))
            // Purified Ores
            builder.add(it, PURIFIED_ORE, Registry.ITEM.get(PURIFIED_ORE.getShape().getIdentifier(it, modId)))
        }
        builder.add(HTMaterialKeys.WOOD, HTShapeKeys.DUST, Registry.ITEM.get(id("sawdust")))
        builder.add(HTMaterialKeys.SULFUR, HTShapeKeys.GEM, IRItemRegistry.SULFUR_CRYSTAL_ITEM)
    }
}
