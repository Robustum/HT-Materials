package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.material.HTMaterial;
import io.github.hiiragi283.material.api.material.property.HTMetalProperty;
import io.github.hiiragi283.material.api.shape.HTShape;
import io.github.hiiragi283.material.common.HTUtil;
import kotlin.Unit;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.Map;

public class Test {

    public static void material() {
        SimpleRegistry<HTMaterial> registry = HTMaterial.REGISTRY;
        HTMaterial.Companion.createMaterial$HT_s_Materials(HTUtil.commonId("test"), material -> {
            material.modifyProperties(properties -> {
                properties.addSafety(new HTMetalProperty());
                return Unit.INSTANCE;
            });
            return Unit.INSTANCE;
        });
    }

    public static void shape() {
        Map<String, HTShape> registry = HTShape.REGISTRY;
    }

}