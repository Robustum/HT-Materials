package io.github.hiiragi283.material

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object HTMaterialsCommon : ModInitializer {

    const val MOD_ID: String = "ht_materials"
    const val MOD_NAME: String = "HT Materials"

    private val LOGGER: Logger = LogManager.getLogger(MOD_NAME)

    @JvmField
    val ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.create(id("material")).icon { ICON.defaultStack }.build()

    @JvmField
    val ICON: Item = Item(FabricItemSettings().group(ITEM_GROUP).rarity(Rarity.EPIC))

    override fun onInitialize() {
        //Register Shapes
        HTMaterialsCore.registerShape()
        HTMaterialsCore.modifyShapePredicate()
        HTMaterialsCore.createShape()
        //Register Materials
        HTMaterialsCore.registerMaterialKey()
        HTMaterialsCore.modifyMaterialContent()
        HTMaterialsCore.modifyMaterialProperty()
        HTMaterialsCore.modifyMaterialFlag()
        HTMaterialsCore.modifyMaterialColor()
        HTMaterialsCore.modifyMaterialFormula()
        HTMaterialsCore.modifyMaterialMolar()
        HTMaterialsCore.createMaterial()
        HTMaterialsCore.verifyMaterial()
        //Initialize Game Objects
        HTMaterialsCore.createContent(Registry.BLOCK_KEY)
        LOGGER.info("All Material Blocks Registered!")
        HTMaterialsCore.registerMaterialFluids()
        LOGGER.info("All Material Fluids Registered!")
        Registry.register(Registry.ITEM, id("icon"), ICON)
        HTMaterialsCore.createContent(Registry.ITEM_KEY)
        LOGGER.info("All Material Items Registered!")
    }

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)

}