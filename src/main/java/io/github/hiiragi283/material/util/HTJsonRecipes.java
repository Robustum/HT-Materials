package io.github.hiiragi283.material.util;

import io.github.hiiragi283.material.api.material.HTMaterial;
import io.github.hiiragi283.material.api.part.HTPartManager;
import io.github.hiiragi283.material.api.shape.HTShape;
import io.github.hiiragi283.material.common.HTMaterialsCommon;
import io.github.hiiragi283.material.common.HTRecipeManager;
import io.github.hiiragi283.material.common.util.HTUtil;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;

import java.util.Optional;

public class HTJsonRecipes {

    public static void registerRecipes() {
        HTMaterial.REGISTRY.forEach(material -> {
            materialRecipe(material);
            HTPartManager.getDefaultItemOptional(material, HTShape.BLOCK).ifPresent(item -> blockRecipe(material, item));
            HTPartManager.getDefaultItemOptional(material, HTShape.INGOT).ifPresent(item -> ingotRecipe(material, item));
            HTPartManager.getDefaultItemOptional(material, HTShape.NUGGET).ifPresent(item -> nuggetRecipe(material, item));
        });
    }

    private static void materialRecipe(HTMaterial material) {
        //1x Block -> 9x Ingot/Gem
        HTShape defaultShape = material.getDefaultShape();
        if (defaultShape == null) return;
        Item result = HTPartManager.getDefaultItem(material, defaultShape);
        if (result == null) return;
        HTRecipeManager.registerVanillaRecipe(
                HTUtil.suffix(defaultShape.getIdentifier(material, HTMaterialsCommon.MOD_ID), "_shapeless"),
                ShapelessRecipeJsonBuilder.create(result, 9)
                        .input(HTShape.BLOCK.getCommonTag(material))
                        .setBypassesValidation(true)
        );
    }

    private static void blockRecipe(HTMaterial material, Item item) {
        //9x Ingot/Gem -> 1x Block
        Optional.ofNullable(material.getDefaultShape()).ifPresent(defaultShape -> {
            if (!HTPartManager.hasDefaultItem(material, defaultShape)) return;
            HTRecipeManager.registerVanillaRecipe(
                    HTUtil.suffix(HTShape.BLOCK.getIdentifier(material, HTMaterialsCommon.MOD_ID), "_shaped"),
                    ShapedRecipeJsonBuilder.create(item)
                            .patterns("AAA", "AAA", "AAA")
                            .input('A', defaultShape.getCommonTag(material))
                            .setBypassesValidation(true)
            );
        });
    }

    private static void ingotRecipe(HTMaterial material, Item item) {
        //9x Nugget -> 1x Ingot
        if (!HTPartManager.hasDefaultItem(material, HTShape.NUGGET)) return;
        HTRecipeManager.registerVanillaRecipe(
                HTUtil.suffix(HTShape.INGOT.getIdentifier(material, HTMaterialsCommon.MOD_ID), "_shaped"),
                ShapedRecipeJsonBuilder.create(item)
                        .patterns("AAA", "AAA", "AAA")
                        .input('A', HTShape.NUGGET.getCommonTag(material))
                        .setBypassesValidation(true)
        );
    }

    private static void nuggetRecipe(HTMaterial material, Item item) {
        //1x Ingot -> 9x Nugget
        if (!HTPartManager.hasDefaultItem(material, HTShape.INGOT)) return;
        HTRecipeManager.registerVanillaRecipe(
                HTUtil.suffix(HTShape.NUGGET.getIdentifier(material, HTMaterialsCommon.MOD_ID), "_shapeless"),
                ShapelessRecipeJsonBuilder.create(item, 9)
                        .input(HTShape.INGOT.getCommonTag(material))
                        .setBypassesValidation(true)
        );
    }

}