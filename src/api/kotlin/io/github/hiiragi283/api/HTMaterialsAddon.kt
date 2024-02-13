package io.github.hiiragi283.api

import com.google.common.collect.ImmutableSet
import com.google.gson.JsonObject
import io.github.hiiragi283.api.collection.DefaultedMap
import io.github.hiiragi283.api.fluid.HTFluidManager
import io.github.hiiragi283.api.material.HTMaterialKey
import io.github.hiiragi283.api.material.HTMaterialType
import io.github.hiiragi283.api.material.composition.HTMaterialComposition
import io.github.hiiragi283.api.material.content.HTMaterialContentMap
import io.github.hiiragi283.api.material.flag.HTMaterialFlagSet
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap
import io.github.hiiragi283.api.part.HTPartManager
import io.github.hiiragi283.api.shape.HTShapeKey
import net.fabricmc.api.EnvType
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier

@JvmDefaultWithCompatibility
interface HTMaterialsAddon {
    val modId: String

    val priority: Int

    //    Initialize    //

    fun registerShape(registry: ImmutableSet.Builder<HTShapeKey>) {}

    fun modifyShapeIdPath(registry: MutableMap<HTShapeKey, String>) {}

    fun modifyShapeTagPath(registry: MutableMap<HTShapeKey, String>) {}

    fun registerMaterialKey(registry: ImmutableSet.Builder<HTMaterialKey>) {}

    fun modifyMaterialComposition(registry: MutableMap<HTMaterialKey, HTMaterialComposition>) {}

    fun modifyMaterialContent(registry: DefaultedMap<HTMaterialKey, HTMaterialContentMap.Builder>) {}

    fun modifyMaterialFlag(registry: DefaultedMap<HTMaterialKey, HTMaterialFlagSet.Builder>) {}

    fun modifyMaterialProperty(registry: DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder>) {}

    fun modifyMaterialType(registry: MutableMap<HTMaterialKey, HTMaterialType>) {}

    //    Post Initialization    //

    fun bindItemToPart(builder: HTPartManager.Builder) {}

    fun bindFluidToPart(builder: HTFluidManager.Builder) {}

    fun postInitialize(envType: EnvType) {}

    //   Recipe Unification    //

    fun replaceJsonRecipeOutput(id: Identifier, serializer: RecipeSerializer<*>, jsonObject: JsonObject) {}
}
