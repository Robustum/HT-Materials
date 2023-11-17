package io.github.hiiragi283.material.client

import com.google.common.collect.HashBasedTable
import com.google.common.collect.ImmutableTable
import com.google.common.collect.Table
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.shape.HTShape
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.texture.TextureManager
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
object HTMaterialTextureManager {

    private val table: Table<HTMaterial, HTShape, Identifier> = HashBasedTable.create()

    @JvmStatic
    fun getTable(): ImmutableTable<HTMaterial, HTShape, Identifier> = ImmutableTable.copyOf(table)

    fun init() {
        table.clear()
        HTMaterial.REGISTRY.forEach { material ->
            HTShape.REGISTRY.forEach { shape ->
                forceRegister(material, shape, shape.defaultTextureId)
            }
        }
    }

    @JvmStatic
    fun getTextureId(material: HTMaterial, shape: HTShape): Identifier =
        table.get(material, shape) ?: TextureManager.MISSING_IDENTIFIER

    @JvmStatic
    fun registerTextureId(material: HTMaterial, shape: HTShape, textureId: Identifier) {
        if (!table.contains(material, shape)) forceRegister(material, shape, textureId)
    }

    @JvmStatic
    internal fun forceRegister(material: HTMaterial, shape: HTShape, textureId: Identifier) {
        table.put(material, shape, textureId)
    }

}