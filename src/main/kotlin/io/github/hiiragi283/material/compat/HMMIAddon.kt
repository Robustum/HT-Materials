package io.github.hiiragi283.material.compat

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extension.nonAirOrNull
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPartRegistry
import io.github.hiiragi283.api.shape.HTShape
import net.minecraft.util.registry.Registry

@Suppress("unused")
object HMMIAddon : HTMaterialsAddon {
    override val modId: String = "modern_industrialization"
    override val priority: Int = 0

    override fun registerShape(shapeHelper: HTMaterialsAddon.ShapeHelper) {
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
        ).map(::HTShape).forEach(shapeHelper::addShape)
    }

    override fun registerPartRegistry(registry: HTPartRegistry) {
        // Register Tags for ALL MI Material Items
        HTMaterialsAPI.INSTANCE.materialRegistry().keys.forEach { material: HTMaterialKey ->
            HTMaterialsAPI.INSTANCE.shapeRegistry().values.forEach { shape ->
                Registry.ITEM.get(shape.getId(material, modId)).nonAirOrNull?.run {
                    registry.add(this, material, shape)
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
