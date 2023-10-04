package hiiragi283.material.api.block

import hiiragi283.material.HiiragiTagRegistry
import hiiragi283.material.api.item.HiiragiBlockItem
import hiiragi283.material.api.item.MaterialBlockItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.material.MaterialItemProvider
import hiiragi283.material.api.material.MiningProperty
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.reigstry.HiiragiRegistries
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.util.SimpleColorProvider
import hiiragi283.material.util.appendBefore
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.server.BlockLootTableGenerator
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder
import pers.solid.brrp.v1.tag.IdentifiedTagBuilder

abstract class MaterialBlock(
    override val part: HiiragiPart,
    blockSettings: FabricBlockSettings,
    itemSettings: FabricItemSettings
) : HiiragiBlock(blockSettings), MaterialItemProvider {

    constructor(
        shape: HiiragiShape,
        material: HiiragiMaterial,
        blockSettings: FabricBlockSettings,
        itemSettings: FabricItemSettings
    ) : this(shape.getPart(material), blockSettings, itemSettings)

    //    Entry    //

    override val blockItem: HiiragiBlockItem? = MaterialBlockItem(this, itemSettings)

    fun register() {
        HiiragiRegistries.BLOCK.register(part.tagPath, this)
    }

    override fun addResources(resourcePack: RuntimeResourcePack) {
        //BlockState
        resourcePack.addBlockState(
            getIdentifier(),
            BlockStateModelGenerator.createSingletonBlockState(
                this,
                getBlockModel(resourcePack, getIdentifier())
            )
        )
        //Item Model
        resourcePack.addModel(
            getIdentifier().appendBefore("item/"),
            getItemModel(getIdentifier())
        )
        //LootTable
        resourcePack.addLootTable(
            this.getLootTableId(),
            BlockLootTableGenerator.drops(this).build()
        )
        //Recipe
        addRecipe(resourcePack, getIdentifier())
        //Tag - Mining Tool
        val miningProperty: MiningProperty = part.material.miningProperty
        miningProperty.tool
            .map(HiiragiTagRegistry::getBlockBuilder)
            .forEach { tagBuilder: IdentifiedTagBuilder<Block> -> tagBuilder.add(this) }
        //Tag - Mining Level
        miningProperty.levelTag?.let { HiiragiTagRegistry.getBlockBuilder(it).add(this) }
        //Tag - Material
        HiiragiTagRegistry.getItemBuilder(part.material.tagKey).add(blockItem)
        HiiragiTagRegistry.getItemBuilder(part.shape.tagKey).add(blockItem)
        HiiragiTagRegistry.getItemBuilder(part.tagKey).add(blockItem)
    }

    abstract fun getBlockModel(resourcePack: RuntimeResourcePack, identifier: Identifier): Identifier

    abstract fun getItemModel(identifier: Identifier): ModelJsonBuilder

    abstract fun addRecipe(resourcePack: RuntimeResourcePack, identifier: Identifier)

    @Environment(EnvType.CLIENT)
    override fun onRegisterClient() {
        val colorProvider: SimpleColorProvider = SimpleColorProvider.of(part) { it.material.color }
        ColorProviderRegistry.BLOCK.register(colorProvider, this)
        ColorProviderRegistry.ITEM.register(colorProvider, this)
    }

}