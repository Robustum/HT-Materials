package io.github.hiiragi283.forge.mixin;

import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Tag.Builder.class)
public interface TagBuilderAccessor {

    @Accessor
    List<Tag.TrackedEntry> getEntries();

}