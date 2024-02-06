package io.github.hiiragi283.api.material

sealed interface HTMaterialType {
    data object Undefined : HTMaterialType

    enum class Gem : HTMaterialType {
        AMETHYST,
        COAL,
        CUBIC,
        DIAMOND,
        EMERALD,
        LAPIS,
        QUARTZ,
        RUBY,
    }

    data object Metal : HTMaterialType

    data object Stone : HTMaterialType

    data object Wood : HTMaterialType
}
