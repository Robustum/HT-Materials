package io.github.hiiragi283.material.mixin;

import com.google.gson.JsonElement;
import io.github.hiiragi283.material.api.util.HTCustomLootTableBlock;
import io.github.hiiragi283.material.util.HTMixinLogger;
import net.minecraft.loot.LootManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootManager.class)
public class LootManagerMixin {

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "HEAD"))
    private void ht_materials$apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        Registry.BLOCK.forEach(block -> {
            if (block instanceof HTCustomLootTableBlock) {
                map.put(block.getLootTableId(), LootManager.toJson(((HTCustomLootTableBlock) block).getLootTable().build()));
            }
        });
        HTMixinLogger.INSTANCE.info("HTMaterials registered loot tables!");
    }

}