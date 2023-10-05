package hiiragi283.material.compat.rei.server

import hiiragi283.material.api.material.MaterialStack
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer
import me.shedaniel.rei.api.common.entry.EntrySerializer
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext
import me.shedaniel.rei.api.common.entry.type.EntryDefinition
import me.shedaniel.rei.api.common.entry.type.EntryType
import net.minecraft.nbt.NbtCompound
import net.minecraft.tag.TagKey
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.stream.Stream

object MaterialEntryDefinition : EntryDefinition<MaterialStack>, EntrySerializer<MaterialStack> {

    //    EntryDefinition   //

    override fun equals(o1: MaterialStack, o2: MaterialStack, context: ComparisonContext): Boolean = o1 == o2

    override fun getValueType(): Class<MaterialStack> = MaterialStack::class.java

    override fun getType(): EntryType<MaterialStack> = HiiragiEntryTypes.MATERIAL

    override fun getRenderer(): EntryRenderer<MaterialStack> = MaterialEntryRenderer

    override fun getSerializer(): EntrySerializer<MaterialStack> = this

    override fun getTagsFor(entry: EntryStack<MaterialStack>, value: MaterialStack): Stream<out TagKey<*>> =
        listOf(value.material.tagKey).stream()

    override fun asFormattedText(entry: EntryStack<MaterialStack>, value: MaterialStack): Text =
        value.material.getText()

    override fun hash(entry: EntryStack<MaterialStack>, value: MaterialStack, context: ComparisonContext): Long =
        value.hashCode().toLong()

    override fun wildcard(entry: EntryStack<MaterialStack>, value: MaterialStack): MaterialStack =
        value.copy(amount = 1)

    override fun normalize(entry: EntryStack<MaterialStack>, value: MaterialStack): MaterialStack =
        value.copy(amount = 1)

    override fun copy(entry: EntryStack<MaterialStack>, value: MaterialStack): MaterialStack = value.copy()

    override fun isEmpty(entry: EntryStack<MaterialStack>, value: MaterialStack): Boolean = value.isEmpty()

    override fun getIdentifier(entry: EntryStack<MaterialStack>, value: MaterialStack): Identifier =
        value.material.identifier

    //    EntrySerializer    //

    override fun supportSaving(): Boolean = true

    override fun supportReading(): Boolean = true

    override fun read(tag: NbtCompound): MaterialStack = MaterialStack.fromNbt(tag)

    override fun save(entry: EntryStack<MaterialStack>, value: MaterialStack): NbtCompound = value.toNbt()

}