package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.api.tag.TagReloadedEvent;
import net.minecraft.tag.TagManager;
import net.minecraft.tag.TagManagerLoader;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TagManagerLoader.class)
public abstract class TagManagerLoaderMixin {

    @Shadow private TagManager tagManager;

    @Redirect(method = "method_30224", at = @At(value = "FIELD", target = "Lnet/minecraft/tag/TagManagerLoader;tagManager:Lnet/minecraft/tag/TagManager;", opcode = Opcodes.PUTFIELD))
    private void injected(TagManagerLoader instance, TagManager value) {
        this.tagManager = value;
        TagReloadedEvent.EVENT.invoker().onReloaded();
    }

}