package hiiragi283.material.item

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.append
import hiiragi283.material.util.simpleItemModel
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder

class MaterialNuggetItem(material: HiiragiMaterial) : MaterialItem(HiiragiShapes.NUGGET, material) {

    override fun getModel(identifier: Identifier): ModelJsonBuilder = simpleItemModel("item/iron_nugget")

    override fun addRecipe(resourcePack: RuntimeResourcePack, identifier: Identifier) {
        //1x Block -> 9x Ingot
        resourcePack.addRecipeAndAdvancement(
            identifier.append("_shapeless"),
            ShapelessRecipeJsonBuilder.create(this, 9)
                .input(HiiragiShapes.INGOT.getPart(part.material).tagKey)
                .criterionFromItem(this)
        )
    }

}