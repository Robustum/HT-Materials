package hiiragi283.material.api.block

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.shape.HiiragiShapes
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder

class MaterialStorageBlock(material: HiiragiMaterial) : MaterialBlock(
    HiiragiShapes.BLOCK,
    material,
    FabricBlockSettings.copyOf(Blocks.IRON_BLOCK),
    FabricItemSettings()
) {

    //    MaterialBlock    //

    override fun getBlockModel(resourcePack: RuntimeResourcePack, identifier: Identifier): Identifier {
        TODO("Not yet implemented")
    }

    override fun getItemModel(identifier: Identifier): ModelJsonBuilder {
        TODO("Not yet implemented")
    }

    override fun addRecipe(resourcePack: RuntimeResourcePack, identifier: Identifier) {
        TODO("Not yet implemented")
    }

}