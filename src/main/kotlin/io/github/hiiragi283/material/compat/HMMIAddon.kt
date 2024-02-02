package io.github.hiiragi283.material.compat

import com.google.common.collect.ImmutableSet
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.util.collection.DefaultedTable
import io.github.hiiragi283.api.util.isAir
import net.minecraft.item.ItemConvertible
import net.minecraft.util.registry.Registry

@Suppress("unused")
object HMMIAddon : HTMaterialsAddon {
    override val modId: String = "modern_industrialization"
    override val priority: Int = 0

    override fun registerShape(registry: ImmutableSet.Builder<HTShapeKey>) {
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

    override fun bindItemToPart(registry: DefaultedTable<HTMaterialKey, HTShapeKey, MutableCollection<ItemConvertible>>) {
        // Register Tags for ALL MI Material Items
        HTMaterialsAPI.getInstance().materialRegistry().getKeys().forEach { material: HTMaterialKey ->
            HTMaterialsAPI.getInstance().shapeRegistry().getValues().forEach { shape ->
                Registry.ITEM.get(shape.getIdentifier(material, modId)).run {
                    if (!this.isAir()) {
                        registry.getOrCreate(material, shape.key).add(this)
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
                    .addItemInput(HTPartManagerOld.getDefaultItem(HTElementMaterials.COPPER, HTShapes.DUST)!!, 3)
                    .addItemInput(HTPartManagerOld.getDefaultItem(HTElementMaterials.TIN, HTShapes.DUST)!!, 1)
                    .addItemOutput(HTPartManagerOld.getDefaultItem(HTCommonMaterials.BRONZE, HTShapes.DUST)!!, 4)
                )
        }
    }*/
}
