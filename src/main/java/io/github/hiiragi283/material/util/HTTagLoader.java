package io.github.hiiragi283.material.util;

import net.minecraft.util.registry.Registry;

import java.util.Optional;

public interface HTTagLoader<T> {

    Optional<Registry<T>> ht_materials$getRegistry();

    void ht_materials$setRegistry(Registry<T> registry);

}