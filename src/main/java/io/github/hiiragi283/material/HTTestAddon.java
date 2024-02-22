package io.github.hiiragi283.material;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.hiiragi283.api.HTMaterialsAPI;
import io.github.hiiragi283.api.HTMaterialsAddon;
import io.github.hiiragi283.api.fluid.HTFluidManager;
import io.github.hiiragi283.api.material.HTMaterialKey;
import io.github.hiiragi283.api.material.HTMaterialType;
import io.github.hiiragi283.api.material.composition.HTMaterialComposition;
import io.github.hiiragi283.api.material.content.HTMaterialContentMap;
import io.github.hiiragi283.api.material.element.HTElement;
import io.github.hiiragi283.api.material.property.HTMaterialPropertyMap;
import io.github.hiiragi283.api.part.HTPartManager;
import io.github.hiiragi283.api.shape.HTShapeKey;
import io.github.hiiragi283.api.shape.HTShapeKeys;
import io.github.hiiragi283.api.util.HTColor;
import io.github.hiiragi283.api.collection.DefaultedMap;
import io.github.hiiragi283.api.material.content.HTSimpleFluidContent;
import io.github.hiiragi283.api.material.content.HTSimpleItemContent;
import net.fabricmc.api.EnvType;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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

    public static final HTShapeKey DIRTY_DUST = new HTShapeKey("dirty_dust");

    @Override
    public void registerShape(@NotNull HTMaterialsAddon.ShapeHelper shapeHelper) {
        shapeHelper.addShapeKey(DIRTY_DUST);
    }

    //    HTMaterial    //

    public static final HTMaterialKey INFINITY_KEY = new HTMaterialKey("infinity");

    @Override
    public void registerMaterialKey(@NotNull ImmutableSet.Builder<HTMaterialKey> registry) {
        registry.add(INFINITY_KEY);
    }

    public static final HTElement INFINITY_ELEMENT = HTElement.of(HTColor.WHITE, "Inf.", Double.MAX_VALUE);

    @Override
    public void modifyMaterialComposition(@NotNull Map<HTMaterialKey, HTMaterialComposition> registry) {
        registry.put(INFINITY_KEY, HTMaterialComposition.molecular(
                ImmutableMap.<HTElement, Integer>builder()
                        .put(INFINITY_ELEMENT, 1)
                        .build()
        ));
    }

    @Override
    public void modifyMaterialContent(@NotNull DefaultedMap<HTMaterialKey, HTMaterialContentMap.Builder> registry) {
        HTMaterialContentMap.Builder builder = registry.getOrCreate(INFINITY_KEY);
        builder.add(new HTSimpleFluidContent());
        builder.add(new HTSimpleItemContent(HTShapeKeys.DUST));
        builder.add(new HTSimpleItemContent(HTShapeKeys.GEAR));
        builder.add(new HTSimpleItemContent(HTShapeKeys.INGOT));
        builder.add(new HTSimpleItemContent(HTShapeKeys.NUGGET));
        builder.add(new HTSimpleItemContent(HTShapeKeys.PLATE));
        builder.add(new HTSimpleItemContent(HTShapeKeys.ROD));
    }

    @Override
    public void modifyMaterialProperty(@NotNull DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder> registry) {
        HTMaterialPropertyMap.Builder builder = registry.getOrCreate(INFINITY_KEY);
    }

    @Override
    public void modifyMaterialType(@NotNull Map<HTMaterialKey, HTMaterialType> registry) {
        registry.put(INFINITY_KEY, HTMaterialType.Metal.INSTANCE);
    }

    //    Post Init    //

    @Override
    public void bindFluidToPart(@NotNull HTFluidManager.Builder builder) {

    }

    @Override
    public void bindItemToPart(@NotNull HTPartManager.Builder builder) {
        builder.add(INFINITY_KEY, HTShapeKeys.GEM, Items.NETHER_STAR);
    }

    @Override
    public void postInitialize(@NotNull EnvType envType) {
        HTMaterialsAPI.getINSTANCE().shapeRegistry().getKeys().forEach(key -> HTMaterialsAPI.log("Shape: " + key));
        HTMaterialsAPI.getINSTANCE().materialRegistry().getKeys().forEach(key -> HTMaterialsAPI.log("Material: " + key));
    }

}