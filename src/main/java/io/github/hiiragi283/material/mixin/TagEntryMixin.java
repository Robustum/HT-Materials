package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.api.part.HTPart;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Tag.TagEntry.class)
public abstract class TagEntryMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Identifier ht_materials$init(Identifier id) {
        HTPart part = HTPart.fromId(id);
        return part == null ? id : part.getPartId();
    }

}