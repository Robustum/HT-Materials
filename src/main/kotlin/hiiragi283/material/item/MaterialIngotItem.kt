package hiiragi283.material.item

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.append
import hiiragi283.material.util.simpleItemModel
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder

class MaterialIngotItem(material: HiiragiMaterial) : MaterialItem(HiiragiShapes.INGOT, material) {

    //    MaterialItem    //

    override fun getModel(identifier: Identifier): ModelJsonBuilder = simpleItemModel("item/iron_ingot")
    override fun addRecipe(resourcePack: RuntimeResourcePack, identifier: Identifier) {
        //9x Nugget -> 1x Ingot
        resourcePack.addRecipeAndAdvancement(
            identifier.append("_shaped"),
            ShapedRecipeJsonBuilder.create(this)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', HiiragiShapes.NUGGET.getPart(part.material).tagKey)
                .criterionFromItem(this)
        )
        //1x Block -> 9x Ingot
        resourcePack.addRecipeAndAdvancement(
            identifier.append("_shapeless"),
            ShapelessRecipeJsonBuilder.create(this, 9)
                .input(HiiragiShapes.BLOCK.getPart(part.material).tagKey)
                .criterionFromItem(this)
        )
    }

}