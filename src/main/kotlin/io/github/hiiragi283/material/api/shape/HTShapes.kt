package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag

object HTShapes {

    internal var canModify: Boolean = true

    private val map: MutableMap<String, HTShape> = mutableMapOf()

    @JvmField
    val REGISTRY: Collection<HTShape> = map.values

    @JvmStatic
    fun getShape(name: String): HTShape? = map[name]

    @JvmStatic
    fun register(shape: HTShape) {
        map.putIfAbsent(shape.name, shape)
    }

    //    Block    //

    @JvmField
    val BLOCK: HTShape = object : HTShape {

        override val name: String = "block"

        override fun canGenerateBlock(material: HTMaterial): Boolean = material.hasFlag(HTMaterialFlag.GENERATE_BLOCk)

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = "${material.getName()}_block"

        override fun getForgePath(material: HTMaterial): String = "storage_blocks/${material.getName()}"

        override fun getCommonPath(material: HTMaterial): String = "${material.getName()}_blocks"

    }.register()

    @JvmField
    val ORE: HTShape = object : HTShape {

        override val name: String = "ore"

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = "${material.getName()}_ore"

        override fun getForgePath(material: HTMaterial): String = "ores/${material.getName()}"

        override fun getCommonPath(material: HTMaterial): String = "${material.getName()}_ores"

    }.register()

    @JvmField
    val RAW_BLOCK: HTShape = object : HTShape {

        override val name: String = "raw_block"

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = "raw_${material.getName()}_block"

        override fun getForgePath(material: HTMaterial): String = "storage_blocks/raw_${material.getName()}"

        override fun getCommonPath(material: HTMaterial): String = "raw_${material.getName()}_blocks"

    }.register()

    //    Fluid    //

    //DO NOT call register()!!
    @JvmField
    val FLUID = object : HTShape {

        override val name: String = "fluid"

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = material.getName()

        override fun getForgePath(material: HTMaterial): String {
            throw UnsupportedOperationException()
        }

        override fun getCommonPath(material: HTMaterial): String {
            throw UnsupportedOperationException()
        }

    }

    //    Item    //

    @JvmField
    val BLADE: HTShape = HTSimpleShape.Item("blade")

    @JvmField
    val BOLT: HTShape = HTSimpleShape.Item("bolt")

    @JvmField
    val BUCKET: HTShape = HTSimpleShape.Item("bucket")

    @JvmField
    val CRUSHED_DUST: HTShape = HTSimpleShape.Item("crushed_dust")

    @JvmField
    val CURVED_PLATE: HTShape = HTSimpleShape.Item("curved_plate")

    @JvmField
    val DOUBLE_INGOT: HTShape = HTSimpleShape.Item("double_ingot")

    @JvmField
    val DRILL_HEAD: HTShape = HTSimpleShape.Item("drill_head")

    @JvmField
    val DUST: HTShape = HTSimpleShape.Item("dust")

    @JvmField
    val GEAR: HTShape = HTSimpleShape.Item("gear")

    @JvmField
    val GEM: HTShape = HTSimpleShape.Item("gem")

    @JvmField
    val HOT_INGOT: HTShape = HTSimpleShape.Item("hot_ingot")

    @JvmField
    val INGOT: HTShape = HTSimpleShape.Item("ingot")

    @JvmField
    val LARGE_PLATE: HTShape = HTSimpleShape.Item("large_plate")

    @JvmField
    val NUGGET: HTShape = HTSimpleShape.Item("nugget")

    @JvmField
    val PLATE: HTShape = HTSimpleShape.Item("plate")

    @JvmField
    val RING: HTShape = HTSimpleShape.Item("ring")

    @JvmField
    val RAW_ORE: HTShape = object : HTShape {

        override val name: String = "raw_ore"

        override fun canGenerateBlock(material: HTMaterial): Boolean = false

        override fun canGenerateItem(material: HTMaterial): Boolean = false

        override fun getIdPath(material: HTMaterial): String = "raw_${material.getName()}_block"

        override fun getForgePath(material: HTMaterial): String = "raw_materials/${material.getName()}"

        override fun getCommonPath(material: HTMaterial): String = "raw_${material.getName()}_ores"

    }.register()

    @JvmField
    val ROD: HTShape = HTSimpleShape.Item("rod")

    @JvmField
    val ROTOR: HTShape = HTSimpleShape.Item("rotor")

    @JvmField
    val TINY_DUST: HTShape = HTSimpleShape.Item("tiny_dust")

    @JvmField
    val WIRE: HTShape = HTSimpleShape.Item("wire")

}