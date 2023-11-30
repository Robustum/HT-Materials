package io.github.hiiragi283.material.api.shape

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag
import io.github.hiiragi283.material.common.HTMaterialsCommon
import io.github.hiiragi283.material.common.util.commonId
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.SimpleRegistry
import java.util.function.Predicate

class HTShape private constructor(
    val name: String,
    private val idFormat: String,
    private val forgeTag: String,
    private val fabricTag: String,
    private var generateBlock: Predicate<HTMaterial>,
    private var generateItem: Predicate<HTMaterial>
) {

    fun modifyBlockPredicate(predicate: Predicate<HTMaterial>) {
        check(canModify) { "Cannot modify Block Predicate after Initialization!!" }
        generateBlock = predicate
    }

    fun modifyItemPredicate(predicate: Predicate<HTMaterial>) {
        check(canModify) { "Cannot modify Item Predicate after Initialization!!" }
        generateItem = predicate
    }

    fun canGenerateBlock(material: HTMaterial): Boolean = generateBlock.test(material)

    fun canGenerateItem(material: HTMaterial): Boolean = generateItem.test(material)

    //    Translation    //

    private val translationKey: String = "ht_shape.$name"

    fun getTranslatedName(material: HTMaterial): String = I18n.translate(translationKey, material.getTranslatedName())

    fun getTranslatedText(material: HTMaterial): TranslatableText =
        TranslatableText(translationKey, material.getTranslatedName())

    //    Identifier    //

    fun getIdentifier(material: HTMaterial, namespace: String = HTMaterialsCommon.MOD_ID) =
        Identifier(namespace, idFormat.format(material.getName()))

    fun getForgeId(material: HTMaterial) = Identifier("c", forgeTag.format(material.getName()))

    fun getCommonId(material: HTMaterial) = Identifier("c", fabricTag.format(material.getName()))

    //    TagKey    //

    fun getForgeTag(material: HTMaterial): TagKey<Item> = TagKey.of(Registry.ITEM_KEY, getForgeId(material))

    fun getCommonTag(material: HTMaterial): TagKey<Item> = TagKey.of(Registry.ITEM_KEY, getCommonId(material))

    //    Any    //

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HTShape -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

    //    Builder    //

    class Builder(val name: String) {

        @JvmField
        var idFormat: String = ""

        @JvmField
        var commonTag: String = ""

        @JvmField
        var forgeTag: String = ""

        @JvmField
        var blockPredicate: Predicate<HTMaterial> = Predicate { false }

        @JvmField
        var itemPredicate: Predicate<HTMaterial> = Predicate { false }

        internal fun build() = HTShape(
            name,
            idFormat,
            forgeTag,
            commonTag,
            blockPredicate,
            itemPredicate
        )

    }

    companion object {

        internal var canModify: Boolean = true

        @JvmField
        val REGISTRY: SimpleRegistry<HTShape> = FabricRegistryBuilder.createSimple(
            HTShape::class.java,
            HTMaterialsCommon.id("shape")
        ).attribute(RegistryAttribute.SYNCED).buildAndRegister()

        @JvmStatic
        fun getShape(name: String): HTShape? = REGISTRY.get(commonId(name))

        //    Shapes    //

        @JvmStatic
        fun create(name: String, init: Builder.() -> Unit = {}): HTShape = Builder(name).apply(init).build().also {
            check(canModify) { "Cannot register shape after Initialization!!" }
            Registry.register(REGISTRY, commonId(name), it)
            HTMaterialsCommon.LOGGER.info("The Shape: $name registered!")
        }

        @JvmStatic
        fun createOre(name: String) = create("${name}_ore") {
            idFormat = "${name}_%s_ore"
            forgeTag = "ores/%s"
            commonTag = "%s_ores"
        }

        //    Block    //

        @JvmField
        val BLOCK = create("block") {
            idFormat = "%s_block"
            forgeTag = "storage_blocks/%s"
            commonTag = "%s_blocks"
            blockPredicate = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_BLOCk) }
        }

        @JvmField
        val FLUID = create("fluid") {
            idFormat = "%s"
            forgeTag = ""
            commonTag = ""
        }

        @JvmField
        val ORE = create("ore") {
            idFormat = "%s_ore"
            forgeTag = "ores/%s"
            commonTag = "%s_ores"
        }

        @JvmField
        val ORE_GRANITE = createOre("granite")

        @JvmField
        val ORE_DIORITE = createOre("diorite")

        @JvmField
        val ORE_ANDESITE = createOre("andesite")

        @JvmField
        val ORE_DEEPSLATE = createOre("deepslate")

        @JvmField
        val ORE_SAND = createOre("sand")

        @JvmField
        val ORE_RED_SAND = createOre("red_sand")

        @JvmField
        val ORE_GRAVEL = createOre("gravel")

        @JvmField
        val ORE_NETHERRACK = createOre("netherrack")

        @JvmField
        val ORE_END_STONE = createOre("end_stone")

        //    Item    //

        @JvmField
        val BLADE = create("blade") {
            idFormat = "%s_blade"
            forgeTag = "blades/%s"
            commonTag = "%s_blades"
        }

        @JvmField
        val BOLT = create("bolt") {
            idFormat = "%s_bolt"
            forgeTag = "bolts/%s"
            commonTag = "%s_bolts"
        }

        @JvmField
        val BUCKET = create("bucket") {
            idFormat = "%s_bucket"
            forgeTag = "buckets/%s"
            commonTag = "%s_buckets"
        }

        @JvmField
        val CRUSHED_DUST = create("crushed_dust") {
            idFormat = "%s_crushed_dust"
            forgeTag = "dusts/crushed/%s"
            commonTag = "%s_crushed_dusts"
        }

        @JvmField
        val CURVED_PLATE = create("curved_plate") {
            idFormat = "%s_curved_plate"
            forgeTag = "plates/curved/%s"
            commonTag = "%s_curved_plates"
        }

        @JvmField
        val DOUBLE_INGOT = create("double_ingot") {
            idFormat = "%s_double_ingot"
            forgeTag = "ingots/double/%s"
            commonTag = "%s_double_ingots"
        }

        @JvmField
        val DRILL_HEAD = create("drill_head") {
            idFormat = "%s_drill_head"
            forgeTag = "heads/drill/%s"
            commonTag = "%s_drill_heads"
        }

        @JvmField
        val DUST = create("dust") {
            idFormat = "%s_dust"
            forgeTag = "dusts/%s"
            commonTag = "%s_dusts"
            itemPredicate = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_DUST) }
        }

        @JvmField
        val GEAR = create("gear") {
            idFormat = "%s_gear"
            forgeTag = "gears/%s"
            commonTag = "%s_gears"
            itemPredicate = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_GEAR) }
        }

        @JvmField
        val GEM = create("gem") {
            idFormat = "%s_gem"
            forgeTag = "gems/%s"
            commonTag = "%s_gems"
            itemPredicate = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_GEM) }
        }

        @JvmField
        val HOT_INGOT = create("hot_ingot") {
            idFormat = "%s_hot_ingot"
            forgeTag = "ingots/hot/%s"
            commonTag = "%s_hot_ingots"
        }

        @JvmField
        val INGOT = create("ingot") {
            idFormat = "%s_ingot"
            forgeTag = "ingots/%s"
            commonTag = "%s_ingots"
            itemPredicate = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_INGOT) }
        }

        @JvmField
        val LARGE_PLATE = create("large_plate") {
            idFormat = "%s_large_plate"
            forgeTag = "plates/large/%s"
            commonTag = "%s_large_plates"
        }

        @JvmField
        val NUGGET = create("nugget") {
            idFormat = "%s_nugget"
            forgeTag = "nuggets/%s"
            commonTag = "%s_nuggets"
            itemPredicate = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_NUGGET) }
        }

        @JvmField
        val PLATE = create("plate") {
            idFormat = "%s_plate"
            forgeTag = "plates/%s"
            commonTag = "%s_plates"
            itemPredicate = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_PLATE) }
        }

        @JvmField
        val RAW_ORE = create("raw_ore") {
            idFormat = "raw_%s"
            forgeTag = "raw_materials/%s"
            commonTag = "raw_%s_ores"
        }

        @JvmField
        val RAW_BLOCK = create("raw_block") {
            idFormat = "raw_%s_block"
            forgeTag = "storage_blocks/raw_%s"
            commonTag = "raw_%s_blocks"
        }

        @JvmField
        val RING = create("ring") {
            idFormat = "%s_ring"
            forgeTag = "rings/%s"
            commonTag = "%s_rings"
        }

        @JvmField
        val ROD = create("rod") {
            idFormat = "%s_rod"
            forgeTag = "rods/%s"
            commonTag = "%s_rods"
            itemPredicate = Predicate { it.hasFlag(HTMaterialFlag.GENERATE_ROD) }
        }

        @JvmField
        val ROTOR = create("rotor") {
            idFormat = "%s_rotor"
            forgeTag = "rotors/%s"
            commonTag = "%s_rotors"
        }

        @JvmField
        val TINY_DUST = create("tiny_dust") {
            idFormat = "%s_tiny_dust"
            forgeTag = "dusts/tiny/%s"
            commonTag = "%s_tiny_dusts"
        }

        @JvmField
        val WIRE = create("wire") {
            idFormat = "%s_wire"
            forgeTag = "wires/%s"
            commonTag = "%s_wires"
        }

    }

}