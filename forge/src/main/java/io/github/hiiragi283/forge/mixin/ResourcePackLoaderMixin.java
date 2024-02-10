package io.github.hiiragi283.forge.mixin;

import io.github.hiiragi283.api.HTMaterialsAPI;
import io.github.hiiragi283.api.util.resource.HTResourcePackProvider;
import io.github.hiiragi283.api.util.resource.HTRuntimeDataPack;
import io.github.hiiragi283.forge.HTMaterialsCoreForge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.packs.ModFileResourcePack;
import net.minecraftforge.fml.packs.ResourcePackLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Mixin(ResourcePackLoader.class)
public class ResourcePackLoaderMixin {

    @Inject(method = "loadResourcePacks", at = @At("RETURN"), remap = false)
    private static <T extends ResourcePackProfile> void ht_materials$loadResourcePacks(ResourcePackManager resourcePacks, BiFunction<Map<ModFile, ? extends ModFileResourcePack>, BiConsumer<? super ModFileResourcePack, T>, ResourcePackLoader.IPackInfoFinder> packFinder, CallbackInfo ci) {
        if (DistExecutor.unsafeRunForDist(() -> () -> resourcePacks != MinecraftClient.getInstance().getResourcePackManager(), () -> () -> true)) {
            HTMaterialsCoreForge.INSTANCE.registerRecipes();
            resourcePacks.addPackFinder(HTResourcePackProvider.SERVER);
            HTMaterialsAPI.log("Registered runtime resource pack!");
        }
    }

}