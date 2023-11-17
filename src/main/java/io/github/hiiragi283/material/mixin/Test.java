package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.material.HTMaterial;
import io.github.hiiragi283.material.api.material.HTMaterialBuilder;
import io.github.hiiragi283.material.api.shape.HTShape;
import net.minecraft.util.registry.SimpleRegistry;

import static io.github.hiiragi283.material.api.HTMaterialsAPI.funcWrapper;

public class Test {

    public static void material() {
        SimpleRegistry<HTMaterial> registry = HTMaterial.REGISTRY;
        HTMaterialBuilder.createMetal("test", funcWrapper(material -> {
            material.modifyFlags(funcWrapper(flags -> {
            }));
            material.modifyInfo(funcWrapper(infos -> {
            }));
        }));
    }

    public static void shape() {
        SimpleRegistry<HTShape> registry = HTShape.REGISTRY;
    }

}