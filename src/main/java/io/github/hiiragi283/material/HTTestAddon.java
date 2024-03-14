package io.github.hiiragi283.material;

import com.google.common.collect.ImmutableMap;
import io.github.hiiragi283.api.HTMaterialsAPI;
import io.github.hiiragi283.api.HTMaterialsAddon;
import io.github.hiiragi283.api.extension.HTColor;
import io.github.hiiragi283.api.fluid.HTFluidManager;
import io.github.hiiragi283.api.material.HTMaterial;
import io.github.hiiragi283.api.material.HTMaterialKey;
import io.github.hiiragi283.api.material.HTMaterialRegistry;
import io.github.hiiragi283.api.material.HTMaterialType;
import io.github.hiiragi283.api.material.composition.HTMaterialComposition;
import io.github.hiiragi283.api.material.element.HTElement;
import io.github.hiiragi283.api.part.HTPartManager;
import io.github.hiiragi283.api.shape.HTShape;
import io.github.hiiragi283.api.shape.HTShapeRegistry;
import io.github.hiiragi283.api.shape.HTShapes;
import net.fabricmc.api.EnvType;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class HTTestAddon implements HTMaterialsAddon {

    @NotNull
    @Override
    public String getModId() {
        return HTMaterialsAPI.MOD_ID;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    //    HTShape    //

    public static final HTShape DIRTY_DUST = new HTShape("dirty_dust");

    @Override
    public void modifyShapeRegistry(@NotNull HTShapeRegistry.Builder builder) {
        builder.add(DIRTY_DUST);
    }

    //    HTMaterial    //

    public static final HTMaterialKey INFINITY_KEY = new HTMaterialKey("infinity");
    public static final HTElement INFINITY_ELEMENT = HTElement.of(HTColor.WHITE, "Inf.", Double.MAX_VALUE);

    @Override
    public void modifyMaterialRegistry(@NotNull HTMaterialRegistry.Builder builder) {
        HTMaterial.Builder builder1 = builder.getOrCreate(INFINITY_KEY);
        builder1.setComposition(HTMaterialComposition.molecular(
                ImmutableMap.<HTElement, Integer>builder()
                        .put(INFINITY_ELEMENT, 1)
                        .build()
        ));
        builder1.setType(HTMaterialType.Metal.INSTANCE);
    }

    /*@Override
    public void modifyMaterialContent(@NotNull DefaultedMap<HTMaterialKey, HTMaterialContentMap.Builder> registry) {
        HTMaterialContentMap.Builder builder = registry.getOrCreate(INFINITY_KEY);
        builder.add(new HTSimpleFluidContent());
        builder.add(new HTSimpleItemContent(HTShapes.DUST));
        builder.add(new HTSimpleItemContent(HTShapes.GEAR));
        builder.add(new HTSimpleItemContent(HTShapes.INGOT));
        builder.add(new HTSimpleItemContent(HTShapes.NUGGET));
        builder.add(new HTSimpleItemContent(HTShapes.PLATE));
        builder.add(new HTSimpleItemContent(HTShapes.ROD));
    }*/

    //    Post Init    //


    @Override
    public void modifyFluidManager(@NotNull HTFluidManager.Builder builder) {
        HTMaterialsAddon.super.modifyFluidManager(builder);
    }

    @Override
    public void modifyPartManager(@NotNull HTPartManager.Builder builder) {
        builder.add(INFINITY_KEY, HTShapes.GEM, Items.NETHER_STAR);
    }

    @Override
    public void postInitialize(@NotNull EnvType envType) {
        HTMaterialsAPI.getINSTANCE().getShapeRegistry().keySet().forEach(key -> HTMaterialsAPI.getLOGGER().info("Shape: " + key));
        HTMaterialsAPI.getINSTANCE().getMaterialRegistry().keySet().forEach(key -> HTMaterialsAPI.getLOGGER().info("Material: " + key));
    }

}