package io.github.hiiragi283.material.compat

import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extension.HTColor
import io.github.hiiragi283.api.extension.averageColor
import io.github.hiiragi283.api.fluid.HTFluidRegistry
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialKeys
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.part.HTPartRegistry
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

    @JvmField
    val NIKOLITE = HTMaterialKey("nikolite")

    override fun registerShape(shapeHelper: HTMaterialsAddon.ShapeHelper) {
        shapeHelper.addShapeKey(CHUNK)
        shapeHelper.addShapeKey(PURIFIED_ORE)
    }

    override fun registerMaterial(materialHelper: HTMaterialsAddon.MaterialHelper) {
        materialHelper.addMaterialKey(NIKOLITE)
        materialHelper.setComposition(
            NIKOLITE,
            HTMaterialComposition.molecular {
                color = averageColor(HTColor.DARK_BLUE, HTColor.DARK_GREEN)
            },
        )
    }

    override fun registerFluidRegistry(registry: HTFluidRegistry) {
        mapOf(
            HTMaterialKeys.COPPER to IRFluidRegistry.MOLTEN_COPPER_STILL,
            HTMaterialKeys.GOLD to IRFluidRegistry.MOLTEN_GOLD_STILL,
            HTMaterialKeys.IRON to IRFluidRegistry.MOLTEN_IRON_STILL,
            HTMaterialKeys.LEAD to IRFluidRegistry.MOLTEN_LEAD_STILL,
            HTMaterialKeys.NETHERITE to IRFluidRegistry.MOLTEN_NETHERITE_STILL,
            HTMaterialKeys.SILVER to IRFluidRegistry.MOLTEN_SILVER_STILL,
            HTMaterialKeys.TIN to IRFluidRegistry.MOLTEN_TIN_STILL,
        ).forEach { (key: HTMaterialKey, fluid: Fluid) -> registry.add(fluid, key) }
    }

    override fun registerPartRegistry(registry: HTPartRegistry) {
        listOf(
            HTMaterialKeys.COPPER,
            HTMaterialKeys.GOLD,
            HTMaterialKeys.IRON,
            HTMaterialKeys.LEAD,
            HTMaterialKeys.NETHERITE,
            HTMaterialKeys.SILVER,
            HTMaterialKeys.TIN,
            NIKOLITE,
        ).forEach {
            // Chunks
            registry.add(Registry.ITEM.get(CHUNK.getShape().getIdentifier(it, modId)), it, CHUNK)
            // Purified Ores
            registry.add(Registry.ITEM.get(PURIFIED_ORE.getShape().getIdentifier(it, modId)), it, PURIFIED_ORE)
        }
        registry.add(Registry.ITEM.get(id("sawdust")), HTMaterialKeys.WOOD, HTShapeKeys.DUST)
        registry.add(IRItemRegistry.SULFUR_CRYSTAL_ITEM, HTMaterialKeys.SULFUR, HTShapeKeys.GEM)
    }
}
