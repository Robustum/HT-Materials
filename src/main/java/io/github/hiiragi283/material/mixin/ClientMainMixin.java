package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.addon.HTMaterialsAddons;
import io.github.hiiragi283.material.common.HTMaterialsCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class ClientMainMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;"))
    private void ht_materials$currentThread(RunArgs args, CallbackInfo ci) {
        HTMaterialsAddons.INSTANCE.clientSetup();
        HTMaterialsCommon.LOGGER.info("HTMaterials Addons finished common & client setup!");
    }

}