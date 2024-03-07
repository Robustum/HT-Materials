package io.github.hiiragi283.material.impl

import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.fluid.HTFluidRegistry
import io.github.hiiragi283.api.material.HTMaterialKey
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.fluid.Fluid
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

object HTFluidRegistryImpl : HTFluidRegistry, SimpleSynchronousResourceReloadListener {
    private val registeredFluidMap: MutableMap<Fluid, HTMaterialKey?> = hashMapOf()
    private val registeredTagMap: MutableMap<Tag<Fluid>, HTMaterialKey?> = hashMapOf()
    private val fluidToMaterialMap: MutableMap<Fluid, HTMaterialKey> = linkedMapOf()
    private val materialToFluidsMap: Multimap<HTMaterialKey, Fluid> = LinkedHashMultimap.create()
    private var tagsPresent: Boolean = false

    init {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(this)
    }

    //    HTFluidRegistry    //

    override val allEntries: Map<Fluid, HTMaterialKey>
        get() = fluidToMaterialMap

    override fun getFluid(materialKey: HTMaterialKey): Collection<Fluid> = materialToFluidsMap.get(materialKey)

    override fun contains(fluid: Fluid): Boolean = fluid in fluidToMaterialMap

    override fun contains(materialKey: HTMaterialKey): Boolean = materialToFluidsMap.containsKey(materialKey)

    override fun get(fluid: Fluid): HTMaterialKey? = fluidToMaterialMap[fluid]

    override fun add(fluid: Fluid, value: HTMaterialKey?) {
        registeredFluidMap[fluid] = value
    }

    override fun add(tag: Tag<Fluid>, value: HTMaterialKey?) {
        registeredTagMap[tag] = value
    }

    override fun remove(fluid: Fluid) {
        add(fluid, null)
    }

    override fun remove(tag: Tag<Fluid>) {
        add(tag, null)
    }

    override fun clear(fluid: Fluid) {
        remove(fluid)
        if (tagsPresent) reload()
    }

    override fun clear(tag: Tag<Fluid>) {
        remove(tag)
        if (tagsPresent) reload()
    }

    //    SimpleSynchronousResourceReloadListener    //

    private fun reload() {
        fluidToMaterialMap.clear()
        materialToFluidsMap.clear()

        registeredTagMap.forEach { (tag: Tag<Fluid>, key: HTMaterialKey?) ->
            if (key == null) return@forEach
            tag.values().forEach { fluid: Fluid ->
                fluidToMaterialMap[fluid] = key
                materialToFluidsMap.put(key, fluid)
            }
        }

        registeredFluidMap.forEach { (fluid: Fluid, key: HTMaterialKey?) ->
            if (key == null) return@forEach
            fluidToMaterialMap[fluid] = key
            materialToFluidsMap.put(key, fluid)
        }
    }

    override fun reload(manager: ResourceManager) {
        reload()
        tagsPresent = true
    }

    override fun getFabricId(): Identifier = HTMaterialsAPI.id("fluid_registry")

    private val dependenciesTag: Collection<Identifier> = listOf(ResourceReloadListenerKeys.TAGS)

    override fun getFabricDependencies(): Collection<Identifier> = dependenciesTag
}
