package io.github.hiiragi283.api.material

import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapes
import net.minecraft.block.Material

sealed interface HTMaterialType {
    val defaultShape: HTShape?
    val blockMaterial: Material
    val resourcePath: String

    data object Undefined : HTMaterialType {
        override val defaultShape: HTShape? = null
        override val blockMaterial: Material = Material.SOIL
        override val resourcePath: String = "solid"
    }

    enum class Gem : HTMaterialType {
        AMETHYST,
        COAL,
        CUBIC,
        DIAMOND,
        EMERALD,
        FLINT,
        LAPIS,
        QUARTZ,
        RUBY,
        ;

        override val defaultShape: HTShape? = HTShapes.GEM
        override val blockMaterial: Material = Material.STONE
        override val resourcePath: String = "gem"
    }

    data object Metal : HTMaterialType {
        override val defaultShape: HTShape = HTShapes.INGOT
        override val blockMaterial: Material = Material.METAL
        override val resourcePath: String = "metal"
    }

    data object Stone : HTMaterialType {
        override val defaultShape: HTShape? = null
        override val blockMaterial: Material = Material.STONE
        override val resourcePath: String = "solid"
    }

    data object Wood : HTMaterialType {
        override val defaultShape: HTShape? = null
        override val blockMaterial: Material = Material.WOOD
        override val resourcePath: String = "solid"
    }
}
