package io.github.hiiragi283.material.mixin;

import com.google.gson.JsonElement;
import io.github.hiiragi283.material.HTMaterials;
import io.github.hiiragi283.material.api.util.resource.HTRuntimeDataManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ServerAdvancementLoader.class)
public abstract class ServerAdvancementLoaderMixin {

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "HEAD"))
    private void ht_materials$apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        HTRuntimeDataManager.advancementConsumer(map::putAll);
        HTMaterials.log("Registered Advancements!");
    }

}