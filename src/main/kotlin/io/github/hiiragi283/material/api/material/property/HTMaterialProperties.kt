package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial
import net.minecraft.block.Block
import net.minecraft.tag.TagKey
import java.util.function.Consumer

class HTMaterialProperties {

    private val map: LinkedHashMap<HTPropertyKey<*>, HTMaterialProperty<*>> = linkedMapOf()

    val values: Collection<HTMaterialProperty<*>>
        get() = map.values

    operator fun <T : HTMaterialProperty<T>> get(key: HTPropertyKey<T>): T? = key.clazz.cast(map[key])

    operator fun <T : HTMaterialProperty<T>> plusAssign(property: T) {
        map.putIfAbsent(property.key, property)
    }

    operator fun <T : HTMaterialProperty<T>> contains(key: HTPropertyKey<T>): Boolean = key in map

    fun verify(material: HTMaterial) {
        map.toList().sortedBy { it.first.name }.let(map::putAll)
        map.values.forEach { it.verify(material) }
    }

    //    Util    //

    fun setFluid(consumer: Consumer<HTFluidProperty>) {
        this += HTFluidProperty().also(consumer::accept)
    }

    fun setGem() {
        this += HTSolidProperty.createGem()
        this += HTGemProperty()
    }

    fun setMetal() {
        this += HTSolidProperty.createMetal()
        this += HTMetalProperty()
    }

    fun setSolid() {
        this += HTSolidProperty.createSolid()
    }

    fun setStone() {
        this += HTSolidProperty.createStone()
        this += HTMetalProperty()
    }

    fun setWood() {
        this += HTSolidProperty.createWood()
        this += HTMetalProperty()
    }

    fun setHarvestLevel(level: Int) {
        get(HTPropertyKey.SOLID)?.harvestLevel = level
    }

    fun setHarvestTool(tool: TagKey<Block>) {
        get(HTPropertyKey.SOLID)?.harvestTool = tool
    }

    //    Any    //

    override fun toString(): String = map.keys.joinToString(separator = ", ")

}