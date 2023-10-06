package hiiragi283.material.item

import hiiragi283.material.api.item.MaterialItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.simpleItemModel
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder

class MaterialDustItem(material: HiiragiMaterial) : MaterialItem(HiiragiShapes.DUST, material) {

    override fun getModel(identifier: Identifier): ModelJsonBuilder = simpleItemModel("item/sugar")

    override fun addRecipe(resourcePack: RuntimeResourcePack, identifier: Identifier) {

    }

}