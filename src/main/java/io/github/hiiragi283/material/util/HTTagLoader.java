package io.github.hiiragi283.material.util;

import net.minecraft.util.registry.Registry;

import java.util.Optional;

/**
 * Reference: <a href="https://github.com/GregTechCEu/GregTech-Modern/blob/1.20.1/common/src/main/java/com/gregtechceu/gtceu/core/IGTTagLoader.java">...</a>
 *
 * @param <T>
 */

public interface HTTagLoader<T> {

    Optional<Registry<T>> ht_materials$getRegistry();

    void ht_materials$setRegistry(Registry<T> registry);

}