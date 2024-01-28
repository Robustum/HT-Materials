package io.github.hiiragi283.material.compat

import io.github.hiiragi283.lib.registry.HTDefaultedTable
import io.github.hiiragi283.lib.registry.HTObjectKeySet
import io.github.hiiragi283.lib.util.isAir
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.api.shape.HTShapeKey
import net.minecraft.item.ItemConvertible
import net.minecraft.util.registry.Registry

@Suppress("unused")
object HMMIAddon : HTMaterialsAddon {
    override val modId: String = "modern_industrialization"

    override val priority: Int = 0

    override fun registerShape(registry: HTObjectKeySet<HTShapeKey>) {
        listOf(
            "blade",
            "bolt",
            "crushed_dust",
            "curved_plate",
            "double_ingot",
            "drill_head",
            "hot_ingot",
            "large_plate",
            "ring",
            "rotor",
            "tiny_dust",
            "wire",
        ).map(::HTShapeKey).forEach(registry::add)
    }

    override fun bindItemToPart(registry: HTDefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {
        // Register Tags for ALL MI Material Items
        HTMaterial.getMaterialKeys().forEach { material: HTMaterialKey ->
            HTShape.getShapeKeys().forEach { shape ->
                Registry.ITEM.get(shape.getIdentifier(material, modId)).run {
                    if (!this.isAir()) {
                        registry.getOrCreate(material, shape).add(this)
                    }
                }
            }
        }
    }

    /*override fun commonSetup() {
        HTRecipeRegisterCallback.EVENT.register { handler ->
            handler.addMIRecipe(
                HTMaterials.id("test_mi"),
                MIRecipeJson.create(MIMachineRecipeTypes.MIXER, 32, 200)
                    .addItemInput(HTPartManager.getDefaultItem(HTElementMaterials.COPPER, HTShapes.DUST)!!, 3)
                    .addItemInput(HTPartManager.getDefaultItem(HTElementMaterials.TIN, HTShapes.DUST)!!, 1)
                    .addItemOutput(HTPartManager.getDefaultItem(HTCommonMaterials.BRONZE, HTShapes.DUST)!!, 4)
                )
        }
    }*/
}
