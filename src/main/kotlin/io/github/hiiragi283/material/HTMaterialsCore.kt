package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extension.*
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialRegistry
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.tag.TagsBuildingEvent
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreen
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreenHandler
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.client.item.TooltipContext
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.ItemTags
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier

internal object HTMaterialsCore {
    lateinit var addons: Iterable<HTMaterialsAddon>

    fun initAddons() {
        addons = getEntrypoints<HTMaterialsAddon>(HTMaterialsAPI.MOD_ID)
            .filter { isModLoaded(it.modId) }
            .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })
        // Print sorted addons
        HTMaterialsAPI.LOGGER.info("HTMaterialsAddon collected!")
        HTMaterialsAPI.LOGGER.info("=== List ===")
        addons.forEach {
            HTMaterialsAPI.LOGGER.info("${it::class.qualifiedName} - Priority: ${it.priority}")
        }
        HTMaterialsAPI.LOGGER.info("============")
    }

    //    Initialize - HTShape    //

    fun initShapeRegistry() {
        // Create and register shape
        val helper = HTMaterialsAddon.ShapeHelper()
        addons.forEach { it.registerShape(helper) }
        HTMaterialsAPIImpl.shapeRegistry1 = HTShapeRegistry(
            buildMap {
                helper.shapeKeys.forEach { name: String ->
                    putIfAbsent(name, HTShape(name))
                    HTMaterialsAPI.LOGGER.debug("Shape: {} registered!", name)
                }
                HTMaterialsAPI.LOGGER.info("HTShapeRegistry initialized!")
            },
        )
    }

    //    Initialize - HTMaterial    //

    fun initMaterialRegistry() {
        // create and register material
        val helper = HTMaterialsAddon.MaterialHelper()
        addons.forEach { it.registerMaterial(helper) }
        HTMaterialsAPIImpl.materialRegistry1 = HTMaterialRegistry(
            buildMap {
                helper.materialKeys.forEach { key: HTMaterialKey ->
                    val composition: HTMaterialComposition = helper.getComposition(key)
                    val flags: HTMaterialFlagSet = helper.getOrCreateFlagSet(key).build()
                    val property: HTMaterialPropertyMap = helper.getOrCreatePropertyMap(key).build()
                    val type: HTMaterialType = helper.getType(key)
                    val material = HTMaterial(key, composition, flags, property, type)
                    put(key, material)
                    HTMaterialsAPI.LOGGER.debug("Material: {} registered!", key)
                }
            },
        )
        HTMaterialsAPI.LOGGER.info("HTMaterialRegistry initialized!")
    }

    fun verifyMaterial() {
        HTMaterialsAPI.INSTANCE.materialRegistry.values.forEach { material ->
            material.forEachProperty { it.verify(material) }
            material.forEachFlag { it.verify(material) }
        }
    }

    //    Post Initialization    //

    fun postInitialize(envType: EnvType) {
        // Register Events
        val id: Identifier = HTMaterialsAPI.id("before_default")
        TagsBuildingEvent.ITEM.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onItemTags)
        }
        TagsBuildingEvent.FLUID.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onFluidTags)
        };
        {
            ItemTooltipCallback.EVENT.register(::getTooltip)
            ScreenRegistry.register(MaterialDictionaryScreenHandler.TYPE, ::MaterialDictionaryScreen)
        }.runWhenOn(EnvType.CLIENT)
        HTMaterialsAPI.LOGGER.info("Registered events!")

        // Post initialize from addons
        addons.forEach { it.postInitialize(envType) }
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

    private fun onItemTags(handler: TagsBuildingEvent.Handler) {
        // Register Tags from HTPartManager
        HTMaterialsAPI.INSTANCE.partManager.itemToEntryMap.forEach { (item: Item, part: HTPart) ->
            val (materialKey: HTMaterialKey, shapeKey: HTShape) = part
            // Shape tag
            handler.builder(shapeKey.getShapeId()).add(item, HTMaterialsAPI.MOD_NAME)
            // Material tag
            handler.builder(materialKey.getMaterialId()).add(item, HTMaterialsAPI.MOD_NAME)
            // Part tag
            handler.builder(part.partId).add(item, HTMaterialsAPI.MOD_NAME)
        }
        HTMaterialsAPI.LOGGER.info("Registered Tags for HTPartManager's Entries!")
    }

    private fun onFluidTags(handler: TagsBuildingEvent.Handler) {
        // Register Tags from HTFluidManagerOld
        HTMaterialsAPI.INSTANCE.fluidManager.fluidToMaterialMap.forEach { (fluid: Fluid, key: HTMaterialKey) ->
            handler.builder(key.commonId).add(fluid, HTMaterialsAPI.MOD_NAME)
        }
    }
}
