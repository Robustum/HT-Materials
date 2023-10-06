package hiiragi283.material.block

import hiiragi283.material.api.block.MaterialBlock
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.append
import hiiragi283.material.util.hiiragiId
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder

class MaterialStorageBlock(material: HiiragiMaterial) : MaterialBlock(HiiragiShapes.BLOCK, material) {

    //    MaterialBlock    //

    override fun getBlockModel(resourcePack: RuntimeResourcePack): Identifier = hiiragiId(
        when {
            part.material.isMetal() -> "storage_metal"
            part.material.isGem() -> "storage_gem"
            else -> "storage_dust"
        }
    )

    override fun getItemModel(): ModelJsonBuilder = ModelJsonBuilder.create(
        hiiragiId(
            when {
                part.material.isMetal() -> "block/storage_metal"
                part.material.isGem() -> "block/storage_gem"
                else -> "block/storage_dust"
            }
        )
    )

    override fun addRecipe(resourcePack: RuntimeResourcePack) {
        //9x Ingot/Gem/Dust -> 1x Block
        val shapedRecipe: ShapedRecipeJsonBuilder = ShapedRecipeJsonBuilder.create(this)
            .pattern("AAA")
            .pattern("AAA")
            .pattern("AAA")
            .criterionFromItem(this)
        if (part.material.isMetal()) {
            resourcePack.addRecipeAndAdvancement(
                getIdentifier().append("_shaped"),
                shapedRecipe
                    .input('A', HiiragiShapes.INGOT.getPart(part.material).tagKey)
            )
        } else if (part.material.isMetal()) {
            resourcePack.addRecipeAndAdvancement(
                getIdentifier().append("_shaped"),
                shapedRecipe
                    .input('A', HiiragiShapes.GEM.getPart(part.material).tagKey)
            )
        } else {
            resourcePack.addRecipeAndAdvancement(
                getIdentifier().append("_shaped"),
                shapedRecipe
                    .input('A', HiiragiShapes.DUST.getPart(part.material).tagKey)
            )
        }
    }

}