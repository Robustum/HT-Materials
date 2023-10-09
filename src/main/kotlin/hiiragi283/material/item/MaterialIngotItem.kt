package hiiragi283.material.item

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.append
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import pers.solid.brrp.v1.api.RuntimeResourcePack

class MaterialIngotItem(material: HiiragiMaterial) : MaterialItem(HiiragiShapes.INGOT, material) {

    //    MaterialItem    //

    override fun addRecipe(resourcePack: RuntimeResourcePack) {
        //9x Nugget -> 1x Ingot
        resourcePack.addRecipeAndAdvancement(
            getIdentifier().append("_shaped"),
            ShapedRecipeJsonBuilder.create(this)
                .patterns("AAA", "AAA", "AAA")
                .input('A', HiiragiShapes.NUGGET.getPart(part.material).tagKey)
                .criterionFromItem(this)
        )
        //1x Block -> 9x Ingot
        resourcePack.addRecipeAndAdvancement(
            getIdentifier().append("_shapeless"),
            ShapelessRecipeJsonBuilder.create(this, 9)
                .input(HiiragiShapes.BLOCK.getPart(part.material).tagKey)
                .criterionFromItem(this)
        )
    }

}