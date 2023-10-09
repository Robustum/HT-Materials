package hiiragi283.material.item

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiItems
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.append
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import pers.solid.brrp.v1.api.RuntimeResourcePack

class MaterialGearItem(material: HiiragiMaterial) : MaterialItem(HiiragiShapes.GEAR, material) {

    override fun addRecipe(resourcePack: RuntimeResourcePack) {
        //4x Ingot + 1x Hammer -> 1x Gear
        resourcePack.addRecipeAndAdvancement(
            getIdentifier().append("_shaped"),
            ShapedRecipeJsonBuilder.create(this)
                .patterns(" A ", "ABA", " A ")
                .input('A', HiiragiShapes.INGOT.getPart(part.material).tagKey)
                .input('B', HiiragiItems.SMITHING_HAMMER)
                .criterionFromItem(this)
        )
    }

}