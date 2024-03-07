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
import io.github.hiiragi283.api.resource.HTResourcePackProvider
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.shape.HTShapeRegistry
import io.github.hiiragi283.api.tag.GlobalTagEvent
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreen
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreenHandler
import io.github.hiiragi283.material.impl.HTFluidRegistryImpl
import io.github.hiiragi283.material.impl.HTPartRegistryImpl
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.TooltipContext
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.ItemTags
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier

internal object HTMaterialsCore {
    private lateinit var addons: Iterable<HTMaterialsAddon>

    fun initAddons() {
        addons = getEntrypoints<HTMaterialsAddon>(HTMaterialsAPI.MOD_ID)
            .filter { isModLoaded(it.modId) }
            .sortedWith(compareBy(HTMaterialsAddon::priority).thenBy { it.javaClass.name })
        // Print sorted addons
        HTMaterialsAPI.log("HTMaterialsAddon collected!")
        HTMaterialsAPI.log("=== List ===")
        addons.forEach {
            HTMaterialsAPI.log("${it::class.qualifiedName} - Priority: ${it.priority}")
        }
        HTMaterialsAPI.log("============")
    }

    //    Initialize - HTShape    //

    fun initShapeRegistry() {
        // Create and register shape
        val helper = HTMaterialsAddon.ShapeHelper()
        addons.forEach { it.registerShape(helper) }
        HTMaterialsAPIImpl.shapeRegistry = HTShapeRegistry(
            buildMap {
                helper.shapeKeys.forEach { key: HTShapeKey ->
                    val idPath: String = helper.getIdPath(key)
                    val tagPath: String = helper.getTagPath(key)
                    putIfAbsent(key, HTShape(key, idPath, tagPath))
                    HTMaterialsAPI.log("Shape: ${key.name} registered!")
                }
                HTMaterialsAPI.log("HTShapeRegistry initialized!")
            },
        )
    }

    //    Initialize - HTMaterial    //

    fun initMaterialRegistry() {
        // create and register material
        val helper = HTMaterialsAddon.MaterialHelper()
        addons.forEach { it.registerMaterial(helper) }
        HTMaterialsAPIImpl.materialRegistry = HTMaterialRegistry(
            buildMap {
                helper.materialKeys.forEach { key: HTMaterialKey ->
                    val composition: HTMaterialComposition = helper.getComposition(key)
                    val flags: HTMaterialFlagSet = helper.getOrCreateFlagSet(key).build()
                    val property: HTMaterialPropertyMap = helper.getOrCreatePropertyMap(key).build()
                    val type: HTMaterialType = helper.getType(key)
                    val material = HTMaterial(key, composition, flags, property, type)
                    put(key, material)
                    HTMaterialsAPI.log("Material: $key registered!")
                }
            },
        )
        HTMaterialsAPI.log("HTMaterialRegistry initialized!")
        // Init HTPart cache
        HTPart.initCache(helper)
    }

