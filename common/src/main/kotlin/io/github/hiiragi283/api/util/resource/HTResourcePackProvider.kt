package io.github.hiiragi283.api.util.resource

import net.minecraft.resource.ResourcePack
import net.minecraft.resource.ResourcePackProfile
import net.minecraft.resource.ResourcePackProvider
import net.minecraft.resource.ResourcePackSource
import java.util.function.Consumer

enum class HTResourcePackProvider(private val pack: ResourcePack) : ResourcePackProvider {
    SERVER(HTRuntimeDataPack),
    CLIENT(HTRuntimeResourcePack),
    ;

    override fun register(profileAdder: Consumer<ResourcePackProfile>, factory: ResourcePackProfile.Factory) {
        ResourcePackProfile.of(
            pack.name,
            true,
            this::pack,
            factory,
            ResourcePackProfile.InsertionPosition.TOP,
            ResourcePackSource.field_25347,
        )?.let(profileAdder::accept)
    }
}
