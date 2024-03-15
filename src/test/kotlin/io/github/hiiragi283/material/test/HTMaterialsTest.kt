package io.github.hiiragi283.material.test

import com.google.gson.JsonObject
import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterial
import io.github.hiiragi283.api.material.property.HTMaterialProperty
import io.github.hiiragi283.api.material.property.HTPropertyType
import io.github.hiiragi283.api.shape.HTShape
import net.fabricmc.api.ModInitializer
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager

object HTMaterialsTest : ModInitializer {
    private val logger = LogManager.getLogger(HTMaterialsTest::class.java)

    override fun onInitialize() {
        Registry.register(
            HTPropertyType.REGISTRY,
            HTMaterialsAPI.id("test_type"),
            Type,
        )
        logger.info("HTMaterialsTest initialized!")
    }

    object Type : HTPropertyType<Property> {
        override fun readJson(jsonObject: JsonObject): Property {
            val name: String = JsonHelper.getString(jsonObject, "name")
            val value: Int = JsonHelper.getInt(jsonObject, "value")
            return Property(name, value)
        }
    }

    class Property(private val name: String, private val value: Int) : HTMaterialProperty<Property> {
        override val type: HTPropertyType<Property> = Type

        override fun appendTooltip(
            material: HTMaterial,
            shape: HTShape?,
            stack: ItemStack,
            lines: MutableList<Text>,
        ) {
            lines.add(LiteralText("- Name: $name"))
            lines.add(LiteralText("- Value: $value"))
        }
    }
}
