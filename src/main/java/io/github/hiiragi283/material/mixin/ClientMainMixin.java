package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.event.HTModsLoadedCallback;
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
    public void ht_materials$currentThread(RunArgs args, CallbackInfo ci) {
        HTModsLoadedCallback.EVENT.invoker().onAllModsLoaded(EnvType.CLIENT);
    }

}