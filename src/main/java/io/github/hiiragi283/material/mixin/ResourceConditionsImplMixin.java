package io.github.hiiragi283.material.mixin;

import dev.architectury.core.RegistryEntry;
import net.fabricmc.fabric.impl.resource.conditions.ResourceConditionsImpl;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(ResourceConditionsImpl.class)
@SuppressWarnings({"UnstableApiUsage", "unchecked"})
public class ResourceConditionsImplMixin {

    @Redirect(method = "tagsPopulatedMatch", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 1))
    private static <K, V> V get$ht_materials(Map<K, V> instance, Object o) {
        Map<Identifier, Tag<RegistryEntry<?>>> map = (Map<Identifier, Tag<RegistryEntry<?>>>) instance;
        Identifier id = (Identifier) o;
        ResourceConditionsImpl.LOGGER.info(id.toString());
        return (V) map.get(id);
    }

}