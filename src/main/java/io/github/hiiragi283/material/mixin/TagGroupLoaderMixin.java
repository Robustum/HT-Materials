package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.api.HTMaterialsAPI;
import io.github.hiiragi283.api.extension.TagKt;
import io.github.hiiragi283.api.part.HTPart;
import io.github.hiiragi283.api.tag.GlobalTagEvent;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin<T> {

    @Final
    @Shadow
    private String entryType;

    @Inject(method = "buildGroup", at = @At(value = "HEAD"))
    private void ht_materials$buildGroup(Map<Identifier, Tag.Builder> tags, CallbackInfoReturnable<TagGroup<T>> cir) {
        // Invoke each event
        HTMaterialsAPI.log("Current entry type: " + entryType);
        switch (entryType) {
            case "block": {
                GlobalTagEvent.getBLOCK().invoker().register(new GlobalTagEvent.Handler(tags));
                break;
            }
            case "item": {
                GlobalTagEvent.getITEM().invoker().register(new GlobalTagEvent.Handler(tags));
                // Convert tags into part format
                new HashMap<>(tags).forEach((Identifier id, Tag.Builder builder) -> {
                    HTPart part = HTPart.fromId(id);
                    if (part != null) {
                        Identifier partId = part.getPartId();
                        tags.compute(partId, (partId2, partBuilder) -> partBuilder == null ? builder : TagKt.merge(builder, partBuilder));
                        tags.remove(id);
                        HTMaterialsAPI.log("Migrated tag builder; " + id + " -> " + partId);
                    }
                });
                HTMaterialsAPI.log("Converted existing tags!");
                break;
            }
            case "fluid": {
                GlobalTagEvent.getFLUID().invoker().register(new GlobalTagEvent.Handler(tags));
                break;
            }
            case "entity_type": {
                GlobalTagEvent.getENTITY_TYPE().invoker().register(new GlobalTagEvent.Handler(tags));
                break;
            }
            default: break;
        }
        // Remove empty Builder
        new HashMap<>(tags).forEach((id, builder) -> {
            if (!builder.streamEntries().findAny().isPresent()) {
                tags.remove(id);
            }
        });
        HTMaterialsAPI.log("Removed empty tag builders!");
    }

}