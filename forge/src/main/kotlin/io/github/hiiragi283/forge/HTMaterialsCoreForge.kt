package io.github.hiiragi283.forge

import io.github.hiiragi283.api.*
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPart
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKey
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.event.server.FMLServerStartedEvent
import net.minecraftforge.forgespi.language.ModFileScanData
import org.objectweb.asm.Type

internal object HTMaterialsCoreForge : HTMaterialsCore() {
    override fun collectAddons(): Iterable<HTMaterialsAddon> {
        val type: Type = Type.getType(HTAddon::class.java)
        return ModList.get().allScanData.flatMap { scanData ->
            scanData.annotations
                .asSequence()
                .filter { it.annotationType == type }
                .map(ModFileScanData.AnnotationData::getMemberName)
                .map { Class.forName(it) }
                .map { it.asSubclass(HTMaterialsAddon::class.java) }
                .map { it.getConstructor() }
                .map { it.newInstance() }
                .toList()
        }
    }

    override fun bindFluidToPart() {
        HTFluidManager.Builder().run {
            addons.forEach { it.bindFluidToPart(this) }
            HTMaterialsAPIForge.fluidManager = HTFluidManager(this)
        }
    }

    override fun bindItemToPart() {
        HTPartManager.Builder().run {
            addons.forEach { it.bindItemToPart(this) }
            HTMaterialsAPIForge.partManager = HTPartManager(this)
        }
    }

    fun serverStarted(event: FMLServerStartedEvent) {
        // Reload Fluid Manager
        HTMaterialsAPIForge.fluidManager = HTFluidManager.Builder().apply {
            // Register fluids from common tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                HTPlatformHelper.INSTANCE.getFluidTag(materialKey.getCommonId()).values().forEach { fluid ->
                    add(materialKey, fluid)
                }
            }
        }.let(::HTFluidManager)
        // Reload Part Manager
        HTMaterialsAPIForge.partManager = HTPartManager.Builder().apply {
            // Register items from part tag
            HTMaterialsAPI.INSTANCE.materialRegistry().getKeys().forEach { materialKey: HTMaterialKey ->
                HTMaterialsAPI.INSTANCE.shapeRegistry().getKeys().forEach { shapeKey: HTShapeKey ->
                    HTPart(materialKey, shapeKey).getPartTag().values().forEach { item ->
                        add(materialKey, shapeKey, item)
                    }
                }
            }
        }.let(::HTPartManager)
    }
}