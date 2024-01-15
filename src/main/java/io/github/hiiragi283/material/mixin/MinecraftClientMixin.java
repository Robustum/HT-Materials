package io.github.hiiragi283.material.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.hiiragi283.material.HTMaterialsCore;
import io.github.hiiragi283.material.api.resource.HTRuntimeResourcePack;
import io.github.hiiragi283.material.util.HTMixinLogger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        HTMixinLogger.INSTANCE.info("HTRuntimeResourcePack added!");
        return copyList;
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;"))
    private void ht_materials$currentThread(RunArgs args, CallbackInfo ci) {
        HTMaterialsCore.INSTANCE.clientSetup();
        HTMixinLogger.INSTANCE.info("HTMaterials Addons finished common & client setup!");
    }

}