package io.github.hiiragi283.material.mixin;

import com.google.common.collect.ImmutableSet;
import io.github.hiiragi283.material.MutableResourcePackManager;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(ResourcePackManager.class)
public class ResourcePackManagerMixin implements MutableResourcePackManager {

    @SuppressWarnings("ShadowModifiers")
    @Shadow
    private Set<ResourcePackProvider> providers;

    @Override
    public void ht_materials$addPackProvider(ResourcePackProvider provider) {
        ImmutableSet.Builder<ResourcePackProvider> builder = ImmutableSet.builder();
        builder.addAll(providers);
        builder.add(provider);
        providers = builder.build();
    }
}