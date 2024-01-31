package io.github.hiiragi283.material.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.hiiragi283.material.HTMaterials;
import io.github.hiiragi283.material.api.util.resource.HTRuntimeResourcePack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @ModifyExpressionValue(
            method = {"<init>", "reloadResources"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;createResourcePacks()Ljava/util/List;")
    )
    private List<ResourcePack> ht_materials$resourcePackManager(List<ResourcePack> original) {
        List<ResourcePack> copyList = new ArrayList<>(original);
        copyList.add(HTRuntimeResourcePack.INSTANCE);
        HTMaterials.log("HTRuntimeResourcePack added!");
        return copyList;
    }

}