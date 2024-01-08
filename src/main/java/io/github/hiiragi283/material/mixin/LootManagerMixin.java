package io.github.hiiragi283.material.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import io.github.hiiragi283.material.HTMaterialsCommon;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(LootManager.class)
public class LootManagerMixin {

    @Inject(
            method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;",
                    shift = At.Shift.BY,
                    by = 1
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void ht_materials$apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci, ImmutableMap.Builder<Identifier, LootTable> builder) {
        builder.put(HTMaterialsCommon.id("blocks/yellow_wool"), BlockLootTableGenerator.drops(Blocks.YELLOW_WOOL).build());
        //net.fabricmc.fabric.impl.loot.LootUtil.determineSource()
    }



}