package io.github.hiiragi283.api.extention

import net.minecraft.block.Block
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

val Fluid.id: Identifier
    get() = Registry.FLUID.getId(this)

fun Fluid.asBlock(): Block = this.defaultState.blockState.block

fun Fluid.isEmpty(): Boolean = this == Fluids.EMPTY

fun Fluid.notEmptyOrNull(): Fluid? = takeUnless { isEmpty() }

fun Fluid.isFlowable(): Boolean = this is FlowableFluid

fun Fluid.asFlowableOrNull(): FlowableFluid? = this.castTo()
