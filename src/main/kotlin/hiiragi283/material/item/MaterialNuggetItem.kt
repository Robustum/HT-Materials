package hiiragi283.material.item

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.append
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import pers.solid.brrp.v1.api.RuntimeResourcePack

class MaterialNuggetItem(material: HiiragiMaterial) : MaterialItem(HiiragiShapes.NUGGET, material) {

    override fun addRecipe(resourcePack: RuntimeResourcePack) {
        //1x Block -> 9x Ingot
        resourcePack.addRecipeAndAdvancement(
            getIdentifier().append("_shapeless"),
            ShapelessRecipeJsonBuilder.create(this, 9)
                .input(HiiragiShapes.INGOT.getPart(part.material).tagKey)
                .criterionFromItem(this)
        )
    }

}