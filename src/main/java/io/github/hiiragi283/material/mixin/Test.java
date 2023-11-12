package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.material.HTMaterial;
import io.github.hiiragi283.material.api.material.property.HTMetalProperty;
import io.github.hiiragi283.material.api.shape.HTShape;
import kotlin.Unit;
import net.minecraft.util.registry.SimpleRegistry;

public class Test {

    public static void material() {
        SimpleRegistry<HTMaterial> registry = HTMaterial.REGISTRY;
        HTMaterial.Companion.createMaterial$HT_s_Materials("test", material -> {
            material.modifyProperties(properties -> {
                properties.addSafety(new HTMetalProperty());
                return Unit.INSTANCE;
            });
            return Unit.INSTANCE;
        });
    }

    public static void shape() {
        SimpleRegistry<HTShape> registry = HTShape.REGISTRY;
    }

}