    fun verifyMaterial() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getValues().forEach { material ->
            material.forEachProperty { it.verify(material) }
            material.forEachFlag { it.verify(material) }
        }
    }

    //    Post Initialization    //

    fun postInitialize(envType: EnvType) {
        // Bind Items/Fluids to materials
        addons.forEach {
            it.registerFluidRegistry(HTFluidRegistryImpl)
            it.registerPartRegistry(HTPartRegistryImpl)
        }
        // Register Events
        val id: Identifier = HTMaterialsAPI.id("before_default")
        /*ServerWorldEvents.LOAD.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onWorldLoaded)
        }*/
        GlobalTagEvent.ITEM.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onItemTags)
        }
        GlobalTagEvent.FLUID.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onFluidTags)
        }
        EnvType.CLIENT.runWhenOn {
            ItemTooltipCallback.EVENT.register(::getTooltip)
        }
        HTMaterialsAPI.log("Registered events!")

        EnvType.CLIENT.runWhenOn {
            // Register runtime resource pack on client-side
            ScreenRegistry.register(MaterialDictionaryScreenHandler.TYPE, ::MaterialDictionaryScreen)
            (MinecraftClient.getInstance().resourcePackManager as MutableResourcePackManager)
                .`ht_materials$addPackProvider`(HTResourcePackProvider.CLIENT)
            HTMaterialsAPI.log("Registered runtime resource pack!")
        }

        // Post initialize from addons
        addons.forEach { it.postInitialize(envType) }
        HTMaterialsAPI.log("Post-initialize completed!")
    }

    //    Event    //

    @Suppress("UnstableApiUsage", "DEPRECATION", "UNUSED_PARAMETER")
    private fun getTooltip(stack: ItemStack, context: TooltipContext, lines: MutableList<Text>) {
        if (stack.isEmpty) return
        // Part tooltip on item
        HTMaterialsAPI.INSTANCE.partRegistry().get(stack.item)?.run {
            getMaterial().appendTooltip(shapeKey, stack, lines)
        }
        // Material tooltip on fluid container item
        FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
            ?.iterable(Transaction.getCurrentUnsafe()?.openNested() ?: Transaction.openOuter())
            ?.map(StorageView<FluidVariant>::getResource)
            ?.map(FluidVariant::getFluid)
            ?.mapNotNull(Fluid::materialKey)
            ?.map(HTMaterialKey::getMaterial)
            ?.forEach { it.appendTooltip(null, stack, lines) }
        // Tag tooltips (only dev)
        onDev {
            ItemTags.getTagGroup().getTagsFor(stack.item).forEach { id: Identifier ->
                lines.add(LiteralText(id.toString()))
            }
        }
    }

    /*@Suppress("UNUSED_PARAMETER")
    private fun onWorldLoaded(server: MinecraftServer, world: ServerWorld) {
        // Reload Fluid Manager
        HTFluidManager.Builder().apply {
            // Register fluids from Registry
            // addons.forEach { it.bindFluidToPart(this) }
            // Register fluids from common tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                TagRegistry.fluid(materialKey.getCommonId()).values().forEach { fluid ->
                    add(materialKey, fluid)
                }
            }
        }.build()
        HTMaterialsAPI.log("Fluid Manager initialized!")

        // Reload Part Manager
        HTPartManager.Builder().apply {
            // Register items from Registry
            // addons.forEach { it.bindItemToPart(this) }
            // Register items from part tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                HTMaterialsAPI.INSTANCE.shapeRegistry().getKeys().forEach { shapeKey: HTShapeKey ->
                    HTPart(materialKey, shapeKey).getPartTag().values().forEach { item ->
                        add(materialKey, shapeKey, item)
                    }
                }
            }
        }.build()
        HTMaterialsAPI.log("Part Manager initialized!")
    }*/

    private fun onItemTags(handler: GlobalTagEvent.Handler) {
        // Register Tags from HTPartManager
        HTMaterialsAPI.INSTANCE.partRegistry().allEntries.forEach { (item: Item, part: HTPart) ->
            val (materialKey: HTMaterialKey, shapeKey: HTShapeKey) = part
            // Shape tag
            handler.builder(shapeKey.getShapeId()).add(item, HTMaterialsAPI.MOD_NAME)
            // Material tag
            handler.builder(materialKey.getMaterialId()).add(item, HTMaterialsAPI.MOD_NAME)
            // Part tag
            handler.builder(part.getPartId()).add(item, HTMaterialsAPI.MOD_NAME)
        }
        HTMaterialsAPI.log("Registered Tags for HTPartManager's Entries!")
    }

    private fun onFluidTags(handler: GlobalTagEvent.Handler) {
        // Register Tags from HTFluidManagerOld
        HTMaterialsAPI.INSTANCE.fluidRegistry().allEntries.forEach { (fluid: Fluid, key: HTMaterialKey) ->
            handler.builder(key.getCommonId()).add(fluid, HTMaterialsAPI.MOD_NAME)
        }
    }
}