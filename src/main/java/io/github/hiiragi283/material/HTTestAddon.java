package io.github.hiiragi283.material;

import io.github.hiiragi283.material.api.HTMaterialsAPI;
import io.github.hiiragi283.material.api.addon.HTMaterialsAddon;
import io.github.hiiragi283.material.api.material.flag.HTMaterialFlag;
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials;
import io.github.hiiragi283.material.api.material.property.HTWoodProperty;
import io.github.hiiragi283.material.common.HTMaterialsCommon;
import org.jetbrains.annotations.NotNull;

public class HTTestAddon implements HTMaterialsAddon {

    @NotNull
    @Override
    public String getModId() {
        return HTMaterialsCommon.MOD_ID;
    }

    @Override
    public void registerShapes() {
        /*HTMaterialsAPI.createShape("double_plate", builder -> {
            builder.idFormat = "%s_double_plate";
            builder.forgeTag = "plates/double/%s";
            builder.commonTag = "%s_double_plates";
            builder.itemPredicate = material -> material.hasFlag(HTMaterialFlag.GENERATE_PLATE);
        });*/
    }

    @Override
    public void modifyMaterials() {
        HTMaterialsAPI.modifyProperties(HTElementMaterials.ALUMINUM, properties -> properties.add(new HTWoodProperty()));
        HTMaterialsAPI.modifyFlags(HTElementMaterials.ALUMINUM, flags -> flags.addAll(HTMaterialFlag.GENERATE_GEAR));
    }

}