package io.github.hiiragi283.material.api.material.property

import io.github.hiiragi283.material.api.material.HTMaterial
import net.minecraft.block.Block
import net.minecraft.tag.Tag
import java.util.function.Consumer

class HTMaterialProperties : MutableMap<HTPropertyKey<*>, HTMaterialProperty<*>> by hashMapOf() {

    fun <T : HTMaterialProperty<T>> getAs(key: HTPropertyKey<T>): T? = key.clazz.cast(this[key])

    private operator fun <T : HTMaterialProperty<T>> plusAssign(property: T) {
        this.add(property)
    }

    fun <T : HTMaterialProperty<T>> add(property: T) {
        this.putIfAbsent(property.key, property)
    }

    fun verify(material: HTMaterial) {
        this.values.forEach { it.verify(material) }
    }

    //    Util    //

    fun setFluid(consumer: Consumer<HTFluidProperty>) {
        this.add(HTFluidProperty().also(consumer::accept))
    }

    fun setGem(type: HTGemProperty.Type) {
        this.add(HTSolidProperty.createGem())
        this.add(HTGemProperty(type))
    }

    fun setMetal() {
        this.add(HTSolidProperty.createMetal())
        this.add(HTMetalProperty())
    }

    fun setSolid() {
        this.add(HTSolidProperty.createSolid())
    }

    fun setStone() {
        this.add(HTSolidProperty.createStone())
        this.add(HTMetalProperty())
    }

    fun setWood() {
        this.add(HTSolidProperty.createWood())
        this.add(HTMetalProperty())
    }

    fun setHarvestLevel(level: Int) {
        getAs(HTPropertyKey.SOLID)?.harvestLevel = level
    }

    fun setHarvestTool(tool: Tag.Identified<Block>) {
        getAs(HTPropertyKey.SOLID)?.harvestTool = tool
    }

    //    Any    //

    override fun toString(): String = this.keys.joinToString(separator = ", ")

}