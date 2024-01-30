package io.github.hiiragi283.material;

import com.google.common.collect.ImmutableSet;
import io.github.hiiragi283.material.api.HTMaterialsAddon;
import io.github.hiiragi283.material.api.material.*;
import io.github.hiiragi283.material.api.material.content.HTMaterialContentMap;
import io.github.hiiragi283.material.api.material.content.HTSimpleFluidContent;
import io.github.hiiragi283.material.api.material.content.HTSimpleItemContent;
import io.github.hiiragi283.material.api.material.property.HTMaterialPropertyMap;
import io.github.hiiragi283.material.api.util.collection.DefaultedMap;
import io.github.hiiragi283.material.api.util.collection.DefaultedTable;
import io.github.hiiragi283.material.api.shape.HTShape;
import io.github.hiiragi283.material.api.shape.HTShapeKey;
import io.github.hiiragi283.material.api.shape.HTShapes;
import io.github.hiiragi283.material.api.util.HTColor;
import net.fabricmc.api.EnvType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings("unused")
public class HTTestAddon implements HTMaterialsAddon {

    @NotNull
    @Override
    public String getModId() {
        return HTMaterials.MOD_ID;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    //    HTShape    //

    public static final HTShapeKey DIRTY_DUST = new HTShapeKey("dirty_dust");

    @Override
    public void registerShape(@NotNull ImmutableSet.Builder<HTShapeKey> registry) {
        registry.add(DIRTY_DUST);
    }

    //    HTMaterial    //

    public static final HTMaterialKey INFINITY = new HTMaterialKey("infinity");

    @Override
    public void registerMaterialKey(@NotNull ImmutableSet.Builder<HTMaterialKey> registry) {
        registry.add(INFINITY);
    }

    @Override
    public void modifyMaterialContent(@NotNull DefaultedMap<HTMaterialKey, HTMaterialContentMap> registry) {
        HTMaterialContentMap builder = registry.getOrCreate(INFINITY);
        builder.add(new HTSimpleFluidContent());
        builder.add(new HTSimpleItemContent(HTShapes.DUST));
        builder.add(new HTSimpleItemContent(HTShapes.GEAR));
        builder.add(new HTSimpleItemContent(HTShapes.INGOT));
        builder.add(new HTSimpleItemContent(HTShapes.NUGGET));
        builder.add(new HTSimpleItemContent(HTShapes.PLATE));
        builder.add(new HTSimpleItemContent(HTShapes.ROD));
    }

    @Override
    public void modifyMaterialProperty(@NotNull DefaultedMap<HTMaterialKey, HTMaterialPropertyMap.Builder> registry) {
        HTMaterialPropertyMap.Builder builder = registry.getOrCreate(INFINITY);
    }

    @Override
    public void modifyMaterialColor(@NotNull Map<HTMaterialKey, ColorConvertible> registry) {
        registry.put(INFINITY, () -> HTColor.WHITE);
    }

    @Override
    public void modifyMaterialFormula(@NotNull Map<HTMaterialKey, FormulaConvertible> registry) {
        registry.put(INFINITY, () -> "INFINITY");
    }

    @Override
    public void modifyMaterialMolar(@NotNull Map<HTMaterialKey, MolarMassConvertible> registry) {
        registry.put(INFINITY, () -> Double.MAX_VALUE);
    }

    @Override
    public void modifyMaterialType(@NotNull Map<HTMaterialKey, HTMaterialType> registry) {
        registry.put(INFINITY, HTMaterialType.Metal.INSTANCE);
    }

    //    Post Init    //

    @Override
    public void bindFluidToPart(@NotNull DefaultedMap<HTMaterialKey, Collection<Fluid>> registry) {

    }

    @Override
    public void bindItemToPart(@NotNull DefaultedTable<HTMaterialKey, HTShapeKey, Collection<ItemConvertible>> registry) {
        registry.getOrCreate(INFINITY, HTShapes.GEM).add(Items.NETHER_STAR);
    }

    @Override
    public void postInitialize(@NotNull EnvType envType) {
        HTShape.getShapeKeys().forEach(key -> HTMaterials.log("Shape: " + key));
        HTMaterial.getMaterials().forEach(key -> HTMaterials.log("Material: " + key));
    }

}