package hiiragi283.material.item

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiItems
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.append
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import pers.solid.brrp.v1.api.RuntimeResourcePack

class MaterialDustItem(material: HiiragiMaterial) : MaterialItem(HiiragiShapes.DUST, material) {

    override fun addRecipe(resourcePack: RuntimeResourcePack) {
        //1x Ingot + 1x Hammer -> 1x Plate
        resourcePack.addRecipeAndAdvancement(
            getIdentifier().append("_shaped"),
            ShapedRecipeJsonBuilder.create(this)
                .patterns("A", "B")
                .input('A', HiiragiItems.SMITHING_HAMMER)
                .input('B', HiiragiShapes.INGOT.getPart(part.material).tagKey)
                .criterionFromItem(this)
        )
    }

}