package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.api.HTMaterialsAPI;
import io.github.hiiragi283.api.util.resource.HTResourcePackProvider;
import io.github.hiiragi283.api.util.resource.HTRuntimeDataPack;
import io.github.hiiragi283.material.MutableResourcePackManager;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "loadDataPacks", at = @At("HEAD"))
    private static void ht_materials$loadDataPacks(ResourcePackManager resourcePackManager, DataPackSettings dataPackSettings, boolean safeMode, CallbackInfoReturnable<DataPackSettings> cir) {
        HTRuntimeDataPack.writeTagData();
        ((MutableResourcePackManager) resourcePackManager).ht_materials$addPackProvider(HTResourcePackProvider.SERVER);
        HTMaterialsAPI.log("Registered runtime data pack!");
    }

}