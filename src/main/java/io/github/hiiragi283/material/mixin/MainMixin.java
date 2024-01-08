package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.HTMaterialsCore;
import io.github.hiiragi283.material.util.HTMixinLogger;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public abstract class MainMixin {

    @Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;startTimerHack()V"))
    private static void ht_materials$startTimerHack(CallbackInfo ci) {
        HTMaterialsCore.INSTANCE.commonSetup();
        HTMixinLogger.INSTANCE.info("HTMaterials Addons finished common setup!");
    }

}