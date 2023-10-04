package hiiragi283.material.api.item

import hiiragi283.material.HiiragiTagRegistry
import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.material.MaterialItemProvider
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.reigstry.HiiragiRegistries
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.util.SimpleColorProvider
import hiiragi283.material.util.appendBefore
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder

abstract class MaterialItem(
    final override val part: HiiragiPart,
    settings: FabricItemSettings
) : HiiragiItem(settings), MaterialItemProvider {

    constructor(
        shape: HiiragiShape,
        material: HiiragiMaterial,
        settings: FabricItemSettings
    ) : this(shape.getPart(material), settings)

    //    General    //

    override fun getName(stack: ItemStack): Text = part.getText()

    //    Entry    //

    fun register() {
        HiiragiRegistries.ITEM.register(part.tagPath, this)
    }

    override fun addResources(resourcePack: RuntimeResourcePack) {
        //Model
        resourcePack.addModel(
            getIdentifier().appendBefore("item/"),
            getModel(getIdentifier())
        )
        //Recipe
        addRecipe(resourcePack, getIdentifier())
        //Tag
        HiiragiTagRegistry.getItemBuilder(part.material.tagKey).add(this)
        HiiragiTagRegistry.getItemBuilder(part.shape.tagKey).add(this)
        HiiragiTagRegistry.getItemBuilder(part.tagKey).add(this)
    }

    abstract fun getModel(identifier: Identifier): ModelJsonBuilder

    abstract fun addRecipe(resourcePack: RuntimeResourcePack, identifier: Identifier)

    @Environment(EnvType.CLIENT)
    override fun onRegisterClient() {
        ColorProviderRegistry.ITEM.register(SimpleColorProvider.of(part) { it.material.color }, this)
    }

}