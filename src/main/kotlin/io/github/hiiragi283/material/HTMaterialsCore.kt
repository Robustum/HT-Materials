package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.extension.*
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreen
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreenHandler
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.client.item.TooltipContext
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.tag.ItemTags
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier

internal object HTMaterialsCore {
    //    Post Initialization    //

    fun postInitialize(envType: EnvType) {
        // Register Events
        EnvType.CLIENT.runWhenOn {
            ItemTooltipCallback.EVENT.register(::getTooltip)
            ScreenRegistry.register(MaterialDictionaryScreenHandler.TYPE, ::MaterialDictionaryScreen)
        }
        HTMaterialsAPI.LOGGER.info("Registered events!")

        // Post initialize from addons
        HTMaterialsAPI.INSTANCE.forEachAddon { it.postInitialize(envType) }
        HTMaterialsAPI.LOGGER.info("Post-initialize completed!")
    }

    //    Event    //

    @Suppress("UnstableApiUsage", "DEPRECATION", "UNUSED_PARAMETER")
    private fun getTooltip(stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {
        if (stack.isEmpty) return
        // Part tooltip on item
        HTMaterialsAPI.INSTANCE.partManager[stack.item]?.run {
            material.appendTooltip(shape, stack, lines)
        }
        // Material tooltip on fluid container item
        FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
            ?.iterable(Transaction.getCurrentUnsafe()?.openNested() ?: Transaction.openOuter())
            ?.map(StorageView<FluidVariant>::getResource)
            ?.map(FluidVariant::getFluid)
            ?.mapNotNull(Fluid::materialKey)
            ?.map(HTMaterialKey::material)
            ?.forEach { it.appendTooltip(null, stack, lines) }
        // Tag tooltips (only dev)
        onDev {
            ItemTags.getTagGroup().getTagsFor(stack.item).forEach { id: Identifier ->
                lines.add(LiteralText(id.toString()))
            }
        }
    }
}