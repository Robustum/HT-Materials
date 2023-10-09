package hiiragi283.material.api.item

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.api.material.MaterialItemConvertible
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.init.HiiragiRegistries
import hiiragi283.material.init.HiiragiTagRegistry
import hiiragi283.material.util.SimpleColorProvider
import hiiragi283.material.util.appendBefore
import hiiragi283.material.util.hiiragiId
import hiiragi283.material.util.simpleItemModel
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.model.ModelJsonBuilder

abstract class MaterialItem private constructor(final override val part: HiiragiPart) :
    HiiragiItem(part.material.itemSettings), MaterialItemConvertible {

    constructor(shape: HiiragiShape, material: HiiragiMaterial) : this(shape.getPart(material))

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
            getModel(resourcePack)
        )
        //Recipe
        addRecipe(resourcePack)
        //Tag
        HiiragiTagRegistry.getItemBuilder(part.material.tagKey).add(this)
        HiiragiTagRegistry.getItemBuilder(part.shape.tagKey).add(this)
        HiiragiTagRegistry.getItemBuilder(part.tagKey).add(this)
    }

    open fun getModel(resourcePack: RuntimeResourcePack): ModelJsonBuilder =
        simpleItemModel(hiiragiId("item/${part.shape.name}"))

    abstract fun addRecipe(resourcePack: RuntimeResourcePack)

    @Environment(EnvType.CLIENT)
    override fun onRegisterClient() {
        ColorProviderRegistry.ITEM.register(SimpleColorProvider.of(part) { it.material.color }, this)
    }

}