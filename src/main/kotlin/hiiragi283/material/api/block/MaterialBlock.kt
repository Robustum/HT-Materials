package hiiragi283.material.api.block

import hiiragi283.material.api.item.HiiragiBlockItem
import hiiragi283.material.api.item.MaterialBlockItem
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.material.MaterialItemProvider
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.util.SimpleColorProvider
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings

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

    @Environment(EnvType.CLIENT)
    override fun onRegisterClient() {
        ColorProviderRegistry.BLOCK.register(SimpleColorProvider(part), this)
        ColorProviderRegistry.ITEM.register(SimpleColorProvider(part), blockItem)
    }


}