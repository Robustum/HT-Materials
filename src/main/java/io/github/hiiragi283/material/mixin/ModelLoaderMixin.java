package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.HTModelLoaderMixin;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

    /**
     * @see <a href="https://github.com/Flytre/ResourceCore/blob/master/src/main/java/net/flytre/resource_core/mixin/ModelLoaderMixin.java">GitHub - ResourceCore</a>
     */

    @Shadow
    @Final
    private ResourceManager resourceManager;

    @ModifyArg(method = "loadModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getAllResources(Lnet/minecraft/util/Identifier;)Ljava/util/List;"))
    private Identifier ht_materials$modifyBlockStateId(Identifier id) {
        return resourceManager.containsResource(id) ? id : HTModelLoaderMixin.modifyBlockStateId(id);
    }

    @ModifyArg(method = "loadModelFromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"))
    private Identifier ht_materials$modifyModelId(Identifier id) {
        return resourceManager.containsResource(id) ? id : HTModelLoaderMixin.modifyModelId(id);
    }

}