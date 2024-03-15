package io.github.hiiragi283.api.material.property

import com.google.gson.JsonObject
import io.github.hiiragi283.api.HTMaterialsAPI
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry

interface HTPropertyType<T : HTMaterialProperty<T>> {
    fun readJson(jsonObject: JsonObject): T

    @Suppress("UNCHECKED_CAST")
    fun cast(property: HTMaterialProperty<*>?): T? = property as? T

    companion object {
        @JvmField
        val REGISTRY: Registry<HTPropertyType<*>> = FabricRegistryBuilder
            .createSimple(HTPropertyType::class.java, HTMaterialsAPI.id("property_type"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister()

        @JvmStatic
        fun deserializeJson(jsonObject: JsonObject): HTMaterialProperty<*> {
            val id: Identifier = JsonHelper.getString(jsonObject, "type").let(::Identifier)
            val type: HTPropertyType<*> = checkNotNull(REGISTRY.get(id))
            return type.readJson(JsonHelper.getObject(jsonObject, "property"))
        }
    }
}
