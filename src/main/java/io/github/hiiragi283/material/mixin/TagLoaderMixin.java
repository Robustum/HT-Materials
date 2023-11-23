package io.github.hiiragi283.material.mixin;

import io.github.hiiragi283.material.api.material.HTMaterial;
import io.github.hiiragi283.material.api.part.HTPartManager;
import io.github.hiiragi283.material.api.shape.HTShape;
import io.github.hiiragi283.material.common.HTMaterialsCommon;
import io.github.hiiragi283.material.common.util.HTUtil;
import io.github.hiiragi283.material.common.util.TableUtil;
import io.github.hiiragi283.material.util.HTTagLoader;
import kotlin.Unit;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Mixin(TagGroupLoader.class)
public class TagLoaderMixin<T> implements HTTagLoader<T> {

    @Inject(method = "loadTags", at = @At(value = "RETURN"))
    private void ht_materials$loadTags(ResourceManager manager, CallbackInfoReturnable<Map<Identifier, Tag.Builder>> cir) {
        Map<Identifier, Tag.Builder> map = cir.getReturnValue();
        ht_materials$getRegistry().ifPresent(registry -> {
            if (registry == Registry.ITEM) {
                //Register items to HTPartManager
                HTMaterial.REGISTRY.forEach(material -> HTShape.REGISTRY.forEach(shape -> {
                    ht_materials$registerParts(map, shape.getForgeTag(material), material, shape);
                    ht_materials$registerParts(map, shape.getCommonTag(material), material, shape);
                }));
                //Register Tags from HTPartManager
                TableUtil.forEach(HTPartManager.getPartToItemsTable(), cell -> {
                    //Register ForgeTag and CommonTag
                    HTMaterial material = cell.getRowKey();
                    HTShape shape = cell.getColumnKey();
                    Collection<Item> items = cell.getValue();
                    ht_materials$registerTags(map, shape.getForgeTag(material), items);
                    ht_materials$registerTags(map, shape.getCommonTag(material), items);
                    //Sync ForgeTag and CommonTag entries
                    Tag.Builder forgeTagBuilder = map.get(shape.getForgeTag(material).id());
                    Tag.Builder commonTagBuilder = map.get(shape.getCommonTag(material).id());
                    if (forgeTagBuilder != null && commonTagBuilder != null) {
                        ht_materials$syncTags(commonTagBuilder, forgeTagBuilder);
                        ht_materials$syncTags(forgeTagBuilder, commonTagBuilder);
                    }
                    return Unit.INSTANCE;
                });
            }
        });
    }

    @Unique
    private void ht_materials$registerParts(Map<Identifier, Tag.Builder> map, TagKey<Item> tagKey, HTMaterial material, HTShape shape) {
        Optional.ofNullable(map.get(tagKey.id())).ifPresent(builder ->
                ((TagBuilderMixin) builder)
                        .getEntries().stream()
                        .map(Tag.TrackedEntry::entry)
                        .forEach(entry -> entry.forEachTagId(id -> {
                            Item item = Registry.ITEM.get(id);
                            if (!HTUtil.isAir(item)) {
                                HTPartManager.register(material, shape, item);
                            }
                        }))
        );
    }

    @Unique
    private void ht_materials$registerTags(Map<Identifier, Tag.Builder> map, TagKey<Item> tagKey, Collection<Item> items) {
        Tag.Builder builder = map.computeIfAbsent(tagKey.id(), key -> Tag.Builder.create());
        items.forEach(item -> builder.add(Registry.ITEM.getId(item), HTMaterialsCommon.MOD_NAME));
    }

    @Unique
    private void ht_materials$syncTags(Tag.Builder parentBuilder, Tag.Builder childBuilder) {
        ((TagBuilderMixin) childBuilder).getEntries().forEach(entry -> parentBuilder.add(entry.entry(), HTMaterialsCommon.MOD_NAME));
    }

    //    HTTagLoader    //

    @Nullable
    @Unique
    private Registry<T> registry;

    @Override
    public Optional<Registry<T>> ht_materials$getRegistry() {
        return Optional.ofNullable(registry);
    }

    @Override
    public void ht_materials$setRegistry(Registry<T> registry) {
        this.registry = registry;
    }

}