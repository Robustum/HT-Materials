package io.github.hiiragi283.material.util;

import io.github.hiiragi283.material.api.material.HTMaterial;
import io.github.hiiragi283.material.api.shape.HTShape;
import net.minecraft.util.registry.SimpleRegistry;

public class Test {

    public static void material() {
        SimpleRegistry<HTMaterial> registry = HTMaterial.REGISTRY;
        HTMaterial.create("test", material -> {
            material.modifyProperties(properties -> {
            });
            material.modifyFlags(flags -> {
            });
            material.modifyInfo(info -> {
            });
        });
    }

    public static void shape() {
        SimpleRegistry<HTShape> registry = HTShape.REGISTRY;
    }

}