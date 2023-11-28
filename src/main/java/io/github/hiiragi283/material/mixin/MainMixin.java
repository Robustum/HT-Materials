package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.addon.HTMaterialsAddons;
import io.github.hiiragi283.material.common.HTMaterialsCommon;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MainMixin {

    @Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;startTimerHack()V"))
    private static void ht_materials$startTimerHack(CallbackInfo ci) {
        HTMaterialsAddons.INSTANCE.commonSetup();
        HTMaterialsCommon.LOGGER.info("HTMaterials Addons finished common setup!");
    }

}