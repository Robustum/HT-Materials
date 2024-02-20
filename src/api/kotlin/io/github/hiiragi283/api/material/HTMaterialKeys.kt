package io.github.hiiragi283.api.material

object HTMaterialKeys {
    //    1st Period    //

    @JvmField
    val HYDROGEN = HTMaterialKey("hydrogen")

    @JvmField
    val HELIUM = HTMaterialKey("helium")

    //    2nd Period    //

    @JvmField
    val LITHIUM = HTMaterialKey("lithium")

    @JvmField
    val BERYLLIUM = HTMaterialKey("beryllium")

    @JvmField
    val CARBON = HTMaterialKey("carbon")

    @JvmField
    val NITROGEN = HTMaterialKey("nitrogen")

    @JvmField
    val OXYGEN = HTMaterialKey("oxygen")

    @JvmField
    val FLUORINE = HTMaterialKey("fluorine")

    //    3rd Period    //

    @JvmField
    val SODIUM = HTMaterialKey("sodium")

    @JvmField
    val MAGNESIUM = HTMaterialKey("magnesium")

    @JvmField
    val ALUMINUM = HTMaterialKey("aluminum")

    @JvmField
    val SILICON = HTMaterialKey("silicon")

    @JvmField
    val PHOSPHORUS = HTMaterialKey("phosphorus")

    @JvmField
    val SULFUR = HTMaterialKey("sulfur")

    @JvmField
    val CHLORINE = HTMaterialKey("chlorine")

    //    4th Period    //

    @JvmField
    val POTASSIUM = HTMaterialKey("potassium")

    @JvmField
    val CALCIUM = HTMaterialKey("calcium")

    @JvmField
    val TITANIUM = HTMaterialKey("titanium")

    @JvmField
    val CHROMIUM = HTMaterialKey("chromium")

    @JvmField
    val MANGANESE = HTMaterialKey("manganese")

    @JvmField
    val IRON = HTMaterialKey("iron")

    @JvmField
    val COBALT = HTMaterialKey("cobalt")

    @JvmField
    val NICKEL = HTMaterialKey("nickel")

    @JvmField
    val COPPER = HTMaterialKey("copper")

    @JvmField
    val ZINC = HTMaterialKey("zinc")

    //    5th Period    //

    @JvmField
    val SILVER = HTMaterialKey("silver")

    @JvmField
    val TIN = HTMaterialKey("tin")

    //    6th Period    //

    @JvmField
    val TUNGSTEN = HTMaterialKey("tungsten")

    @JvmField
    val IRIDIUM = HTMaterialKey("iridium")

    @JvmField
    val PLATINUM = HTMaterialKey("platinum")

    @JvmField
    val GOLD = HTMaterialKey("gold")

    @JvmField
    val MERCURY = HTMaterialKey("mercury")

    @JvmField
    val LEAD = HTMaterialKey("lead")

    //    7th Period    //

    @JvmField
    val URANIUM = HTMaterialKey("uranium")

    @JvmField
    val PLUTONIUM = HTMaterialKey("plutonium")

    //    Atomic Groups    //

    // CO3
    @JvmField
    val CARBONATE = arrayOf(CARBON to 1, OXYGEN to 3)

    // NO3
    @JvmField
    val NITRATE = arrayOf(NITROGEN to 1, OXYGEN to 3)

    // Al2O3
    @JvmField
    val ALUMINUM_OXIDE = arrayOf(ALUMINUM to 2, OXYGEN to 3)

    // SiO2
    @JvmField
    val SILICON_OXIDE = arrayOf(SILICON to 1, OXYGEN to 2)

    //    Vanilla - Fluids    //

    @JvmField
    val WATER = HTMaterialKey("water")

    @JvmField
    val LAVA = HTMaterialKey("lava")

    //    Vanilla - Gems    //

    @JvmField
    val AMETHYST = HTMaterialKey("amethyst")

    @JvmField
    val DIAMOND = HTMaterialKey("diamond")

    @JvmField
    val ENDER_PEARL = HTMaterialKey("ender_pearl")

    @JvmField
    val EMERALD = HTMaterialKey("emerald")

    @JvmField
    val FLINT = HTMaterialKey("flint")

    @JvmField
    val LAPIS = HTMaterialKey("lapis")

    @JvmField
    val QUARTZ = HTMaterialKey("quartz")

    //    Vanilla - Metals    //

    @JvmField
    val NETHERITE = HTMaterialKey("netherite")

    //    Vanilla - Solids    //

    @JvmField
    val BRICK = HTMaterialKey("brick")

    @JvmField
    val CHARCOAL = HTMaterialKey("charcoal")

    @JvmField
    val CLAY = HTMaterialKey("clay")

    @JvmField
    val COAL = HTMaterialKey("coal")

    @JvmField
    val GLASS = HTMaterialKey("glass")

    @JvmField
    val GLOWSTONE = HTMaterialKey("glowstone")

    @JvmField
    val NETHER_BRICK = HTMaterialKey("nether_brick")

    @JvmField
    val REDSTONE = HTMaterialKey("redstone")

    //    Vanilla - Stones    //

    @JvmField
    val STONE = HTMaterialKey("stone")

    @JvmField
    val GRANITE = HTMaterialKey("granite")

    @JvmField
    val DIORITE = HTMaterialKey("diorite")

    @JvmField
    val ANDESITE = HTMaterialKey("andesite")

    @JvmField
    val DEEPSLATE = HTMaterialKey("deepslate")

    @JvmField
    val CALCITE = HTMaterialKey("calcite")

    @JvmField
    val TUFF = HTMaterialKey("tuff")

    @JvmField
    val OBSIDIAN = HTMaterialKey("obsidian")

    @JvmField
    val NETHERRACK = HTMaterialKey("netherrack")

    @JvmField
    val BASALT = HTMaterialKey("basalt")

    @JvmField
    val END_STONE = HTMaterialKey("end_stone")

    //    Vanilla - Woods    //

    @JvmField
    val WOOD = HTMaterialKey("wood")

    //    Common - Fluids    //

    //    Common - Gems    //

    @JvmField
    val CINNABAR = HTMaterialKey("cinnabar")

    @JvmField
    val COKE = HTMaterialKey("coke")

    @JvmField
    val OLIVINE = HTMaterialKey("olivine")

    @JvmField
    val PERIDOT = HTMaterialKey("peridot")

    @JvmField
    val RUBY = HTMaterialKey("ruby")

    @JvmField
    val SALT = HTMaterialKey("salt")

    @JvmField
    val SAPPHIRE = HTMaterialKey("sapphire")

    //    Common - Metal    //

    @JvmField
    val BRASS = HTMaterialKey("brass")

    @JvmField
    val BRONZE = HTMaterialKey("bronze")

    @JvmField
    val ELECTRUM = HTMaterialKey("electrum")

    @JvmField
    val INVAR = HTMaterialKey("invar")

    @JvmField
    val STAINLESS_STEEL = HTMaterialKey("stainless_steel")

    @JvmField
    val STEEl = HTMaterialKey("steel")

    //    Common - Solids    //

    @JvmField
    val ASHES = HTMaterialKey("ashes")

    @JvmField
    val BAUXITE = HTMaterialKey("bauxite")

    @JvmField
    val RUBBER = HTMaterialKey("rubber")

    //    Common - Stones    //

    @JvmField
    val MARBLE = HTMaterialKey("marble")

    //    Common - Woods    //
}
