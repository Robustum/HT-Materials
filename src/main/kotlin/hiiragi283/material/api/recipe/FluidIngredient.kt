package hiiragi283.material.api.recipe

import net.minecraft.fluid.Fluid
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry
import java.util.function.BiPredicate

sealed class FluidIngredient(val amount: Long) : BiPredicate<Fluid, Long> {

    abstract fun getMatchingFluids(): Collection<Pair<Fluid, Long>>

    fun getFluidStack(fluid: Fluid) = fluid to amount

    //    Fluids    //

    class Fluids(vararg fluids: Fluid, amount: Long = 0) : FluidIngredient(amount) {

        private val fluids: Collection<Fluid> = fluids.toList()

        override fun getMatchingFluids(): Collection<Pair<Fluid, Long>> = fluids.map(::getFluidStack)

        override fun test(fluid: Fluid, amount: Long): Boolean = amount >= this.amount && fluid in fluids

    }

    //    Tag    //

    class Tags(val tag: TagKey<Fluid>, amount: Long = 0) : FluidIngredient(amount) {

        override fun getMatchingFluids(): Collection<Pair<Fluid, Long>> =
            Registry.FLUID.iterateEntries(tag)
                .map(RegistryEntry<Fluid>::value)
                .map(::getFluidStack)

        override fun test(fluid: Fluid, amount: Long): Boolean = amount >= this.amount && fluid.isIn(tag)

    }

    //    Custom    //

    class Custom(
        val matchingFluids: () -> Collection<Pair<Fluid, Long>>,
        val predicate: (Fluid, Long) -> Boolean,
        amount: Long = 0
    ) : FluidIngredient(amount) {

        override fun getMatchingFluids(): Collection<Pair<Fluid, Long>> = matchingFluids()

        override fun test(fluid: Fluid, amount: Long): Boolean = predicate(fluid, amount)

    }

}