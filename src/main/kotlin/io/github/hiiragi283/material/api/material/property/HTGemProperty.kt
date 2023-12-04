package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.common.HTMaterialsCommon
import net.minecraft.util.Identifier

class HTGemProperty(val type: Type) : HTMaterialProperty<HTGemProperty> {

    override val key: HTPropertyKey<HTGemProperty> = HTPropertyKey.GEM

    override fun verify(material: HTMaterial) {
        material.modifyProperties { setGem(type) }
        if (material.hasProperty(HTPropertyKey.METAL)) {
            throw IllegalStateException("Material: has both Metal and Gem Property, which is not allowed!")
        }
    }

    enum class Type(val textureId: Identifier) {
        AMETHYST(HTMaterialsCommon.id("item/amethyst_gem")),
        COAL(HTMaterialsCommon.id("item/coal_gem")),
        CUBIC(HTMaterialsCommon.id("item/cubic_gem")),
        DIAMOND(HTMaterialsCommon.id("item/diamond_gem")),
        EMERALD(HTMaterialsCommon.id("item/emerald_gem")),
        LAPIS(HTMaterialsCommon.id("item/lapis_gem")),
        QUARTZ(Identifier("item/quartz")),
        RUBY(HTMaterialsCommon.id("item/ruby_gem"));
    }

}