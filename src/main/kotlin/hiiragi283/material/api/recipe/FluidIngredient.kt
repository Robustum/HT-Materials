package hiiragi283.material.api.recipe

import com.google.gson.*
import hiiragi283.material.api.fluid.FluidStack
import hiiragi283.material.util.HiiragiNbtConstants
import net.minecraft.fluid.Fluid
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry
import java.util.function.Predicate

class FluidIngredient private constructor(private val entries: Collection<Entry>) : Predicate<FluidStack> {

    private val matchingStacks: MutableList<FluidStack> = mutableListOf()

    fun getMatchingStacks(): Collection<FluidStack> {
        cacheMatchingStacks()
        return matchingStacks
    }

    private fun cacheMatchingStacks() {
        if (matchingStacks.isEmpty()) {
            entries.flatMap(Entry::getStacks).forEach(matchingStacks::add)
        }
    }

    override fun test(stack: FluidStack): Boolean {
        cacheMatchingStacks()
        return if (matchingStacks.isEmpty()) {
            stack.isEmpty()
        } else {
            matchingStacks.any { FluidStack.isFluidEqual(it, stack) }
        }
    }

    companion object {

        @JvmStatic
        val EMPTY = FluidIngredient(listOf())

        @JvmStatic
        private fun ofEntries(vararg entries: Entry): FluidIngredient =
            if (entries.isEmpty()) EMPTY else FluidIngredient(entries.toList())

        @JvmStatic
        private fun ofEntries(entries: Collection<Entry>): FluidIngredient =
            if (entries.isEmpty()) EMPTY else FluidIngredient(entries)

        @JvmStatic
        fun ofFluids(vararg fluids: Fluid): FluidIngredient = ofStacks(fluids.map(::FluidStack))

        @JvmStatic
        fun ofStacks(vararg stacks: FluidStack): FluidIngredient = ofEntries(stacks.map(::StackEntry))

        @JvmStatic
        fun ofStacks(stacks: Collection<FluidStack>): FluidIngredient = ofEntries(stacks.map(::StackEntry))

        @JvmStatic
        fun fromTag(tag: TagKey<Fluid>): FluidIngredient = ofEntries(listOf(TagEntry(tag)))

        @JvmStatic
        fun fromJson(jsonElement: JsonElement): FluidIngredient = if (jsonElement.isJsonNull) {
            throw JsonSyntaxException("Fluid cannot be null")
        } else if (jsonElement.isJsonObject) {
            ofEntries(entryFromJson(jsonElement.asJsonObject))
        } else if (jsonElement.isJsonArray) {
            val jsonArray: JsonArray = jsonElement.asJsonArray
            if (jsonArray.isEmpty) {
                throw JsonSyntaxException("Fluid array cannot be empty, at least one fluid must be defined")
            }
            ofEntries(jsonArray.map { entryFromJson(JsonHelper.asObject(it, HiiragiNbtConstants.FLUID)) })
        } else throw JsonSyntaxException("Expected fluid to be object or array of objects")

        @JvmStatic
        private fun entryFromJson(jsonObject: JsonObject): Entry =
            if (jsonObject.has(HiiragiNbtConstants.FLUID) && jsonObject.has(HiiragiNbtConstants.TAG)) {
                throw JsonParseException("An ingredient entry is either a tag or an fluid, not both")
            } else if (jsonObject.has(HiiragiNbtConstants.FLUID)) {
                val fluid: Fluid =
                    Registry.FLUID.get(Identifier(JsonHelper.getString(jsonObject, HiiragiNbtConstants.FLUID)))
                StackEntry(FluidStack(fluid))
            } else if (jsonObject.has(HiiragiNbtConstants.TAG)) {
                val identifier = Identifier(JsonHelper.getString(jsonObject, HiiragiNbtConstants.TAG))
                val tagKey: TagKey<Fluid> = TagKey.of(Registry.FLUID_KEY, identifier)
                TagEntry(tagKey)
            } else {
                throw JsonParseException("An ingredient entry needs either a tag or an fluid")
            }


    }

    interface Entry {

        fun getStacks(): Collection<FluidStack>

        fun toJSon(): JsonObject

    }

    private class TagEntry(val tag: TagKey<Fluid>) : Entry {

        override fun getStacks(): Collection<FluidStack> = Registry.FLUID.iterateEntries(tag)
            .map(RegistryEntry<Fluid>::value)
            .map(::FluidStack)

        override fun toJSon(): JsonObject {
            val root = JsonObject()
            root.addProperty(HiiragiNbtConstants.TAG, tag.id.toString())
            return root
        }

    }

    private class StackEntry(val stack: FluidStack) : Entry {

        override fun getStacks(): Collection<FluidStack> = listOf(stack)

        override fun toJSon(): JsonObject {
            val root = JsonObject()
            root.addProperty(HiiragiNbtConstants.FLUID, Registry.FLUID.getId(stack.fluid).toString())
            return root
        }

    }

}