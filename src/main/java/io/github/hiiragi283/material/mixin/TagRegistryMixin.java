package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.part.HTPart;
import io.github.hiiragi283.material.util.HTMixinLogger;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TagRegistry.class)
public abstract class TagRegistryMixin {

    @ModifyVariable(method = "item", at = @At(value = "HEAD"), argsOnly = true)
    private static Identifier ht_materials$item(Identifier id) {
        HTPart part = HTPart.fromId(id);
        if (part != null) {
            HTMixinLogger.INSTANCE.info("Redirected; " + id + " -> " + part.getPartId());
            return part.getPartId();
        }
        return id;
    }

}