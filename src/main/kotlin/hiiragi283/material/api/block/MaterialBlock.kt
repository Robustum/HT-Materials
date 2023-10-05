package hiiragi283.material.api.block

import hiiragi283.material.init.HiiragiTagRegistry
import hiiragi283.material.api.item.HiiragiBlockItem
import hiiragi283.material.api.item.MaterialBlockItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.material.MaterialItemProvider
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.init.HiiragiRegistries
import hiiragi283.material.util.SimpleColorProvider
import hiiragi283.material.util.appendBefore
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.server.BlockLootTableGenerator
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder

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
                getBlockModel(resourcePack)
            )
        )
        //Item Model
        resourcePack.addModel(
            getIdentifier().appendBefore("item/"),
            getItemModel()
        )
        //LootTable
        resourcePack.addLootTable(
            this.getLootTableId(),
            BlockLootTableGenerator.drops(this).build()
        )
        //Recipe
        addRecipe(resourcePack)
        //Tag - Mining Tool & Level
        part.material.miningProperty.register(this)
        //Tag - Material
        HiiragiTagRegistry.getItemBuilder(part.material.tagKey).add(blockItem)
        HiiragiTagRegistry.getItemBuilder(part.shape.tagKey).add(blockItem)
        HiiragiTagRegistry.getItemBuilder(part.tagKey).add(blockItem)
    }

    abstract fun getBlockModel(resourcePack: RuntimeResourcePack): Identifier

    abstract fun getItemModel(): ModelJsonBuilder

    abstract fun addRecipe(resourcePack: RuntimeResourcePack)

    @Environment(EnvType.CLIENT)
    override fun onRegisterClient() {
        val colorProvider: SimpleColorProvider = SimpleColorProvider.of(part) { it.material.color }
        ColorProviderRegistry.BLOCK.register(colorProvider, this)
        ColorProviderRegistry.ITEM.register(colorProvider, this)
    }

}