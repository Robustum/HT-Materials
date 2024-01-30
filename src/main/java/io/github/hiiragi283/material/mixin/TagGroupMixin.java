package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.part.HTPart;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(TagGroup.class)
public interface TagGroupMixin<T> {

    @Shadow
    Map<Identifier, Tag<T>> getTags();

    @Inject(method = "getTag", at = @At(value = "RETURN"), cancellable = true)
    default void ht_materials$getTag(Identifier id, CallbackInfoReturnable<Tag<T>> cir) {
        HTPart part = HTPart.fromId(id);
        if (part != null) {
            cir.setReturnValue(this.getTags().get(part.getPartId()));
        }
    }

}