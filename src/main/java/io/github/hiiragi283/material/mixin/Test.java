package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.material.HTMaterial;
import io.github.hiiragi283.material.api.material.property.HTMetalProperty;
import io.github.hiiragi283.material.api.shape.HTShape;
import net.minecraft.util.registry.SimpleRegistry;

import static io.github.hiiragi283.material.api.HTMaterialsAPI.funcWrapper;

public class Test {

    public static void material() {
        SimpleRegistry<HTMaterial> registry = HTMaterial.REGISTRY;
        HTMaterial.createMaterial$HT_s_Materials("test", funcWrapper(material -> {
            material.modifyProperties(funcWrapper(properties -> properties.plusAssign(new HTMetalProperty())));
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