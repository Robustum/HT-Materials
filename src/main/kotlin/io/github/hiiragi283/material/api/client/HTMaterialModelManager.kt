package io.github.hiiragi283.material.api.client

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.hiiragi283.material.HTMaterials
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.material.HTMaterialType
import io.github.hiiragi283.material.api.shape.HTShapeKey
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.util.layeredModel
import net.minecraft.util.Identifier
import java.util.function.Function

object HTMaterialModelManager {
    //    Default Handler    //

    private val DEFAULT_FUNCTION: Function<HTMaterialKey, JsonObject> =
        Function { key: HTMaterialKey -> layeredModel(HTMaterials.id("item/${key.name}")) }

    private val DEFAULT_HANDLERS: MutableMap<HTShapeKey, Function<HTMaterialKey, JsonObject>> = hashMapOf()

    @JvmStatic
    fun setModelHandler(shapeKey: HTShapeKey, function: Function<HTMaterialKey, JsonObject>) {
        DEFAULT_HANDLERS[shapeKey] = function
    }

    //    Model Table    //

    private val MODEL_TABLE: Table<HTMaterialKey, HTShapeKey, JsonElement> = HashBasedTable.create()

    @JvmStatic
    fun getModel(materialKey: HTMaterialKey, shapeKey: HTShapeKey): JsonElement =
        MODEL_TABLE.get(materialKey, shapeKey) ?: DEFAULT_HANDLERS.getOrDefault(shapeKey, DEFAULT_FUNCTION)
            .apply(materialKey)

    @JvmStatic
    fun setModel(materialKey: HTMaterialKey, shapeKey: HTShapeKey, json: JsonObject) {
        MODEL_TABLE.put(materialKey, shapeKey, json)
    }

    init {
        // Dust
        setModelHandler(HTShapes.DUST) { _ ->
            layeredModel(Identifier("item/sugar"))
        }
        // Gem
        setModelHandler(HTShapes.GEM) { key ->
            val type: HTMaterialType = key.getMaterial().type
            if (type is HTMaterialType.Gem) {
                layeredModel(HTMaterials.id("item/${type.name.lowercase()}_gem"))
            } else {
                DEFAULT_FUNCTION.apply(key)
            }
        }
    }
}
