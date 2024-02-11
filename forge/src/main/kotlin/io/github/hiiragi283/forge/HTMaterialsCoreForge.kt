package io.github.hiiragi283.forge

import io.github.hiiragi283.api.HTAddon
import io.github.hiiragi283.api.HTMaterialsAddon
import io.github.hiiragi283.api.HTMaterialsCore
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKeys
import io.github.hiiragi283.api.util.addArray
import io.github.hiiragi283.api.util.addObject
import io.github.hiiragi283.api.util.buildJson
import io.github.hiiragi283.api.util.prefix
import io.github.hiiragi283.api.util.resource.HTRuntimeDataPack
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraftforge.fml.ModList
import net.minecraftforge.forgespi.language.ModFileScanData
import net.minecraftforge.registries.ForgeRegistries
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

    override fun ingotRecipe(partManager: HTPartManager, materialKey: HTMaterialKey, item: Item) {
        if (!partManager.hasItem(materialKey, HTShapeKeys.NUGGET)) return
        val nuggetTagId: Identifier = HTShapeKeys.NUGGET.getShape().getCommonId(materialKey)
        HTRuntimeDataPack.addRecipe(
            HTShapeKeys.INGOT.getShape().getIdentifier(materialKey).prefix("shaped/"),
            buildJson {
                addProperty("type", "minecraft:crafting_shaped")
                addArray("pattern") {
                    add("AAA")
                    add("AAA")
                    add("AAA")
                }
                addObject("key") {
                    addObject("A") {
                        addProperty("tag", nuggetTagId.toString())
                    }
                }
                addObject("result") {
                    addProperty("item", ForgeRegistries.ITEMS.getKey(item).toString())
                }
            },
        )
    }

    override fun nuggetRecipe(partManager: HTPartManager, materialKey: HTMaterialKey, item: Item) {
        if (!partManager.hasItem(materialKey, HTShapeKeys.INGOT)) return
        val ingotTagId: Identifier = HTShapeKeys.INGOT.getShape().getCommonId(materialKey)
        HTRuntimeDataPack.addRecipe(
            HTShapeKeys.NUGGET.getShape().getIdentifier(materialKey).prefix("shapeless/"),
            buildJson {
                addProperty("type", "minecraft:crafting_shapeless")
                addArray("ingredients") {
                    add(buildJson { addProperty("tag", ingotTagId.toString()) })
                }
                addObject("result") {
                    addProperty("item", ForgeRegistries.ITEMS.getKey(item).toString())
                    addProperty("count", 9)
                }
            },
        )
    }
}
