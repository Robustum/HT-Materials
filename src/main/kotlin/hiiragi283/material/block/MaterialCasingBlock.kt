package hiiragi283.material.block

import hiiragi283.material.api.block.MaterialBlock
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiItems
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.append
import hiiragi283.material.util.hiiragiId
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack

class MaterialCasingBlock(material: HiiragiMaterial) : MaterialBlock(HiiragiShapes.CASING, material) {

    override fun getBlockModel(resourcePack: RuntimeResourcePack): Identifier = hiiragiId("casing")

    override fun addRecipe(resourcePack: RuntimeResourcePack) {
        //8x Plate + 1x Hammer -> 1x Casing
        resourcePack.addRecipeAndAdvancement(
            getIdentifier().append("_smithing"),
            ShapedRecipeJsonBuilder.create(this)
                .patterns("AAA", "ABA", "AAA")
                .input('A', HiiragiShapes.PLATE.getPart(part.material).tagKey)
                .input('B', HiiragiItems.SMITHING_HAMMER)
                .criterionFromItem(this)
        )
    }

}