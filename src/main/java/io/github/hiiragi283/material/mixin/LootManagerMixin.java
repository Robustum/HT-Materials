package io.github.hiiragi283.material.mixin;

import com.google.gson.JsonElement;
import io.github.hiiragi283.api.HTMaterialsAPI;
import io.github.hiiragi283.api.util.resource.HTRuntimeDataManager;
import net.minecraft.loot.LootManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootManager.class)
public abstract class LootManagerMixin {

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "HEAD"))
    private void ht_materials$apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        HTRuntimeDataManager.lootTableConsumer(map::putAll);
        HTMaterialsAPI.log("Registered loot tables!");
    }

}