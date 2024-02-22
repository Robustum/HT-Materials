package io.github.hiiragi283.material

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.extention.getEntrypoints
import io.github.hiiragi283.api.extention.id
import io.github.hiiragi283.api.extention.isModLoaded
import io.github.hiiragi283.api.extention.runWhenOn
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.resource.HTResourcePackProvider
import io.github.hiiragi283.api.resource.HTRuntimeDataPack
import io.github.hiiragi283.api.shape.HTShape
import io.github.hiiragi283.api.shape.HTShapeKey
import io.github.hiiragi283.api.tag.GlobalTagEvent
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreen
import io.github.hiiragi283.material.dictionary.MaterialDictionaryScreenHandler
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.fabricmc.fabric.api.tag.TagRegistry
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.TooltipContext
import net.minecraft.data.server.RecipesProvider
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.tag.ItemTags
import net.minecraft.tag.Tag
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

    fun createShapeMap(): Map<HTShapeKey, HTShape> {
        // Create and register shape
        val helper = HTMaterialsAddon.ShapeHelper()
        addons.forEach { it.registerShape(helper) }
        return buildMap {
            helper.shapeKeys.forEach { key: HTShapeKey ->
                val idPath: String = helper.getIdPath(key)
                val tagPath: String = helper.getTagPath(key)
                putIfAbsent(key, HTShape(key, idPath, tagPath))
                HTMaterialsAPI.log("Shape: ${key.name} registered!")
            }
            HTMaterialsAPI.log("HTShapeRegistry initialized!")
        }
    }

    //    Initialize - HTMaterial    //

    fun createMaterialMap(): Map<HTMaterialKey, HTMaterial> {
        // create and register material
        val helper = HTMaterialsAddon.MaterialHelper()
        addons.forEach { it.registerMaterial(helper) }
        return buildMap {
            helper.materialKeys.forEach { key ->
                val composition: HTMaterialComposition = helper.getComposition(key)
                val flags: HTMaterialFlagSet = helper.getOrCreateFlagSet(key).build()
                val property: HTMaterialPropertyMap = helper.getOrCreatePropertyMap(key).build()
                val type: HTMaterialType = helper.getType(key)
                put(key, HTMaterial(key, composition, flags, property, type))
                HTMaterialsAPI.log("Material: $key registered!")
            }
            HTMaterialsAPI.log("HTMaterialRegistry initialized!")
        }
    }

    fun verifyMaterial() {
        HTMaterialsAPI.INSTANCE.materialRegistry().getValues().forEach { material ->
            material.forEachProperty { it.verify(material) }
            material.forEachFlag { it.verify(material) }
        }
    }

    //    Initialization    //

    /*private fun forEachContent(type: HTMaterialContent.Type, consumer: BiConsumer<HTMaterialContent, HTMaterialKey>) {
        for (material: HTMaterial in HTMaterialsAPI.INSTANCE.materialRegistry().getValues()) {
            for (content: HTMaterialContent in material.getContents(type)) {
                consumer.accept(content, material.key)
            }
        }
    }

    fun initContents() {
        forEachContent(HTMaterialContent.Type.BLOCK) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Blocks registered!")
        forEachContent(HTMaterialContent.Type.FLUID) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Fluids registered!")
        forEachContent(HTMaterialContent.Type.ITEM) { content, key -> content.init(key) }
        HTMaterialsAPI.log("All Material Items registered!")
    }*/

    //    Post Initialization    //

    fun postInitialize(envType: EnvType) {
        // Process postInit on HTMaterialContent
        /*forEachContent(HTMaterialContent.Type.BLOCK) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Blocks completed!")
        forEachContent(HTMaterialContent.Type.FLUID) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Fluids completed!")
        forEachContent(HTMaterialContent.Type.ITEM) { content, key -> content.postInit(key) }
        HTMaterialsAPI.log("All post-initialization for Material Items completed!")*/

        // Bind game objects to HTPart
        HTFluidManager.Builder().run {
            addons.forEach { it.bindFluidToPart(this) }
            HTMaterialsAPIImpl.fluidManager = build()
        }
        HTMaterialsAPI.log("HTFluidManager initialized!")
        HTPartManager.Builder().run {
            addons.forEach { it.bindItemToPart(this) }
            HTMaterialsAPIImpl.partManager = build()
        }
        HTMaterialsAPI.log("HTPartManager initialized!")

        // Register Events
        val id: Identifier = HTMaterialsAPI.id("before_default")
        ServerWorldEvents.LOAD.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onWorldLoaded)
        }
        GlobalTagEvent.ITEM.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onItemTags)
        }
        GlobalTagEvent.FLUID.run {
            addPhaseOrdering(id, Event.DEFAULT_PHASE)
            register(id, ::onFluidTags)
        }
        EnvType.CLIENT.runWhenOn {
            ScreenRegistry.register(MaterialDictionaryScreenHandler.TYPE, ::MaterialDictionaryScreen)
            ItemTooltipCallback.EVENT.register(::getTooltip)
            (MinecraftClient.getInstance().resourcePackManager as MutableResourcePackManager)
                .`ht_materials$addPackProvider`(HTResourcePackProvider.CLIENT)
            HTMaterialsAPI.log("Registered runtime resource pack!")
        }
        HTMaterialsAPI.log("Registered events!")

        HTRuntimeDataPack.addRecipe { exporter ->
            ShapelessRecipeJsonFactory.create(HTMaterialsAPI.INSTANCE.dictionaryItem())
                .input(Items.BOOK)
                .input(Items.IRON_INGOT)
                .criterion("hsa_book", RecipesProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter, HTMaterialsAPI.INSTANCE.dictionaryItem().id)
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
        HTMaterialsAPI.INSTANCE.partManager().getEntry(stack.item)?.let {
            it.materialKey.getMaterial().appendTooltip(it.shapeKey, stack, lines)
        }
        // Material tooltip on fluid container item
        FluidStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack))
            ?.iterable(Transaction.getCurrentUnsafe()?.openNested() ?: Transaction.openOuter())
            ?.map(StorageView<FluidVariant>::getResource)
            ?.map(FluidVariant::getFluid)
            ?.mapNotNull { HTMaterialsAPI.INSTANCE.fluidManager().getMaterialKey(it) }
            ?.forEach { it.getMaterial().appendTooltip(null, stack, lines) }
        // Tag tooltips (only dev)
        if (FabricLoader.getInstance().isDevelopmentEnvironment) {
            ItemTags.getTagGroup().getTagsFor(stack.item).forEach { id: Identifier ->
                lines.add(LiteralText(id.toString()))
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onWorldLoaded(server: MinecraftServer, world: ServerWorld) {
        // Reload Fluid Manager
        HTMaterialsAPIImpl.fluidManager = HTFluidManager.Builder().apply {
            // Register fluids from common tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                TagRegistry.fluid(materialKey.getCommonId()).values().forEach { fluid ->
                    add(materialKey, fluid)
                }
            }
        }.build()
        HTMaterialsAPI.log("Reloaded Fluid Manager!")

        // Reload Part Manager
        HTMaterialsAPIImpl.partManager = HTPartManager.Builder().apply {
            // Register items from part tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                HTMaterialsAPI.INSTANCE.shapeRegistry().getKeys().forEach { shapeKey: HTShapeKey ->
                    HTPart(materialKey, shapeKey).getPartTag().values().forEach { item ->
                        add(materialKey, shapeKey, item)
                    }
                }
            }
        }.build()
        HTMaterialsAPI.log("Reloaded Part Manager!")

        // registerRecipes()
    }

    private fun onItemTags(map: MutableMap<Identifier, Tag.Builder>) {
        // Convert tags into part format
        HashMap(map).forEach { (id: Identifier, builder: Tag.Builder) ->
            HTPart.fromId(id)?.getPartId()?.let {
                // copy builder to part id
                map[it] = builder
                // remove original id
                map.remove(id)
                HTMaterialsAPI.log("Migrated tag builder: $id -> $it")
            }
        }
        HTMaterialsAPI.log("Converted existing tags!")

        // Register Tags from HTPartManager
        HTMaterialsAPI.INSTANCE.partManager().getAllEntries().forEach { entry ->
            val (materialKey: HTMaterialKey, shapeKey: HTShapeKey, item: Item) = entry
            // Shape tag
            map.computeIfAbsent(shapeKey.getShapeId()) { Tag.Builder.create() }
                .add(item.id, HTMaterialsAPI.MOD_NAME)
            // Material tag
            map.computeIfAbsent(materialKey.getMaterialId()) { Tag.Builder.create() }
                .add(item.id, HTMaterialsAPI.MOD_NAME)
            // Part tag
            map.computeIfAbsent(entry.part.getPartId()) { Tag.Builder.create() }
                .add(item.id, HTMaterialsAPI.MOD_NAME)
        }
        HTMaterialsAPI.log("Registered Tags for HTPartManager's Entries!")
    }

    private fun onFluidTags(map: MutableMap<Identifier, Tag.Builder>) {
        // Register Tags from HTFluidManagerOld
        HTMaterialsAPI.INSTANCE.fluidManager().materialToFluidsMap.forEach { key: HTMaterialKey, fluid: Fluid ->
            map.computeIfAbsent(key.getCommonId()) { Tag.Builder.create() }
                .add(fluid.id, HTMaterialsAPI.MOD_NAME)
        }
    }
}
