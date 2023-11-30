package io.github.hiiragi283.material;

import io.github.hiiragi283.material.api.HTMaterialsAPI;
import io.github.hiiragi283.material.api.addon.HTMaterialsAddon;
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag;
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials;
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials;
import io.github.hiiragi283.material.api.material.property.HTWoodProperty;
import io.github.hiiragi283.material.api.shape.HTShape;
import io.github.hiiragi283.material.common.HTMaterialsCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HTTestAddon implements HTMaterialsAddon {

    @NotNull
    @Override
    public String getModId() {
        return HTMaterialsCommon.MOD_ID;
    }

    @Override
    public void registerShapes() {
        HTMaterialsAPI.createShape("double_plate", builder -> {
            builder.idFormat = "%s_double_plate";
            builder.forgeTag = "plates/double/%s";
            builder.commonTag = "%s_double_plates";
            builder.itemPredicate = material -> material.hasFlag(HTMaterialFlag.GENERATE_PLATE);
        });
    }

    @Override
    public void registerMaterials() {

    }

    @Override
    public void modifyShapes() {
        HTMaterialsAPI.modifyBlockPredicate(HTShape.BLOCK, material -> !Objects.equals(material, HTVanillaMaterials.DIAMOND));
        HTMaterialsAPI.modifyBlockPredicate(HTShape.HOT_INGOT, material -> material.hasFlag(HTMaterialFlag.GENERATE_INGOT));
    }

    @Override
    public void modifyMaterials() {
        HTMaterialsAPI.modifyProperties(HTElementMaterials.ALUMINUM, properties -> properties.plusAssign(new HTWoodProperty()));
        HTMaterialsAPI.modifyFlags(HTElementMaterials.ALUMINUM, flags -> flags.addFlags(HTMaterialFlag.GENERATE_GEAR));
        HTMaterialsAPI.modifyInfo(HTElementMaterials.ALUMINUM, info -> info.setColor(Blocks.WHITE_CONCRETE));
    }

    @Override
    public void commonSetup() {
        HTMaterialsAPI.registerItemToPart(HTVanillaMaterials.CALCITE, HTShape.ROD, Items.BONE);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void clientSetup() {

    }

}