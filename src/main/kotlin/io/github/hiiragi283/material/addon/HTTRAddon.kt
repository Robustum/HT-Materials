package io.github.hiiragi283.material.addon

import io.github.hiiragi283.material.api.addon.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.materials.HTCommonMaterials
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapes
import techreborn.init.TRContent

object HTTRAddon : HTMaterialsAddon {

    override val modId: String = "techreborn"

    override fun commonSetup() {
        HTPartManager.register(HTCommonMaterials.RUBY, HTShapes.GEM, TRContent.Gems.RUBY)
        HTPartManager.register(HTCommonMaterials.RUBY, HTShapes.GEM, TRContent.Gems.RUBY)
        HTPartManager.register(HTCommonMaterials.SAPPHIRE, HTShapes.GEM, TRContent.Gems.SAPPHIRE)
        HTPartManager.register(HTCommonMaterials.TUNGSTEN_STEEL, HTShapes.HOT_INGOT, TRContent.Ingots.HOT_TUNGSTENSTEEL)
        HTPartManager.register(HTElementMaterials.PHOSPHORUS, HTShapes.DUST, TRContent.Dusts.PHOSPHOROUS)
        HTPartManager.register(HTElementMaterials.PHOSPHORUS, HTShapes.SMALL_DUST, TRContent.SmallDusts.PHOSPHOROUS)
        HTPartManager.register(HTVanillaMaterials.END_STONE, HTShapes.DUST, TRContent.Dusts.ENDSTONE)
        HTPartManager.register(HTVanillaMaterials.END_STONE, HTShapes.SMALL_DUST, TRContent.SmallDusts.ENDSTONE)
        HTPartManager.register(HTVanillaMaterials.WOOD, HTShapes.DUST, TRContent.Dusts.SAW)
        HTPartManager.register(HTVanillaMaterials.WOOD, HTShapes.SMALL_DUST, TRContent.SmallDusts.SAW)
    }

}